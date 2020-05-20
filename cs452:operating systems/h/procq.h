/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

// link descriptor type 
typedef struct proc_link {
	int index;
	struct proc_t *next;
} proc_link;

// process table entry type
typedef struct proc_t {
	proc_link p_link[SEMMAX]; // pointers to next entries
	state_t p_s; // processor state of the process
	int qcount; // number of queues containing this entry
	int *semvec[SEMMAX]; // vector of active semaphores for this entry
} proc_t;

