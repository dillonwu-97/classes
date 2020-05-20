/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

// Compute perfect numbers
// Takes a command line arg
#include "defs.h"

// initializing variables
key_t key;
int shmid, msgid, start, perfecto, proc_id;
sh_mem *mem;
pid_t id;

// Algorithm for computing perfect numbers
// 1 = True, 0 = False
int is_perf (int num) {

	int sum = 1;
	for (int i = 2; i*i <= num; i++) {
		if (num % i == 0) {
			if (i * i == num) {
				sum += i;
			} else {
				sum = sum + i + num/i;
			}
		}
	}
	if (sum == num) {
		return 1;
	} else {
		return 0;
	}

}

//power function: x^y
int power (int x, int y) {
	int result = 1;
	for (int i = 0; i < y; i++) {
		result = x * result;
	}
	return result;
}

// calculates the index of array containing bit for j
// e.g. 2^6 = 64, but need to | with 100000 which is 32 (or just ignore 1?)
int whichint (int j ) {
	int i = (j-1) / 32;
	return i;
}

// calculates the 0-31 bit position for j
int whichbit (int j) {
	int i = (j-1)%32 ; //so #1 maps to index 0 
	return i;
}

// i = int position, j = bit position, int*k = bitmap
void flipperoo (int i, int j, int*k) {
	k[i] = k[i] | power(2,31-j);
}

// Tests if the bit representing the number is 0 or 1 -> use and operation with 1
int testbit(int i, int j) {
	if (i & (1 << (31 - j))) {
		return 1;
	} else {
		return 0;
	}
}

void terminate(int signal) {
	mem->process[proc_id].pid = 0;
	mem->process[proc_id].count = 0;
	mem->process[proc_id].tested = 0;
	mem->process[proc_id].skipped = 0;
}

int main(int argv, char* argc[]) {
	
	// Initializing shared memory
	mem = mem_initialize();

	// Initializing message queue
	msgid = msg_initialize();
	message *mes = malloc(sizeof(message));

	// Sending process id to manage.c
	mes->mtype = PID_FLAG;
	id = getpid();
	mes->number = id;
	if ((msgsnd(msgid, mes, sizeof(message), 0))== -1) {
		perror("Compute message not sent");
		exit(1);
	}
	//printf("Compute sent a message \n");
	// receiving proc index
	if ((msgrcv(msgid, mes, sizeof(message), PROC_LOC_FLAG, 0)) == -1){
		perror("Compute message not received");
		exit(1);
	}
	//printf("%s\n", "Compute recieved a message");
	proc_id = mes->number;
	//printf("proc_id is: %d\n", proc_id);

	// Replacing INTR signal
	struct sigaction sig;
	sig.sa_handler = terminate;
	sigaction(SIGINT, &sig, NULL);


	// Detecting all of the perfect numbers and sending them to manage.c
	start = atoi(argc[1]);
	//printf("%s\n", "BOOKMARKBOOKMARKBOOKMARKBOOKMARKBOOKMARK");
	int k = start;
	int n = 0;
	while (k < ITER_SIZE || n < start) {
		//printf("%d\n", k);
		if (k >= ITER_SIZE) {
			printf("%d\n", n);
			n++;
		}
		int i = whichint(k);
		int j = whichbit(k);
		if (testbit(mem->num[i], j) == 0) {
			//printf("int is: %d\n", i);
			//printf("bit is: %d\n", j);
			mem->process[proc_id].tested++;
			if ( (perfecto = is_perf(k)) == 1) {
				mes->mtype = PERF_FLAG;
				mes->number = k;
				//printf("PERFECTO %d\n", k);
				if ((msgsnd(msgid, mes, sizeof(message), 0)) == -1) {
					perror("Perfect number sending failed");
					exit(1);
				}
				mem->process[proc_id].count++;
			}
			flipperoo(i,j,mem->num);
		} else{
			//printf("%s\n", "skipped");
			mem->process[proc_id].skipped++;
		}
		k++;
		
	}
	
	// Clean up and terminate process 
	if (n == start) {
		//printf("%s\n", "Reached");
		terminate(SIGINT);
	}
	

	return 0;
}
