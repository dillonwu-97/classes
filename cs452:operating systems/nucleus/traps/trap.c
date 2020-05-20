/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
*/

#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/procq.e"
#include "../../h/asl.e"
#include "../../h/util.h"
#include "../../h/main.e"
#include "../../h/int.e"
#include "../../h/syscall.e"
#include "../../h/vpop.h"
void static trapsyshandler(); 
void static trapmmhandler();
void static trapproghandler();

state_t *sysarea;
state_t *mmarea;
state_t *progarea;
state_t *sysnew_area;
state_t *mmnew_area;
state_t *prognew_area;
// loads several entries in the EVT and sets the new areas for the traps
void trapinit() {
	*(int*)0x008 = (int)STLDMM;
	*(int*)0x00c = (int)STLDADDRESS;
	*(int*)0x010 = (int)STLDILLEGAL;
	*(int*)0x014 = (int)STLDZERO;
	*(int*)0x020 = (int)STLDPRIVILEGE;
	*(int*)0x08c = (int)STLDSYS;
	*(int*)0x94 = (int)STLDSYS9;
	*(int*)0x98 = (int)STLDSYS10;
	*(int*)0x9c = (int)STLDSYS11;
	*(int*)0xa0 = (int)STLDSYS12;
	*(int*)0xa4 = (int)STLDSYS13;
	*(int*)0xa8 = (int)STLDSYS14;
	*(int*)0xac = (int)STLDSYS15;
	*(int*)0xb0 = (int)STLDSYS16;
	*(int*)0xb4 = (int)STLDSYS17;
	*(int*)0x100 = (int)STLDTERM0;
	*(int*)0x104 = (int)STLDTERM1;
	*(int*)0x108 = (int)STLDTERM2;
	*(int*)0x10c = (int)STLDTERM3;
	*(int*)0x110 = (int)STLDTERM4;
	*(int*)0x114 = (int)STLDPRINT0;
	*(int*)0x11c = (int)STLDDISK0;
	*(int*)0x12c = (int)STLDFLOPPY0;
	*(int*)0x140 = (int)STLDCLOCK;

	// Defining new area
	state_t* new_area;
	// if i do not add sizeof, i am just at the oldstate
	progarea = (state_t*)(0x800);
	new_area = (state_t*)0x800+1;
	STST(progarea);
	STST(new_area);
	new_area->s_pc = (int)trapproghandler;
	new_area->s_sp = top_of_stack;
	prognew_area = new_area;
	
	mmarea = (state_t*)0x898;
	new_area = (state_t*)0x898+1;
	STST(mmarea);
	STST(new_area);
	new_area->s_pc = (int)trapmmhandler;
	new_area->s_sp = top_of_stack;
	mmnew_area = new_area;

	sysarea = (state_t*)0x930;
	new_area = (state_t*)0x930+1;
	STST(sysarea);
	STST(new_area);
	new_area->s_pc = (int)trapsyshandler;
	new_area->s_sp = top_of_stack;
	sysnew_area = new_area;
}

// gets the current time and puts into timeofday
void timer(proc_t* head) {
	long time;
        STCK(&time);
        head->timeofday = time;
}

// calculates the time that was spent by the process until a trap or interrupt
int cpu_timer(proc_t* head) {
	long time;
	STCK(&time);
	return time - head->timeofday;
}

// calculates the time that had been used and puts it into the cpu_time 
// updates timeofday again
void save_time(proc_t* head) {
	int used_time = cpu_timer(head);// save the amount of time that the process had been running
	head->cputime += used_time;
	timer(head);

}

// pass up from the traps
void batonpass(int val) {
	proc_t* head = headQueue(RQ);
	save_time(head);
	if (val == 2) {
		if (head->newsys!= (state_t*)ENULL) {
			*head->oldsys = *sysarea;
			LDST(head->newsys);	
		} else {
			killproc();
		}
	}
	if (val == 1) {
		if (head->newmm!= (state_t*)ENULL) {
			// old area or new area? which one and why?
			*head->oldmm = *mmarea;
			LDST(head->newmm);	
		} else {
			killproc();
		}
	}
	if (val == 0) {
		if (head->newprog!= (state_t*)ENULL) {
			//LDST(&(head->p_s));
			*head->oldprog = *progarea;
			LDST(head->newprog);

		} else {
			killproc();
		}
	}
}

// handles 9 different traps; has switch statement and each case calls a function
// waitforpclock and waitforio are in int.c
// rest are in syscall
// Note: trapsyshandler passes pointer to old trap area to functions in syscall.c and int.c
void static trapsyshandler() {
	proc_t* head = headQueue(RQ);
	state_t head_state = head->p_s;
	unsigned switch_val = sysarea->s_tmp.tmp_sys.sys_no;
	save_time(head);
	// systrap for SYS9 through SYS17	
	if (switch_val >= 9 && switch_val <=17) {
		batonpass(2);
	// pass up to prog handler if there are incorrect privileges
	} else if (sysarea->s_sr.ps_s == 0) {
		*progarea = *sysarea;
		progarea->s_tmp.tmp_pr.pr_typ = PRIVILEGE;
		batonpass(0);
	} else {
		switch(switch_val){
			case (1):
				creatproc();
				break;
			case (2):
				killproc();
				break;
			case (3):
				semop();
				break;
			case (4):
				notused();
				break;
			case (5):
				trapstate(sysarea->s_r[2]);
				break;
			case (6):
				getcputime();
				break;	
			case (7):
				waitforpclock();
				break;
			case (8):
				waitforio();
				break;
			default:
				batonpass(2);
				break;
		}
	}
	timer(head); // save time of day when the process starts up again	
	LDST(sysarea);

}
// pass up traps or terminate process
void static trapmmhandler() {
	batonpass(1);
}

void static trapproghandler() {
	// pass up traps or terminate process
	batonpass(0); 
}
