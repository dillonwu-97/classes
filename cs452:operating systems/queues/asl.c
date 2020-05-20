/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu
asl.c creates the active semaphore list(ASL) abstraction. 
A semaphore is active if there is at least one process blocked on it.  
The code contains functions to allocate and deallocate semaphores, as well
as ways to shift their positions around in memory.
*/
#include "../h/const.h"
#include "../h/types.h"
#include "../h/procq.e"
#include "../h/asl.e"


semd_t *semdFree_h; // ENULL terminated free list with head pointed to by semdFree_h
semd_t semdTable[MAXPROC]; // array of semd_t's
semd_t *semd_h = (semd_t*)ENULL; // head of active semaphore list

// helper function to find the semaphore with address semAdd
// returns the semd_t with the semAdd if it exists; if it doesn't return NULL -> NULL could mean empty semd_h OR nonempty semd_h w/o semAdd
semd_t* find_semaphore(int* semAdd) {
	semd_t* temp = semd_h;
	while (temp!= (semd_t*)ENULL && temp->s_semAdd != semAdd) {
		temp = temp->s_next;
	}
	return temp;
	
}

// finds a good spot for semAddress in proc_t
void place_sem_in_p(int *semAdd, proc_t* p) {
	int j;
	for (j = 0; j < SEMMAX; j++) {
		if (p->semvec[j] == (int*)ENULL) {
			p->semvec[j] = semAdd;
			break;
		}			
	}
}

// gets rid of the semAddress in proc_t
void remove_sem_from_p (int* semAdd, proc_t* p) {
	int j;
	for (j = 0; j < SEMMAX; j++) {
		if (p->semvec[j] == semAdd) {
			p->semvec[j] = (int*)ENULL;
			break;
		}
	}
	//p->qcount--;
}


// Insert process table entry pointed to by p at tail of process queue associated with semaphore whose address is semAdd
// If semaphore not active --> allocate new descriptor from free list, insert in the ASL, and initialize all fields
// If semaphore descriptor needs to be allocated and free list is empty --> TRUE, else FALSE
int insertBlocked (int *semAdd, proc_t* p) {
	int j;	
	// If free list is empty and new semaphore descriptor needs to be allocated
	if (semdFree_h == (semd_t*)ENULL) {
		return TRUE;
	}
	// Finding the semaphore with address semAdd
	semd_t* entry = find_semaphore(semAdd);
	if (entry != (semd_t*)ENULL) {
		// Insert process table entry to tail of pqueue
		insertProc(&(entry->s_link), p);
		// Save semaphore address in p
		place_sem_in_p(semAdd, p);
		return FALSE;
	// If semaphore with address semAdd does not exist
	} else {
		// Moving free pointer		
		semd_t *new_sem = semdFree_h;		
		semdFree_h = semdFree_h->s_next;
		// initializing fields 
		new_sem->s_semAdd = semAdd;
		insertProc(&(new_sem->s_link),p);
		place_sem_in_p(semAdd, p);		
		// semd_h is empty
		if (semd_h == (semd_t*)ENULL) {
			semd_h = new_sem;
			new_sem->s_next = (semd_t*)ENULL;
			new_sem->s_prev = (semd_t*)ENULL;
		} else {
		// semd_h is not empty; insert new_sem at head
			new_sem->s_next = semd_h;
			new_sem->s_prev = (semd_t*)ENULL;
			semd_h->s_prev = new_sem;
			semd_h = new_sem;
			
			/*
semd_t *temp_head = semd_h;
			while (new_sem->s_semAdd > temp_head->s_semAdd && temp_head->s_next!= (semd_t*)ENULL) {
				temp_head = temp_head->s_next;
			}
			if (temp_head->s_semAdd < new_sem->s_semAdd) {
				new_sem->s_next = (semd_t*)ENULL;
				temp_head->s_next = new_sem;
				new_sem->s_prev = temp_head;
			// less than first element
			} else if (temp_head->s_prev == (semd_t*)ENULL) {
				new_sem->s_next = temp_head;
				new_sem->s_prev = (semd_t*)ENULL;
				temp_head->s_prev = new_sem;
			// greater than ith element, less than i+1th element for 0<i<max
			} else {
				new_sem->s_prev = temp_head->s_prev;				
				temp_head->s_prev->s_next = new_sem;
				temp_head->s_prev = new_sem;
				new_sem->s_next = temp_head;
			}
			*/
		}
		return FALSE;		
	}
	
	
}

