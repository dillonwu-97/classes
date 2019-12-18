/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/types.h>
#include <ctype.h>
#include <sys/wait.h>


void parser () {
	int pipe1[2];
	pipe (pipe1);
	pid_t returnValue = fork();
	
	//child process has return value of 0
	if (returnValue == 0) {

		int pipeSup[2]; // creating the second pipe
		pipe(pipeSup);
		pid_t porker = fork(); // run fork

		if (porker == 0) { //in the child
			
			//set up
			int buffersize = 34;
			char buffer[buffersize];
			char bufferBEFORE [ buffersize];
			int counter = 1;
			char c;
			bufferBEFORE[0] = '1'; //CANARY

			//closing more pipes
			close(pipe1[0]);
			close(pipe1[1]);

			//closing and duplicating pipes
			close (pipeSup[1]);//nothing to write so close write
			FILE* read_from = fdopen(pipeSup[0], "r"); // read to stdout
			while (fgets(buffer, buffersize, read_from) != NULL) { // reading from the pipe
				if (strcmp(buffer, bufferBEFORE) != 0) {
					if (bufferBEFORE[0] != '1') { // check if previous buffer is empty
						printf("%-5d%s", counter, bufferBEFORE);
					}
					counter= 1;
					strcpy(bufferBEFORE, buffer);
				} else {
					counter++;
				}
			}
			printf("%-5d%s", counter, bufferBEFORE); // one more after reading all the nulls
			close (pipeSup[0]); // closing the read
			fclose(read_from);

		} else {

			//exec sorting
			//idea for third process is to run fork again within the child process
			close(pipe1[1]); // close the original write; don't need it
			dup2(pipe1[0], STDIN_FILENO);
			close(pipe1[0]); //close original read

			close(pipeSup[0]); //close second read
			dup2(pipeSup[1], STDOUT_FILENO); //redirect out to pipe
			close(pipeSup[1]); //close second write
			
			execlp ("sort", "sort", (char*)NULL); // reads from stdin and writes to stdout
			wait(&porker);
			//printf("%s\n", "First Child done");
		}
	
	} else {
	//this is the parent process
		//exec parsing to words
		close(pipe1[0]); // close the original read
		//dup2(pipe1[1], STDOUT_FILENO); // instead of writing to standard out, write to the pipe
		FILE* write_to = fdopen(pipe1[1], "w");
		char c;
		int count = 0;
		int flag = 1; //keep track of the number of characters added
		char buffer[34]; //letters and new line, null byte 
		int delimiter = 3;
		while ((c = fgetc(stdin))!= EOF) { //get characters from standardin
			if (count > 31) {
				flag = 0;
			}
			c = tolower(c);
			if (isalpha(c)!=0 || c == '\n'){
				//regular character
				if (c == '\n') {
					buffer[count] = c;
					buffer[++count] = '\0';
					//printf("%s\n", buffer);
					if (count > 4) {
						fputs(buffer, write_to);
						fflush(write_to); //need to fflush buffer
					}
					count = 0;
					flag = 1;
					delimiter = 3;
				} else {
					if (flag == 1 && delimiter == 3) {
						buffer[count] = c;//put characters into the pipe
						count++;

					}
				}
			//delimiting words if a nonalphabetic character is found until '\n' is spotted
			} else if (isalpha(c) == 0) {
				//printf("%s\n", "test");
				delimiter = 4;
			}
		}
		close(pipe1[1]); // close the original write
		fclose(write_to);
		wait(&returnValue);
	}
}


int main () {
	parser();
	//printf("%s\n", "Finished reading all the words");
	exit(0);


}
