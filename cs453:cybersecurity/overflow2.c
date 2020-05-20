#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>

void message(char *input) {
    char buf[256];
    strcpy(buf, input);

    printf("You said: %s\n", buf);
}

int main(int argc, char **argv) {
    setresgid (getegid(), getegid(), getegid());
    if (argc > 1){
        message(argv[1]);
    } else {
        printf("Usage: ./overflow <message>\n");
    }
    return 0;
}

