/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/msg.h>
#include <string.h>	
#include <signal.h>


// 2^25 / 32
#define ITER_SIZE 33554432
#define ARRAY_SIZE 1048576
#define PERF_SIZE 20 // perfect number size and process structure size
#define PERM_FILE (S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH) // THIS SHOULD be 0666
#define KEY 68232
#define PID_FLAG 1 // THIS DOESN'T WORK IF ONE OF THE FLAGS ARE 0 FOR SOME REASON
#define PERF_FLAG 2
#define KILL_FLAG 3
#define PROC_LOC_FLAG 4



typedef struct {
	pid_t pid;
	int count; // Number of perfect numbers found
	int tested; // Number of candidates tested
	int skipped; // Number of candidates skipped
} p_struct;

typedef struct {
	int num[ARRAY_SIZE];
	int ints[PERF_SIZE];
	p_struct process[PERF_SIZE];
} sh_mem;

typedef struct {
	long mtype;
	int number;
} message;

int glob; // store mem seg id
// initializing memory segment in all three processes?

sh_mem* mem_initialize() {
	// Intializing key variables
	key_t key;
	int shmid;
	sh_mem *mem = malloc(sizeof(sh_mem));

	key = 68232;
	
	shmid = shmget(key, sizeof(sh_mem), IPC_CREAT | PERM_FILE);
	
	//printf("Shmid is: %d\n", shmid);
	if ( shmid == -1) {
		perror("error");
		exit(1);
	}
	glob = shmid;
	mem = shmat(shmid, NULL, 0);
	return mem;
}


int msg_initialize() {
	//Initializing the variables
	key_t key;
	int msgid;

	key = 268232;
	msgid = msgget(key, IPC_CREAT | 0666);
	return msgid;
}	


