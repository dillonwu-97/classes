/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
*/

#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/procq.e"
#include "../../h/asl.e"
#include "../../h/trap.e"
#include "../../h/util.h"
#include "../../h/int.e"

extern int p1();
int top_of_stack;
proc_link RQ;

// determines how much physical memory there is in the system
// calls initProc(), initsSemd(), trapinit(), initinit()
void static init(){
	initProc();
	initSemd();
	trapinit();
	intinit();
}

//
void schedule() {
	if (RQ.next != (proc_t*)ENULL) {
		intschedule();
		// load head of state
		proc_t* head = headQueue(RQ);
		long time;
		STCK(&time);
		head->timeofday = time;
		LDST(&head->p_s);

	} else {
		intdeadlock();
	}
}

// Calls init() and sets up processor state for p1()
// Adds p1() to the Ready Queue and calls schedule()
void main(){
	state_t stack_start; 
	STST(&stack_start);	
	top_of_stack = stack_start.s_sp;
	if (top_of_stack % 2 == 1) {
		// this means not word aligned
		top_of_stack--;
	}
	init();	
	// p1_p is processor state for p1
	proc_t* p1_p = allocProc();
	p1_p->p_s = stack_start;
	p1_p->p_s.s_sp = top_of_stack - 2*PAGESIZE;
	p1_p->p_s.s_pc = (int)p1;
	RQ.index = -1;
	RQ.next = (proc_t*)ENULL;
	insertProc(&RQ, p1_p);
	schedule();
	
}

