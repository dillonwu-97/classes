#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <syslog.h>
#include <unistd.h>


/* Oooh, this looks promising... */
void acidburn(void)
{
	uid_t uid = geteuid();
	setresuid (uid, uid, uid);
	system ("/bin/bash");
}


int zerocool(char *fmt)
{
	char buf[BUFSIZE];
	memset (buf, 0, sizeof(buf));
	printf ("Hi! I am z3r0c00l.\n");
	snprintf (buf, sizeof(buf)-1, "Your nick is: %s\n", fmt);
	printf (buf);
	return 0;
}



int main(int argc, char **argv)
{
	char boundary[1024];

	memset (boundary, 0x44, sizeof(boundary));

	if (argc != 2)
	{
		printf ("z3r0c00l: Please specify your nickname !\n");
		return -1;
	}
	printf ("The length of your nick is %d characters\n", strlen(argv[1]));
	
	zerocool (argv[1]);
	return 0;	
}