// Search ASL for a descriptor of this semaphore; if none --> return ENULL
// Else, remove the first process table entry from process queue of the appropriate semaphore descriptor and return pointer to it
// If process queue for this semaphore becomes empty, remove descriptor from ASL and insert it in free list of semaphore descriptors
proc_t* removeBlocked(int *semAdd) {
	semd_t* location = find_semaphore(semAdd);
	if (location == (semd_t*)ENULL) {
		return (proc_t*)ENULL;
	} else {
		proc_t* return_value = removeProc(&location->s_link);
		remove_sem_from_p(semAdd, return_value);
		// process queue for this semaphore is empty		
		if (location->s_link.next == (proc_t*)ENULL) {
			// deallocate
			// if descriptor is first entry
			if (semd_h == location) {
				semd_h = location->s_next;			
			} else {
				location->s_prev->s_next = location->s_next;
			}
			// handling case where descriptor is last entry
			if (location->s_next != (semd_t*)ENULL) {			
				location->s_next->s_prev = location->s_prev;
			}
			if (semdFree_h == (semd_t*)ENULL) {
				semdFree_h = location;
				semdFree_h->s_next = (semd_t*)ENULL;
			} else {
				semd_t* placeholder = semdFree_h;
				location->s_next = placeholder;
				placeholder->s_prev = location;
				//location->s_next = semdFree_h->s_next;
				semdFree_h = location;
			}			
			location->s_prev = (semd_t*)ENULL;
			location->s_semAdd = (int*)ENULL;
		}
		return return_value;
	}
	
}

// Remove process table entry pointed to by p from queues associated with appropriate semaphore on ASL
// If desired entry does not appear in process queues return ENULL; else return p
proc_t* outBlocked (proc_t *p) {
	proc_t* return_value = (proc_t*)ENULL;
	proc_t* return_dummy;
	semd_t* temp = semd_h;
	semd_t* found_p_pointer;
	while (temp != (semd_t*)ENULL) {
		return_dummy = outProc(&(temp->s_link), p);
		if (return_dummy == p) {
			*(temp->s_semAdd)= *(temp->s_semAdd) + 1;
			return_value = return_dummy;
			// clearing semvec
			int j;
			for (j = 0; j < SEMMAX; j++) {
				if (return_value->semvec[j] == temp->s_semAdd) {
					return_value->semvec[j] = (int*)ENULL;
					break;
				}
			}
			// deallocation for this specific sem
			found_p_pointer = temp->s_next;
			if (temp->s_link.next == (proc_t*)ENULL) {
				// if descriptor is first entry
				if (semd_h == temp) {
					semd_h = temp->s_next;			
				} else {
					temp->s_prev->s_next = temp->s_next;
				}
				// handling case where descriptor is last entry
				if (temp->s_next != (semd_t*)ENULL) {			
					temp->s_next->s_prev = temp->s_prev;
				}
				if (semdFree_h == (semd_t*)ENULL) {
					semdFree_h = temp;
					semdFree_h->s_next = (semd_t*)ENULL;
				} else {
					semd_t* placeholder = semdFree_h;
					temp->s_next = placeholder;
					placeholder->s_prev = temp;
					//temp->s_next = semdFree_h->s_next;
					semdFree_h = temp;
				}			
				temp->s_prev = (semd_t*)ENULL;
				temp->s_semAdd = (int*)ENULL;
			}
			temp = found_p_pointer;
		} else {
			temp = temp->s_next; 
		}
	}
	return return_value;
	
}

// Return pointer to process table entry that is at head of process queue associated with semaphore semAdd
// If list is empty, return ENULL
proc_t* headBlocked(int* semAdd) {
	semd_t *temp = find_semaphore(semAdd);
	proc_t* return_value;
	if (temp == (semd_t*)ENULL) {
		return_value = (proc_t*)ENULL;
	} else {
		return_value = headQueue(temp->s_link);
	}	
	return return_value;
	
}

// Initialize semaphore descriptor free list
void initSemd(){
	int i, j;
	// initializing the variables on semd_t structure that don't require handling edge cases
	for (j = 0; j < MAXPROC; j++) {
		semdTable[j].s_semAdd = (int*)ENULL;
		semdTable[j].s_link.index = -1;
		semdTable[j].s_link.next = (proc_t*)ENULL;
	}
	// Starting at i = 1, ending at MAXPROC-1 so need to handle edge cases afterwards
	for (i = 1; i < MAXPROC-1; i++) {
		semdTable[i].s_next = &semdTable[i+1];
		semdTable[i].s_prev = &semdTable[i-1];
	}
	// Handling the last entry
	semdTable[MAXPROC-1].s_next = (semd_t*)ENULL;
	semdTable[MAXPROC-1].s_prev = &semdTable[MAXPROC-2];
	// Handling the first entry
	semdTable[0].s_next = &semdTable[1];
	semdTable[0].s_prev = (semd_t*)ENULL;
	// Handling the free pointer
	semdFree_h = &semdTable[0];
}

// Determine if any semaphores are on the ASL
// FALSE if ASL empty, else TRUE
int headASL(){
	if (semd_h == (semd_t*)ENULL) {
		return FALSE;
	} else {
		return TRUE;
	}
}
