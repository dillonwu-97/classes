/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
*/
#include <string.h>
#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/vpop.h"
#include "../../h/util.h"
#include "h/tconst.h"
#include "../../h/procq.e"
#include "../../h/asl.e"
#include "../../h/main.e"
#include "../../h/trap.e"
#include "../../h/syscall.e"
#include "../../h/page.e"
#include "../../h/support.h"

// upamount = 128
// buff size of disk = 512

#define TERMCOUNT 5

extern int wake_cron_mm;
extern int term_mm;
extern long term_delay[TERMCOUNT];
extern long term_sem [TERMCOUNT];
extern state_t pstat_n[TERMCOUNT];
extern state_t mstat_n[TERMCOUNT];
extern state_t sstat_n[TERMCOUNT];
extern state_t pstat_o[TERMCOUNT];
extern state_t mstat_o[TERMCOUNT];
extern state_t sstat_o[TERMCOUNT];
// device registers
extern devreg_t* devregs[15];

extern int proc_counter;

register int r2 asm("%d2");
register int r3 asm("%d3");
register int r4 asm("%d4");

extern char read_input[TERMCOUNT][UPAMOUNT];

// requests invoking t process be suspended until line of input has been read from associated terminal
// data read should be placed in virtual memory of t process executing sys 9, at virtual address given in d3 during time of call
// count of number of characters read from terminal available in d2 when t process is continued
// attempt to read when there is no more data available returns negative of the "end of input" status code in D2
// if operation ends with status other than "successful completion" or "end of input", negative of Status register value should be returned in D2
// negative numbers returned in D2 are error flags
void readfromterminal(int tn){
	char* virtual_address = (char*)sstat_o[tn].s_r[3];	// data read  should be placed here
	// can print values are string char 
	devreg_t* term = devregs[tn];
	// set up the terminal registers
	term->d_badd = &read_input[tn][0];
	term->d_op = IOREAD;
	r4 = tn; // assign the terminal number
	SYS8(); // waitforio
	
	// the waitforio call was ok so put into the address
	if (r3 == ENDOFINPUT || r3 == NORMAL) {
		int amnt = r2;
		if (amnt > 0) {
			int i;
			memcpy(virtual_address, &read_input[tn][0], amnt);
			sstat_o[tn].s_r[2] = amnt;
		}
		if (r3 == ENDOFINPUT) {
			sstat_o[tn].s_r[2] = -1 * ENDOFINPUT;
		}
	} else {
	// damedayo
		sstat_o[tn].s_r[2] = -1 * r3;
	}

}

// requests t-process be suspended until a line of output has been written on terminal associated with process 
// virtual address of first character of the line written will be in D3 at time of call
// count of number of characters to be written will be in D4
// count of number of characters actually written should be placed in D2 upon completion of SYS10
// as in SYS9 non successful completion status will cause error flag to be returned instead of character count
void writetoterminal(int tn){
	char* virtual_address = (char*) sstat_o[tn].s_r[3];
	int count = sstat_o[tn].s_r[4];
	memcpy(&read_input[tn][0], virtual_address, count); // copy into buffer
	devreg_t *term = devregs[tn];
	term->d_amnt = count;
	term->d_badd = &read_input[tn][0];
	term->d_op = IOWRITE;
	r4 = tn;
	SYS8(); // waitforio
	if (r3 == NORMAL) {
		sstat_o[tn].s_r[2] = count;
	} else {
		sstat_o[tn].s_r[2] = -1 * count;
	}

}

// d4 contains number of microseconds for which invoke is delayed
// caller delayed at least this long, and not substantially longer
// nucleus controls low level scheduling decisions, all you can ensure is invoker is not dispatchable until time has elapsed
// but becomes dispatchable shortly thereafter
void delay(int tn) {
	long to_delay;
	to_delay = sstat_o[tn].s_r[4];
	long now;
	STCK(&now);	
	vpop p1semops[2];
	// lock on do_cron_mm
	// the idea is that only one t process at a time is updating the semaphore array
	term_delay[tn] = to_delay + now;
	p1semops[0].op = LOCK;
	p1semops[0].sem =(int*)&term_sem[tn];
	
	// unlock wake_cron_mm
	// do i have the privileges to make a semop syscall?
	// can i just do semop or do i have to do sys 3?
	// place cron on the RQ
	p1semops[1].op = UNLOCK;
	p1semops[1].sem = &wake_cron_mm;
	r3 = 2;
	r4 = (int)p1semops;
	SYS3(); 



}

// returns value of timeofday clock in D2
void gettimeofday(int tn){
	long tod;
	STCK(&tod);
	sstat_o[tn].s_r[2] = tod;

}

// terminates t process
// when all t processes have terminated, operating system shuts down
// system processes created in support level must be terminated after all five t processes have terminated
// there should be no dispatchable or blocked processes so nucleus will halt
void terminate(){
	// unlock counter for processes
	vpop p1semops[1];
	p1semops[0].op = LOCK;
	p1semops[0].sem = &term_mm;
	r3 = 1;
	r4 = (int)p1semops;
	SYS3();
	
	if (term_mm == 1) {
		// unlock cron to terminate
		p1semops[0].op = UNLOCK;
		p1semops[0].sem = &wake_cron_mm;
		SYS3();	
	}
	// terminate	
	SYS2();
	putframe();

}
