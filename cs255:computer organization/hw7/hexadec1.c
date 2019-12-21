/* -----------------------
   Hexadecimal numbers
   ----------------------- */

#include <stdio.h>

int main( int argc, char* argv[] )
{
   int a = 0x1A;         /* 1A Hexadecimal = 16 + 10 = 26 decimal */
   printf( "a =  %d\n", a  );

   a = 0x10A;            /* 101 Hexadecimal = 256 + 10 = 266 decimal */
   printf( "a =  %d\n", a  );
}