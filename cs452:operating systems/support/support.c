/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
*/
#include <string.h>
#include "../../h/support.h"
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
#include "../../h/slsyscall1.e"


// external variables
extern int start(), endt0(), startt1(), etext(), startd0(), endd0(), startd1(), edata(),
      startb0(), endb0(), startb1(), end();
extern int sem_pf, pf_ctr, pf_start, Tsysframe[5], Tmmframe[5],
       Scronframe, Spagedframe, Sdiskframe, Tsysstack[5], Tmmstack[5], 
	Scronstack, Spagedstack, Sdiskstack;
extern void vsempop();
// registers
register int r2 asm("%d2");
register int r3 asm("%d3");
register int r4 asm("%d4");

// semaphores
int sem_mm = 1; // mem management semaphore segment 1
int seg_lock= 1; // mem management semaphore segment 2
int wake_cron_mm = 0; // wakes up cron instead of having pseudoclock wake it up at each interval
int term_mm = 1; // semaphore that keeps track of the number of running processes; cron terminates when term_mm is 1? every time a process starts, increment with an UNLOCK

// state_t 
state_t proc1a, tpriv, tuser;
state_t	pstat_n[TERMCOUNT];
state_t mstat_n[TERMCOUNT];
state_t sstat_n[TERMCOUNT];
state_t pstat_o[TERMCOUNT];
state_t mstat_o[TERMCOUNT];
state_t sstat_o[TERMCOUNT];

long term_delay[TERMCOUNT]; // terminal delays
int term_sem[TERMCOUNT] = {0}; // terminal semaphore

// cron table prereq
sd_t cronsegtable[32];
pd_t cronpgtable[1024];

// 5 because 5 terminals total and each segment table for each terminal has 32 pages
// terminal tables - privileged
sd_t termsegpriv[TERMCOUNT][32];

// terminal tables - unprivileged
sd_t termseguser[TERMCOUNT][32];

// terminal page tables for segment 0 (privileged)
pd_t termpg_zero[TERMCOUNT][1024];
// terminal page tables for segment 1
// should use the same page table for both user and priv
pd_t termpg_one[TERMCOUNT][32]; // user
// terminal page tables for segment 2
pd_t termpg_two[32];

// terminal fubbers
char read_input [TERMCOUNT][UPAMOUNT];

// vpop structure
vpop p1semops[1];

// process counter
int proc_counter = 0; // semop is 0

// booty code 
int bootcode[] = {	/* boot strap loader object code */
0x41f90008,
0x00002608,
0x4e454a82,
0x6c000008,
0x4ef90008,
0x0000d1c2,
0x787fb882,
0x6d000008,
0x10bc000a,
0x52486000,
0xffde4e71
};

// functions
void static p1a();
void static tprocess();
void static slmmhandler();
void static slsyshandler();
void static slproghandler();
void static cron();

// terminal privileged segment 0 pagetable helper
void static termpg_zerohelper(int index, int flag) {
	int j;
	for (j = 0; j < TERMCOUNT; j++) {
		termpg_zero[j][index].pd_p = flag;
		termpg_zero[j][index].pd_r = 1; // setting reference bit to 1 <-- should i include this or not? pg 26 of packet says i should? 
		// can set frame for all of them
		termpg_zero[j][index].pd_frame = index;
		//termpg_zero[j][index].pd_r = flag;
	}			
}

// cron segment 0 pagetable helper
void static cronpg_helper(int index, int flag) {
	cronpgtable[index].pd_p = flag;
	cronpgtable[index].pd_r = 1; // setting reference bit to 1 <-- same question as above
	cronpgtable[index].pd_frame = index;
		//cronpgtable[index].pd_r = flag;
}

