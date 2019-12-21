/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Dillon Wu*/

#include <stdio.h>
#include <stdlib.h>
#include "header.h"

// power function
// x ^ y
int power (int x, int y) {
	int result = 1;
	for (int i = 0; i < y; i++) {
		result = x * result;
	}
	return result;
}

// make all elements in prime[] array equal to 1
void setAll () {
	for (int i = 0; i < MAX; i++) {
		prime[i] = 0;
		prime[i] = ~prime[i];
	}
}

//The clearBit(n) function will clear (only) the bit in the prime[] array that corresponds to the
//number n
void clearBit (int n) {
	// first, find the array index 
	int index = n / 32;
	// next, find the bit index
	int remain = (n % 32);
	// to find the bit sequence
	int flip = power(2,remain);
	// finally, flip that bit to 0
	prime[index] = prime[index] ^ flip;
}

//The testBitIs1(n) function will return 1 if the bit in the prime[] array that corresponds to the
//number n is equal to 1. Otherwise, it will return 0
int testBitIs1(int n) {
	// array index
	int index = n/32;
	// bit index
	int remain = (n % 32);
	// bit sequence tester
	int flip = power(2, remain);
	// if prime[index]'s remain bit is = to 1, return 1; else return 0
	// using & - only 1 AND 1 --> 1, else --> 0
	if ((prime[index] & flip) == 0) {
		return 0;
	} else {
		return 1;
	}
}

// finds all prime numbers that are less than or equal to N using
// the Sieve of Eratosthenes algorithm. 
void sieveOfE (int n) {
	// if n is 1 or 0, flip since 1 and 0 are not primes
	setAll();
	clearBit(0);
	clearBit(1);
	// if n is greater than 2, continue flipping 
	// start at 2 instead of 1 because if n is a prime, it stays as 1; if it's not a prime, it would have already
	// been flipped; also, do < n for the same reason
	int counter = 2;
	while (counter < n) {
		if (testBitIs1(counter) == 1) {
			// start at counter + 1 and check everything up to and including n
			for (int i = counter + 1; i <= n; i++) {
				if ( i % counter == 0 ) {
					// only flip the bit if it is 1, if it is 0, it is already flipped
					if (testBitIs1(i) == 1) {
						//printf("%s", "counter is: ");
						//printf("%d\n", counter); // debugging
						//printf("%s", "i is: ");
						//printf("%d\n", i); // debugging
						clearBit(i);
					}
				
				}
			}
		}
		counter++;
		//printf("%d\n", n);
		printf("%d\n", counter);
	}
}

// finds the number of primes that are less than or equal to N.
int countPrimes (int n) {
	int count = 0;
	for (int i = 1; i <= n; i++) {
		if ( testBitIs1(i) == 1 ) {
			count++;
			//printf("%d\n", i);
		}
	}
	return count;
}

// prints the primes that are less than or equal to N.

void printPrimes (int n) {
	for (int i = 2; i <= n; i++) {
		if (testBitIs1(i) == 1) {
			printf("%d\n", i);
		}
	}
}
