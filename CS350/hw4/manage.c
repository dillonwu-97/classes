/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

// Maintain shared memory segment
// Compute processes post their results here 
// Keeps track of active "comput" processes and tells them when to terminate 

#include "defs.h"
// Initializing variables
key_t key;
// perf_position = for perfect numbers array; proc_position = for process array
int shmid, perf_position = 0, proc_position = 0, rec, msgid;
sh_mem *mem;

void terminate(int signal) {
	for (int i = 0; i < PERF_SIZE; i++) {
		if (mem->process[i].pid == 0) {
			break;
		} else {
			if ((kill(mem->process[i].pid, SIGINT))== -1) {
				perror("Cannot be killed buzz buzz");
				exit(1);
			}
		}
	}

	sleep(5);

	// Detach memory
	if ((shmdt(mem)) == -1) {
		perror("I'll be back");
		exit(1);
	}

	// Erase memory
	if ((shmctl(glob, IPC_RMID, 0)) == -1) {
		perror("Not today");
		exit(1);
	}

	// Clear message queue
	if ((msgctl(msgid, IPC_RMID, NULL)) == -1) {
		perror("Asta lavista");
		exit(1);
	}

	
}


int main (int argc, char* argv[]) {
	//printf("%s\n", "Starting receive messages");

	// Memory initialized
	mem = mem_initialize();
	memset(mem->num, 0, sizeof(mem->num)); // reset to 0
	memset(mem->ints, 0, sizeof(mem->ints));
	memset(mem->process, 0, sizeof(mem->process));


	// Message queue initialized
	msgid = msg_initialize();
	message *mes = malloc(sizeof(message));

	// Replacing interrupt signal
	struct sigaction sig;
	sig.sa_handler = terminate;
	sigaction(SIGINT, &sig, NULL);



	// Receiving messages
	while(1) {

		if (msgrcv(msgid, mes, sizeof(message), -3,0) == -1) {
			exit(1);
		}
		//printf ("%ld\n", mes->mtype);
		if (mes->mtype == PID_FLAG) { // if message is a pid
			rec = mes->number;
			//printf("position is %d\n", proc_position);
			//printf("the pid received is: %d\n", rec);
			mem->process[proc_position].pid = rec;
			//printf("pid is %d\n", mem->process[proc_position].pid);
			mem->process[proc_position].count = 0;
			mem->process[proc_position].tested = 0;
			mem->process[proc_position].skipped = 0;
			
			mes->mtype = PROC_LOC_FLAG; // khabib send location
			mes->number = proc_position; 
			if ((msgsnd(msgid, mes, sizeof(message), 0)) == -1) {
				perror("Manage message not sent");
			}
			proc_position++;

		} else if (mes->mtype == PERF_FLAG) { // if message is a perfect number
			rec = mes->number;
			//printf("the perfect number received is: %d\n", rec);
			mem->ints[perf_position] = rec;
			perf_position++;
		}  else if (mes->mtype == KILL_FLAG) {
			//printf("%s\n", "Trying to kill");
			if ((kill(mem->process[mes->number].pid, SIGINT))== -1) {
				perror("Cannot be killed buzz buzz");
				exit(1);
			}
		}
		
	}
	// Receiving a message from compute.c
	// receiving process id
	// Handling multiple message queues?	





	return 0;
}
