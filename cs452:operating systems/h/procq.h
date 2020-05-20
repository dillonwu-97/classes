/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

// link descriptor type 
typedef struct proc_link {
	int index;
	struct proc_t *next;
} proc_link;

// process table entry type
typedef struct proc_t proc_t;
struct proc_t {
	proc_link p_link[SEMMAX]; // pointers to next entries
	state_t p_s; // processor state of the process
	int qcount; // number of queues containing this entry
	int *semvec[SEMMAX]; // vector of active semaphores for this entry
	proc_t* child; // pointer to child process
	proc_t* sibling; // pointer to sibling process OR if at the end of the row, this pointer actually points to the parent
	int pflag; // parent flag
	long cputime;
	long timeofday;
	state_t* oldsys;
	state_t* newsys;
	state_t* oldprog;
	state_t* newprog;
	state_t* oldmm;
	state_t* newmm;
};