// initializes all segment and page tables
// protects nucleus from support level by marking nucleus page as not present
// initializes page module by calling pageinit()
// calls getfreeframe() and loads bootcode on page frame
// passes control to p1a() which runs with mem mapping on and interrupts enabled
void p1() {
	// page init
	pageinit(); 
	int i;
	// setting up seg 0 of crontable
	cronsegtable[0].sd_p = 1; // presence bit of page 0 of segment table
	cronsegtable[0].sd_prot = 7; // read write execute
	cronsegtable[0].sd_len = 1023; // last valid page is 1 less than max number of pages to handle overflows
	cronsegtable[0].sd_pta = &cronpgtable[0];	

	// initializing cron segment table for seg 1 onwards
	for (i = 1; i < 32; i++) {
		cronsegtable[i].sd_p = 0;	
	}

	for (i = 0; i < TERMCOUNT; i++) {
		// setting up seg 0 for each privileged terminal process
		termsegpriv[i][0].sd_p = 1;
		termsegpriv[i][0].sd_prot = 7; // are these all 7?
		termsegpriv[i][0].sd_len = 1023;
		termsegpriv[i][0].sd_pta = &termpg_zero[i][0];

		// setting up seg 1 for each privileged terminal process
		termsegpriv[i][1].sd_p = 1;
		termsegpriv[i][1].sd_prot = 7;
		termsegpriv[i][1].sd_len = 31;
		termsegpriv[i][1].sd_pta = &termpg_one[i][0];

		// setting up seg 1 for each user terminal process
		termseguser[i][1].sd_p = 1;
		termseguser[i][1].sd_prot = 7;
		termseguser[i][1].sd_len = 31;
		termseguser[i][1].sd_pta = &termpg_one[i][0];

		// setting up seg 2 for each privileged terminal process
		termsegpriv[i][2].sd_p = 1;
		termsegpriv[i][2].sd_prot = 7;
		termsegpriv[i][2].sd_len = 31;
		termsegpriv[i][2].sd_pta = &termpg_two[0];

		// setting up seg 2 for each user terminal process
		termseguser[i][2].sd_p = 1;
		termseguser[i][2].sd_prot = 7;
		termseguser[i][2].sd_len = 31;
		termseguser[i][2].sd_pta = &termpg_two[0];
	}

	int j;
	// initializing terminal user segment table for seg 0
	for (j = 0; j < TERMCOUNT; j++) {
		termseguser[j][0].sd_p = 0;
	}

	// initializing terminal priv and user segment table for page 3 onwards
	for (i = 3; i < 32; i++) {
		for (j = 0; j < TERMCOUNT; j++) {
			termsegpriv[j][i].sd_p = 0;	
			termseguser[j][i].sd_p = 0;
		}
	}
	// initializing cron segment 0 page table
	// also initializing terminals priv 0-5 segment 0 page table
	for (i = 0; i < 1024; i++) {
		if (i == 2) {
		// segment 0 page 2
			cronpg_helper(i,1);
			termpg_zerohelper(i,1);

		// EVT
		} else if (i >= 0x0/PAGESIZE && i <= 0x800/PAGESIZE) {
			cronpg_helper(i,0);
			termpg_zerohelper(i,0);
		} else if (i >= 0x800/PAGESIZE && i < 0x1400/PAGESIZE) {
		// trap int areas
			cronpg_helper(i,0);	
			termpg_zerohelper(i,0);
		} else if (i >= 0x1400/PAGESIZE && i <= 0x1500/PAGESIZE) {
		// device registers
			cronpg_helper(i,0);
			termpg_zerohelper(i,1);
		} else if (i >= (int)start/PAGESIZE && i <= (int)endt0/PAGESIZE) {
		// nucleus text
			cronpg_helper(i,0);
			termpg_zerohelper(i,0);
		} else if (i >= (int)startt1/PAGESIZE && i<= (int)etext/PAGESIZE) {
		// support level text
			cronpg_helper(i,1);
			termpg_zerohelper(i,1);
		} else if (i >= (int)startd0/PAGESIZE && i <= (int)endd0/PAGESIZE) {
		// nucleus data
			cronpg_helper(i,0);
			termpg_zerohelper(i,0);
		} else if (i >= (int)startd1/PAGESIZE && i <= (int)edata/PAGESIZE) {
		// support level data
			cronpg_helper(i,1);
			termpg_zerohelper(i,1);
		} else if (i >= (int)startb0/PAGESIZE && i <= (int)endb0/PAGESIZE) {
		// nucleus BSS 
			cronpg_helper(i,0);
			termpg_zerohelper(i,0);
		} else if (i >= (int)startb1/PAGESIZE && i <= (int)end/PAGESIZE) {
		// support level BSS
			cronpg_helper(i,1);
			termpg_zerohelper(i,1);
		} else if (i == Scronframe) {
		// setting up the stack for cron page table
			cronpg_helper(i, 1);
		} else {
			cronpg_helper(i,0);
			termpg_zerohelper(i,0);
		}
	}
 	
	// setting up sysframes and mmframes	
	for (i = 0; i < TERMCOUNT; i++) {
	        termpg_zero[i][Tsysframe[i]].pd_p = 1;
		termpg_zero[i][Tsysframe[i]].pd_r = 1;
		termpg_zero[i][Tsysframe[i]].pd_frame = Tsysframe[i];
		termpg_zero[i][Tmmframe[i]].pd_p = 1;	
		termpg_zero[i][Tmmframe[i]].pd_r = 1; 
		termpg_zero[i][Tmmframe[i]].pd_frame = Tmmframe[i];
	}


// setting all reference bits of segment 1 page table pages to 1
	for (j = 0; j < TERMCOUNT; j++) {
		for (i = 0; i < 32; i++) {
			termpg_one[j][i].pd_r = 1;
			//termpg_one[j][i].pd_frame = i;
			termpg_one[j][i].pd_p = 0;
		}
	}

	// setting all reference bits of segment 2 page table pages to 1
	for (i = 0; i < 32; i++) {
		termpg_two[i].pd_r = 1;
		//termpg_two[i].pd_frame = i;
		termpg_two[i].pd_p = 0;
	}
			
	
	// getfreeframe (terminal number, segment number, page number)
	int freeframe; 
	pd_t* pd_address; 
	int* copy_into;
	// memcpy test: are these the same?
	//int* test1 = (int*)(15000);
	//int* test2 = (int*)(16000);
	//memcpy(test1, bootcode, sizeof(bootcode));
	//for (i = 0; i < 11; i++) {
	//	*((test2 + i)) = bootcode[i]; <-- this one
	//	(test2 + i) = bootcode[i]; <-- not this one
	//}	
	
	
	for (i = 0; i < TERMCOUNT; i++) {
		// freeframe contains the frame number for the page, multiply by pagesize to get actual address
		// have to copy into the actual address with gives us the location of segment 1 page 31 on the stack
		freeframe = getfreeframe(i, 31, 1);
		copy_into = (int*)(freeframe*PAGESIZE);	
		memcpy(copy_into, bootcode, sizeof(bootcode));
		//for (j = 0; j < 11; j++) {
		//	*((copy_into + j)) = bootcode[j];
		//}
		pd_address = &termpg_one[i][31];	
		pd_address->pd_frame = freeframe;
		pd_address->pd_p = 1;
		pd_address->pd_m = 1;
		pd_address->pd_r = 1;
		// calculating physical address from the free frame number
		//pd_address = termsegpriv[i][1].sd_pta + freeframe;
		//memcpy(pd_address, bootcode, sizeof(bootcode)); <-- incorrect because you are copying into the struct?
	}


	STST(&proc1a);
	proc1a.s_sr.ps_m = 1;
	proc1a.s_sr.ps_s = 1;
	proc1a.s_sr.ps_int = 7;
	proc1a.s_pc = (int)p1a;
	proc1a.s_crp = &cronsegtable[0];
	proc1a.s_sp = Scronstack;
	
	LDST(&proc1a);	
	
}

