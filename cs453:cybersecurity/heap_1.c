// gcc heap-arbitrary-write.c -o heap-arbitrary-write -m32 -fno-pic -g

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct person {
	char *name;
};

int secret = 0;

int main(int argc, char *argv[]) {
	struct person *a = malloc(sizeof(struct person));
	a->name = malloc(32);
	struct person *b = malloc(sizeof(struct person));
	b->name = malloc(32);

	strcpy(a->name, argv[1]);
	strcpy(b->name, argv[2]);

	if (secret == 0x11223344) {
		printf("You found the secret!\n");
		gid_t gid = getegid();
		setresgid(gid, gid, gid);
		system("/bin/sh -i");
	} else {
		printf("Secret is: 0x%x\n", secret);
	}
}
