/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu 
*/
#include "../../h/const.h"
#include "../../h/types.h"
#include "../../h/procq.e"
#include "../../h/asl.e"
#include "../../h/main.e"
#include "../../h/trap.e"
#include "../../h/vpop.h"

extern int cpu_timer();

// finds the youngest sibling 
proc_t* findyoungest (proc_t* child) {
	
	while (child->pflag != 1) {
		child = child->sibling;
	}
	return child;
}

// finds the previous proc_t in the threaded tree
proc_t* findprev (proc_t* current) {
	proc_t* lookfor = current;
	// find the parent first
	while (current->pflag!=1) {
		current = current->sibling;
	}
	proc_t* parent = current->sibling; // make current the parent
	current = parent->child; // make current the child and check if the child after this one is what we are looking for
	if (current==lookfor) {
		return parent;
	} else {
		while (current->sibling!=lookfor) {
			current = current->sibling;
		}
		return current;
	}
}

// causes new process (progeny of the first) to be created
// D4 contains address of processor state area at time this instruction is executed
// processor state should be used as initial state for newly created process
// process executing creatproc continues to exist and execute
// if new process cannot be created because of lack of resources, error code of -1 returned in D2
// else D2 returns 0
void creatproc() {
	state_t* d4 = (state_t*)sysarea->s_r[4];
	proc_t* head = headQueue(RQ);
	proc_t* new_proc = allocProc();
	if (new_proc == (proc_t*)ENULL){
		sysarea->s_r[3] = -1;
	} else {

		new_proc->p_s = *d4;
		insertProc(&RQ, new_proc);
		// if child of head is null, insert child
		if (head->child == (proc_t*)ENULL) {
			head->child = new_proc;
			new_proc->sibling = head;
			new_proc->pflag = 1;
		// if child of head is not null, then head's child now has a sibling
		} else if (head->child != (proc_t*)ENULL) {
			proc_t* temp = head->child;
			// find the youngest child
			proc_t* youngest = findyoungest(temp);	
			new_proc->sibling = youngest->sibling;
			new_proc->pflag = 1;
			youngest->sibling = new_proc;
			youngest->pflag = -1;
		}
	}
}

// search and destroy performs recursion on the children
void searchanddestroy(proc_t* proc2kill) {
	// remove proc from runqueue and free it
	// also need to take care of siblings
	if (proc2kill->child == (proc_t*) ENULL) {
		// several cases to handle
		// if this is the root
		if (proc2kill->sibling == (proc_t*)ENULL) {
		// if there is only one child 
		} else if (proc2kill->sibling->child == proc2kill) {
			proc2kill->sibling->child = (proc_t*)ENULL;
		// if proc2kill is the oldest child
		} else {
			proc_t* previous = findprev(proc2kill);
			if (previous->child == proc2kill) {
				previous->child = proc2kill->sibling;
			} else {
				if (proc2kill->pflag==1) {
					previous->pflag = 1;
				}
				previous->sibling = proc2kill->sibling;
			}
		}	
		outBlocked(proc2kill);
		outProc(&RQ, proc2kill);
		freeProc(proc2kill);	
	} else {
		// killing all children
		while (proc2kill->child!= (proc_t*)ENULL) {		
			searchanddestroy(proc2kill->child);
		}
		// after killing children, have to kill current as well
		// if no sibling -> means proc2kill is a root process
		if (proc2kill->sibling != (proc_t*)ENULL) {
			searchanddestroy(proc2kill);

		} else {
			outProc(&RQ,proc2kill);
			// include outBlock to take processes off semaphore queues
			outBlocked(proc2kill);
			freeProc(proc2kill);
		}
	}

}

// causes executing processes to cease to exist; any progency of the processes are terminated; execution doesn't complete until everything is terminated 
void killproc() {	
	proc_t* proc2kill = headQueue(RQ);
	// kill process and children
	searchanddestroy(proc2kill);
	schedule();
}

// Interpreted by the nucleus as a set of V and P operations atomically applied to a group of semaphores
// Each semaphore + operation is described in a vpop struct
// D4 contains address of vpop vector
// D3 contains number of vpops in vector
void semop() {
	int to_run = 0;
	proc_t* head = headQueue(RQ); 
	int d3 = sysarea->s_r[3]; // d3 number of vpops
	vpop* d4 = (vpop*) sysarea->s_r[4]; // d4 is the address of the vpop structure
	int i;
	for (i=0; i<d3; i++) {
		int* current_sem = d4[i].sem;
		int operation = d4[i].op;
		// Perform operation on the semaphore in question
		int old_sem = *(current_sem);
		int new_sem = old_sem + operation;
		// this means that semaphore is no longer active
		if (operation == -1) {
			if (new_sem < 0) {
				head->p_s = *sysarea;
				insertBlocked(current_sem, head);
				to_run = 1;
			}	
		} else if (operation == 1 && old_sem <0) { // and old_sem<0?
	//		if (new_sem >= 0) {
				proc_t* removed = removeBlocked(current_sem);
				if (removed!= (proc_t*)ENULL && removed->qcount == 0) {
					insertProc(&RQ, removed);
				}
	//		}
		}

		*(d4[i].sem)+=d4[i].op;
	}
	if (to_run==1) {
		removeProc(&RQ);
		schedule();
	}
	
}

// if executed, error condition and halt
void notused() {
	HALT();
}

// three pieces of info sypplied to nucelus
// (1) type of trap: 0= program, 1=memory, 2=sys
// area where the old processor state is to be stored when trap occurs; address in D3
// area of new processor state; address in d4
void trapstate(int num) {
	proc_t* head = headQueue(RQ);	
	if (num == 0) {
		if (head->newprog != (state_t*)ENULL) {
			killproc();
		} else {
			head->oldprog = (state_t*)sysarea->s_r[3];
			// how does the computer know now to overwrite this specific area (e.g. sysarea->s_r[3]?
			//*prog_store = *progarea;
			head->newprog = (state_t*)sysarea->s_r[4];
			//*newprog_store = *prognew_area;	
		}
	} else if (num == 1) {
		if (head->newmm != (state_t*)ENULL) {
			killproc();
		} else {
			head->oldmm = (state_t*)sysarea->s_r[3];
			head->newmm = (state_t*)sysarea->s_r[4];
		}
	} else if (num == 2) {
		if (head->newsys != (state_t*)ENULL) {
			killproc();
		} else {
			head->oldsys = (state_t*)sysarea->s_r[3];
			head->newsys = (state_t*)sysarea->s_r[4];
		}
	}
	
}

// when executed, causes CPU time used by process executing instruction to be placed in D2
// Means nucleus must record amount of processor time used by each process
void getcputime() {
	proc_t* head = headQueue(RQ);
	int time = cpu_timer(head);
	sysarea->s_r[2] = head->cputime + time;
		
}


