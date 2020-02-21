/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu
procq.c creates the abstraction of queues of processes.
It keeps track of the processes as well as information about them like which 
queues the processes are on, how many queues they are on, etc.
The code also contains functions which allow for the manipulation of these processes
within the queues.
*/

#include "../h/types.h"
#include "../h/const.h"
#include "../h/procq.h"

// Global variables
proc_t procTable[MAXPROC];
proc_link procFree_h;
char buf[128];	/* nonrecoverable error message before shut down */

// Inserts element pointed to by p into the process queue where tp contains the pointer to the tail
// Updates the tail pointer 
// If process is already in SEMMAX queues, call panic function
void insertProc(proc_link *tp, proc_t *p) {
	int j;	
	for (j = 0; j < SEMMAX; j++) {
		if (p->p_link[j].next == (proc_t*)ENULL) {
			if (tp->next == (proc_t*)ENULL) {
				tp->next = p;
				tp->index = j;
				// This is to make sure that the data can be transferred later one
				p->p_link[ j ].next = p;
				p->p_link[ j ].index = p->qcount;
				p->qcount++;
				break;
			} else {		
				// Grab the tail entry
				proc_t* tail = tp->next; 
				// This replaces proc_link so that new tail points to head
				p->p_link[ j ].next = tail->p_link [ tp->index ].next;
				p->p_link[j].index = tail->p_link[tp->index].index;
				// Change current tail so that tail's next points to p
				tail->p_link [ tp->index ].next = p;
				tail->p_link [ tp->index ].index = j;
				// Set tail pointer to point to p
				tp->next = p;
				tp->index = j;
				p->qcount++;
				break;
			}
		}
		if (j == SEMMAX-1) {
			panic("Already in SEMMAX queues");
		}
	}
	
}

// Returns a pointer to the process table entry at the head of the queue
proc_t* headQueue(proc_link tp) {
	proc_t* tail = tp.next; // Assuming that the tail is where the tp.next points to
	proc_t* head = tail->p_link[ tp.index ].next; // Go to the proc_link that points to the next head
	return head;
}

// Remove first element from process queue whose tail is pointed to by tp
// Return ENULL if queue was initially empty, else return the pointer to the removed element
// Update the pointer to the tail of queue if needed
proc_t* removeProc(proc_link *tp) {

	proc_t* head = (proc_t*)ENULL;
	if (tp->next == (proc_t*)ENULL) {
		return head;
	}
	if (tp->next->p_link [tp->index ].next == tp->next) {
		// deallocate memory if there is just one item on the queue
		head = tp->next->p_link[ tp->index ].next;		
		tp->next->p_link[ tp->index ].next = (proc_t*)ENULL;
		tp->next->p_link[ tp->index ].index = -1;
		tp->next = (proc_t*)ENULL;
		tp->index = -1;
	} else {
	//if (tp->next->p_link[ tp->index ].next != tp->next)
		proc_t*tail = tp->next;
		head = tail->p_link[ tp->index ].next;
		int important_index = tail->p_link[ tp->index ].index;  // important index is for knowing how to get to the proc_t after head
		tail->p_link[ tp->index ].next = head->p_link[ important_index ].next;
		tail->p_link[ tp->index ].index = head->p_link[ important_index ].index;

		// Resetting the pointers and returning head
		head-> p_link[ important_index ].next = (proc_t*)ENULL;
		head-> p_link[important_index].index = -1;
	}

	return head;
}