// creates the terminal processes
// it then becomes a cron process
void static p1a() {
	// creating privileged start state for T-processes
	int i;
	STST(&tpriv);
	tpriv.s_pc = (int)tprocess;
	for (i = 0; i < TERMCOUNT; i++) {
		tpriv.s_sr.ps_m = 1; // mm on
		tpriv.s_sr.ps_s = 1; // privileged
		tpriv.s_sr.ps_int = 7; // int on
		// these above
		tpriv.s_sp = Tsysstack[i]; // privileged tprocesses are on the segment 0 stack, which exists after end()
		tpriv.s_crp = &termsegpriv[i][0];
		tpriv.s_r[2] = i; // ok to store here or should be somewhere else
		r4 = (int)&tpriv;
		SYS1(); // why does sys1 work but not creatproc; arent they the same thing:
		// i think it is because we are not in the nucleus so we dont have access to the function except through sys calls
		
		// keep track of the number of processes running
		p1semops[0].op = UNLOCK;
		p1semops[0].sem = &term_mm;
		r3 = 1;
		r4 = (int)p1semops;
		SYS3();

	}
	proc_counter++;
	cron();
}

// does the appropriate SYS5s 
// also loads state with user mode and PC=0X80000+31*PAGESIZE
void static tprocess() {
	
	/* specify trap vectors */
	
	int tn = r2;	
	STST(&pstat_n[tn]);
	pstat_n[tn].s_pc = (int)slproghandler;
	pstat_n[tn].s_sp = Tsysstack[tn];
	pstat_n[tn].s_crp = &termsegpriv[tn][0];

	STST(&mstat_n[tn]);
	mstat_n[tn].s_pc = (int)slmmhandler;
	mstat_n[tn].s_sp = Tmmstack[tn];
	mstat_n[tn].s_crp = &termsegpriv[tn][0];	// used for getting page offsets? / for paging?? is this even useful info? it is not essential but is good practice

	STST(&sstat_n[tn]);
	sstat_n[tn].s_pc = (int)slsyshandler;
	sstat_n[tn].s_sp = Tsysstack[tn];
	sstat_n[tn].s_crp = &termsegpriv[tn][0]; 

	// placing terminal number in the new areas
	pstat_n[tn].s_r[4] = tn;
	mstat_n[tn].s_r[4] = tn;
	sstat_n[tn].s_r[4] = tn;

	r2 = PROGTRAP;
	r3 = (int)&pstat_o[tn];
	r4 = (int)&pstat_n[tn];
	SYS5();

	r2 = MMTRAP;
	r3 = (int)&mstat_o[tn];
	r4 = (int)&mstat_n[tn];
	SYS5();

	r2 = SYSTRAP;
	r3 = (int)&sstat_o[tn];
	r4 = (int)&sstat_n[tn];
	SYS5();


	// load tprocess with user mode and correct PC
	STST(&tuser);
	tuser.s_sr.ps_s = 0; // user mode
	tuser.s_sr.ps_m = 1;  // mm on
	tuser.s_sr.ps_int = 1; // int on
	tuser.s_pc = 0x80000+31*PAGESIZE; // bottom of page 31
	tuser.s_sp = 0x80000+32*PAGESIZE - 2; // is this the top of page 31? have to decrement by 2 because u dont want to go into the next page
	tuser.s_crp = &termseguser[tn][0]; // seg table of user
	proc_counter++;
	LDST(&tuser);
	
}

