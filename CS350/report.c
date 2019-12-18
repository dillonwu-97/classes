/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */


#include "defs.h"
// Initializing variables
key_t key;
int shmid, position, rec, msgid;
sh_mem *mem;

// Send manage an interrupt signal
void interr() {
	message *mes = malloc(sizeof(message));
	mes->mtype = KILL_FLAG;
	for (int i = 0; i < PERF_SIZE; i++) {
		mes->number = i;
		if (mem->process[i].pid == 0) {
			break;
		}
		//printf("%s\n", "Report sent");
		if((msgsnd(msgid, mes, sizeof(message), 0)) == -1) {
			perror("Report didn't send");
			exit(1);
		}

	}
}

void report(sh_mem *mem) {

	// Track where we are
	int proc_pos = 0, perf_pos = 0, pid, tested, skipped, found;
	
	printf("Perfect Numbers: ");
	while (mem->ints[perf_pos] != 0) {
		printf("%d ", mem->ints[perf_pos]);
		perf_pos++;		
	}
	printf("\n");

	printf("%-10s%-10s%-10s%-10s\n", "PID", "TESTED", "SKIPPED", "FOUND");

	
	while (mem->process[proc_pos].pid != 0) {
		pid = mem->process[proc_pos].pid;
		tested = mem->process[proc_pos].tested;
		skipped = mem->process[proc_pos].skipped;
		found = mem->process[proc_pos].count;
		printf("%-10d%-10d%-10d%-10d\n", pid, tested, skipped, found);
		proc_pos ++; // increment

	}


}



int main (int argc, char * argv[]) {
	
	// Memory and message queue initialized
	mem = mem_initialize();
	msgid = msg_initialize();

	int flag = 0;
	if (argc >= 2 && (strcmp(argv[1], "-k") == 0)) {
		flag = 1;
		report(mem);
		interr();
	}

	if (flag == 0) {
		report(mem);
	}
	
	return 0;

}