// Remove the process table entry pointed to by p from the queue whose tail is pointed to by tp
// Update the pointer to the tail of queue if needed
// If desired entry is not in defined queue, return ENULL, else return p
proc_t* outProc(proc_link *tp, proc_t *p) {

	// Saving basic information about the tp
	proc_t* return_value = (proc_t*)ENULL;
	proc_t* tail = tp->next; // Save this for comparison in case of tail removal
	proc_t* head = tail->p_link[ tp->index ].next;	// used to test if queue has only one element
	proc_link temp_link = *tp;
	proc_t* temp_proc = tp->next;
	int temp_num = tp->index;
	proc_t* temp_proc_next = temp_proc->p_link[temp_num].next;
	//proc_t* test = tp->next->p_link[temp_num].next;
	
	// if temp_proc_next == tail, then we have gone full circle
	while( temp_proc_next != p ){
		if (temp_proc_next == (proc_t*)ENULL || temp_proc_next == tail) {
			break;
		}
		temp_link = temp_proc->p_link [ temp_num ]; // update the current view
		temp_proc = temp_link.next;
		temp_num = temp_link.index;
		temp_proc_next = temp_proc->p_link[temp_num].next; // update the look ahead		
	}
	if (temp_proc_next == (proc_t*)ENULL) {
		return (proc_t*)ENULL;
	}

	if (temp_proc_next == tail && temp_proc_next != p) {
		return (proc_t*)ENULL;
	}
	// if last element and is equal to p; remove last element and set free
	proc_t* current;
	if (head == tail) {
		current = removeProc(tp);
	} else {
		// If they are equal, then remove the item from the queue
		// Similar to remove_proc
		current = temp_proc_next;
		proc_t* before = temp_proc;
		proc_t* after = current->p_link[before->p_link[temp_num].index].next;
		
		// Change the pointers so that before points to after, and current points to nothing
		int current_index_before_change = current->p_link[ before->p_link [temp_num].index ].index;
		current->p_link[ before->p_link [temp_num].index ].index = -1; // deallocation
		current->p_link[ before->p_link [temp_num].index ].next = (proc_t*)ENULL; // deallocation
		before->p_link[ temp_num ].index = current_index_before_change;
		before->p_link[ temp_num ].next = after;
		// Handling case where tail is removed
		// this might be wrong, but should be correct
		if (current == tail) {
			tp->next = before;
			tp->index = temp_num;
		}
	}
	return current;

}


// Returns ENULL if procFree is empty
// Else remove an element from procFree list and return a pointer to it
proc_t* allocProc() {
	if (procFree_h.next == (proc_t*)ENULL) {
		return (proc_t*)ENULL;
	} else {
		proc_t* return_value = procFree_h.next;
		proc_link to_after_return = return_value->p_link [0];
		proc_t * after_return = to_after_return.next;
		procFree_h.next = after_return;
		return_value->p_link[0].next= (proc_t*)ENULL;
		return return_value;
	}
}

// returns element pointed to by p into procFree list
// I think freeProc resets all information of p
void freeProc(proc_t *p) {
	int j;
	p->p_link[0].index = procFree_h.index;
	p->p_link[0].next = procFree_h.next;
	procFree_h.next = p;
	p->semvec[0] = (int*)ENULL;
	p->qcount = 0;
	
	// resetting p for case i = (1,SEMMAX); 0 case was handled above
	for (j = 1; j < SEMMAX; j++) {
		p->p_link[j].index = -1;
		p->p_link[j].next = (proc_t*)ENULL;
		p->semvec[j] = (int*)ENULL;
	} 
}

// Initializes the procFree list to contain all the elements of the array procTable
void initProc() {
	int i,j;
	// handling nonedge cases
	for (i = 0; i < MAXPROC; i++) {		
		for (j = 0; j < SEMMAX; j++) { 
			procTable[i].p_link[j].next = (proc_t*)ENULL;
			procTable[i].p_link[j].index = -1;
			procTable[i].semvec[j] = (int*)ENULL;
		}
		procTable[i].qcount = 0;
	}
	// handling the 0 cases
	for (i = 0; i < MAXPROC-1; i++) {		
		procTable[i].p_link[0].next = &procTable[i+1];
		procTable[i].p_link[j].index = -1;
	}
	// handling the procFree pointer
	procFree_h.next = &procTable[0];
	procFree_h.index=-1;

}


panic(s)
register char *s;
{
	register char *i=buf;

	while ((*i++ = *s++) != '\0');

	HALT();

        asm("	trap	#0");
}


