// checks the validity of a page fault
// calls getfreeframe() to allocate free page frame
// if necessary calls pagein and then updates page tables
// uses semaphore sem_mm to control access to critical section that updates shared data structures
// pages in segment two are special case in this function
void static slmmhandler() {
	state_t copy; // do this to make a copy of the current processor state
	STST(&copy);
	int tn = copy.s_r[4];
	
	// information about the mm trap
	int seg_num, page_num, pres_bit, ref_bit, free_frame, type;
	type = mstat_o[tn].s_tmp.tmp_mm.mm_typ;
	seg_num = mstat_o[tn].s_tmp.tmp_mm.mm_seg; // segment number
	page_num = mstat_o[tn].s_tmp.tmp_mm.mm_pg; // page number
		
	// if segment 1 and page miss
	if (seg_num == 1 && type == 1) {
		free_frame = getfreeframe(tn, page_num, seg_num); //term, page, seg
		// also need to lock sem_mm while doing this so that nothing interferes while you are updating the reference bit
		vsempop(LOCK, &sem_mm);
		// s_crp[tn] or s_crp[seg_num]?
		pres_bit = mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_p; // presence bit
		ref_bit = mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_r; // reference bit

		// if presence bit is off and reference bit is off, then we need to call page_in
		// not sure what else I need to do here
		//if (pres_bit == 0 && ref_bit == 0) {
		if (ref_bit == 0) {	
			pagein(tn, page_num, seg_num, free_frame); // term, page, seg, pf (page frame)
		} else {
			// update page table entry and turn on valid bit
			//mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_p = 1;
			//mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_frame = free_frame;
			termpg_one[tn][page_num].pd_frame = free_frame;
			termpg_one[tn][page_num].pd_p = 1;
			termpg_one[tn][page_num].pd_r = 0;
						//mstat_o[0].s_crp[tn].sd_pta[page_num].pd_frame = free_frame; // this actually just triggers another page fault
		}
		// unlock sem_mm now that we are done updating
		vsempop(UNLOCK, &sem_mm);
	} else if (seg_num == 2 && type ==1) {
		vsempop(LOCK, &seg_lock);
		// if there is still a page miss
		pres_bit = mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_p; // presence bit
		ref_bit = mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_r; // reference bit
		// get free frame 
		free_frame = getfreeframe(tn, page_num, seg_num);
		vsempop(LOCK, &sem_mm);	

		// if presence bit is off and reference bit is 0, this means that the page has been visited before but is no longer in our frames list
		// as a result, we page in we put the page which we had seen before into the new free frame we just got
		//if (pres_bit == 0 && ref_bit == 0) {
		if (ref_bit == 0) {
			pagein(tn, page_num, seg_num, free_frame);
		} else {
			termpg_two[page_num].pd_frame = free_frame;
			termpg_two[page_num].pd_p = 1;
			termpg_two[page_num].pd_r = 0;
			//mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_frame = free_frame;
			//mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_p = 1;
			//mstat_o[tn].s_crp[seg_num].sd_pta[page_num].pd_r = 0;
		}
		// unlock sem_mm
		vsempop(UNLOCK, &sem_mm);
		// unlock seg_lock
		vsempop(UNLOCK, &seg_lock);
			
	} else {
	// if the page fault is anything other than type 1, then shut down the system
		SYS17(); // terminate??? what to do here	
	}	
	LDST(&mstat_o[tn]);
}

