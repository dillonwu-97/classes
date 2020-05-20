// gcc heap-hijack-funcptr.c -o heap-hijack-funcptr -m32 -fno-pic -g

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct function {
  void (*f)();
};

void hello(void) { printf("Hello world!\n"); }

void shell() {
  gid_t gid = getegid();
  setresgid(gid, gid, gid);
  system("/bin/sh -i");
}

int main(int argc, char *argv[]) {
  char *cp = (char *)malloc(64);

  struct function *fp = (struct function *)malloc(sizeof(struct function));
  fp->f = hello;

  strcpy(cp, argv[1]);
  fp->f();
}
