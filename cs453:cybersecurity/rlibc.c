#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#define _GNU_SOURCE
#define __USE_GNU
#include <unistd.h>

void flugeldufel(char *s)
{
	char buf[BUFLEN];
	strcpy(buf, s);

	printf ("Wrote %d bytes, master.\n", strlen(buf));
}

void agonistic_heaven(void)
{
	static uid_t uid;
	uid = geteuid();
	setresuid (uid, uid, uid);
}

void ausgeschnitzel(void)
{
	system("/usr/bin/fortune"); /* Oh no! */
	exit(0);
}

int main(int argc, char *argv[])
{
	if (argc < 2)
		exit(1);

	uid_t uid = getuid();
	uid_t euid = geteuid();

	printf("Running as uid = %u, euid = %u\n", uid, euid);

	flugeldufel (argv[1]);

	return 0;
}