// switch statements and calls functions in slsyscall1.c and slsyscall2.c
void static slsyshandler() {
	state_t copy;
	STST(&copy);
	int tn = copy.s_r[4];
	int switch_val = sstat_o[tn].s_tmp.tmp_sys.sys_no;
	switch (switch_val) {
		case (9):
			readfromterminal(tn);
			break;
		case(10):
			writetoterminal(tn);
			break;
		case(11):
			virtualv(tn);
			break;
		case(12):
			virtualp(tn);
			break;
		case(13):
			delay(tn);
			break;
		case(14):
			diskput(tn);
			break;
		case(15):
			diskget(tn);
			break;
		case(16):
			gettimeofday(tn);
			break;
		case(17):
			terminate( tn);
			break;
	}
	LDST(&sstat_o[tn]);
	
}

// calls terminate
void static slproghandler() {
	terminate();
}

// release processes which delayed themselves
// shuts down if no t processes running
// in an infinite loop and blocks on the pseudoclock if there is no work to be done
// should synchronize delay and cron
void static cron() {
	// blocking on pseudoclock if no work to be done
	// check the array of tproc semaphores to see if anything is blocked
	// check the timers to see if its the right time 
	vpop cronsemop[1];
	while (1) {
		int i;
		// lock cron on semaphore because there are no delays happening	
		int flag = 0;
		long now;
		STCK(&now);	
		for (i = 0; i <TERMCOUNT; i++) {
			// if there is a process blocked on semaphore and it is the right time, then unblock and put on RQ
			if (term_sem[i] == -1) {
				flag = 1;
				if (term_delay[i] < now) {
					
					term_delay[i] = 0; // reset timer
					// unlock the termsemaphore
					// lock wake_cron_mm
					// is the logic and order correct?
					r3 = 1;
					// unlock the process that also wanted to delay but couldnt because there was someone that already asked for a delay, third
					r4 = (int)cronsemop;
					cronsemop[0].op = UNLOCK;
					cronsemop[0].sem = &term_sem[i];
					SYS3();
				}
			}
		}
		if (flag == 1) {
			SYS7(); // waitforpclock
		} else {
			// unlock the process that caused the delay second so that it can run
			// lock cron first since it is at the head of RQ
			if (term_mm == 1) {
				SYS2();	
			} else {
					
				cronsemop[0].op = LOCK;
				cronsemop[0].sem = &wake_cron_mm;
				r3 = 1;
				r4 = (int)cronsemop;
				SYS3();
			}
				
		}

	// check if cron should terminate; do this with a tproc counter; for each process created, do an unlock in p1a, that just decrements each time terminate is called
	// if get to 0 -> no tproc, terminate
	
	}
	
}


