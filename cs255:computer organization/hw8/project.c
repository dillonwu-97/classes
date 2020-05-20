/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Dillon Wu*/
#include <stdio.h>
#include <stdlib.h>
#include "header.h"
#include "math.h"
EXTERN int NSegs;
EXTERN seg * head;
seg * trace;

void clearAll () {
	seg * track = head;
	int count = 0;
	while (track != NULL){
		count++;
			for (int i = 0; i < SIZE_OF_SEG; i++) {
			(*track).bits[i] = 0;	
			// why does this print out -1 instead of 1?
			(*track).bits[i] = ~(*track).bits[i];
			//printf("%d\n", track->bits[i]);
		}
		track = track->next;
	}
}

// power function
// x ^ y
int power (int x, int y) {
	int result = 1;
	for (int i = 0; i < y; i++) {
		result = x * result;
	}
	return result;
}

//The clearBit(n) function will clear (only) the bit in the array that corresponds to the
//number n
void clearBit (int n) {
	// first, find the array index 
	int index = n / 32;
	// next, find the bit index
	int remain = (n % 32);
	// to find the bit sequence
	int flip = power(2,remain);
	// finally, flip that bit to 0
	trace->bits[index] = trace->bits[index] ^ flip;
}



//The testBitIs1(n) function will return 1 if the bit in the prime[] array that corresponds to the
//number n is equal to 1. Otherwise, it will return 0
int testBitIs1(int n) {
	// array index
	int index = n/32;
	// bit index
	//printf("%s", "index: ");
	//printf("%d\n", index);
	int remain = (n % 32);
	// bit sequence tester
	//printf("%s", "remain: ");
	//printf("%d\n", remain);
	int flip = power(2, remain);
	//printf("%s", "flip: ");
	//printf("%d\n", flip);
	//printf("%s", "trace: ");
	//printf("%d\n", trace->bits[index]);
	// if prime[index]'s remain bit is = to 1, return 1; else return 0
	// using & - only 1 AND 1 --> 1, else --> 0
	if ((trace->bits[index] & flip) == 0) {
		return 0;
	} else {
		return 1;
	}
}

// Uses head to find the "bit array" (list)
// 8192 odd numbers in a segment
// 2n+1 is the formula: at bit 0, the number is 1. at bit 1, the number is 3
// at bit 8191, the number is 16383
// The goal is to flip all of the primes up t
// bit 0 at each subsequent array bit[i] location = 511 * i + 1/ general form is 511 * i + (2n + 1)
// goes up to 511 in the first bit[0].
void sieveOfE( int N ) {
	// I know that the number of times the loop below needs to run will be at most sqrt(N), so I find the sqrt
	// of N. I then convert it to an int and add 1 and then round up. I know there will be a rounding issue
	// but I resolve it by adding 1 so that there is an UPPER BOUND on the number of times the loop runs. 
	double loopTimes = sqrt(N); 
	int loopInt = (int) loopTimes;
	///loopInt++;// needed? most likely not
	//printf("%d\n", loopInt);

	// this segment traverses the linked list
	trace = head;

	// these two integers keep track of the number of segments I need to go through 
	int segOne = N / (2 * 8192); // the number of times I need to run 
	//printf("%d\n", segOne);


	// clears the first bit since 1 is not a prime
	clearBit(0);

	// start at bit 4, i.e. at the number 9
	// the number starts at 4, then we start at 12, then 24, then 40, then 60...
	// start gets incremented by 4 * x, where x starts at 2, e.g. the transition from 3 --> 5 is 
	// 4 + 4 * 2, then from 5 --> 7 is 12 + 4 * 3...
	int start = 4; // update 4 at the end of each number
	int reset = start; // keeps track of original start value
	int count = 2; // count gives me the constant I need to multiply start by
	int increment = 3; // this gets updated / incremented by 2 every time we start the process over.
	int incrementSet = increment; 
	
	// for int i = 9 up to sqrt n
	// this is the number of times the loop runs
	// after the first iteration, all multiples of 3 are cleared, then all of 5, then 7, up to sqrt(N)
	// reminder: check edge cases
	for (int j = 0; j < loopInt; j= j + 2) {
		// the loop to find all of the multiples of each odd number
		int k = start;
		clearBit(start);
		for (int i = 0; i <= segOne; i++) {
			//printf("%s", "i is: ");
			//printf("%d\n", i);
			if (k <= N/2) {

				while ((k < 8192 * (i + 1)) && k < N/2) {
					//printf("%d\n", k);
					//printf("%s\n", "Book");
					if (testBitIs1(k % 8192) == 1) {
						clearBit( k % 8192 );
						//printf("%d\n", k%8192);
					}
					k = k + increment;
					//printf("%d\n", k);
				}
				trace = trace->next;
				//printf("%d\n", trace->bits[0]);
			}


		}

		// reset the start value
		start = reset; 
		start = start + 4 * count;
		reset = start;
		increment = increment + 2;
		count++;
		trace = head;

		//printf("%s\n", "We made it?");
	}
	//printf("%d\n", trace->bits[251]); // -2138494954
	//printf("%d\n", trace->bits[252]); //5: 1744830560
	//printf("%d\n", trace->bits[253]); //6: 269258753
	//printf("%d\n", trace->bits[254]); //4: -2143289056 
	//printf("%d\n", trace->bits[255]); //7: 1093681728
	// total should be 23 
}      

int countPrimes (int n) {
	int count = 0;
	int track = -1; // keeps track of how many numbers of each node we have visited
	n = (n-1) /2;
		for (int i = 0; i <= n; i++) {
			track++;
			if (track > 8191) {
				track = 0;
				trace = trace->next;
			}
			if ( testBitIs1(track) == 1 ) {
				//printf("%d\n", i);
				count++;
				//printf("%d\n", i);
			}
		}
	return count +1; // to account for 2
}

// prints the primes that are less than or equal to N.
void printPrimes (int n) {

	printf("%d\n", 2);
	for (int i = 1; i < n/2; i++) {
		if (testBitIs1(i) == 1) {
			printf("%d\n", 2*i + 1);
		}
	}
}

// factorize
// Plan: reduce the number so that it is odd; call Sieve function and then print everything that is a 1
void factor(int inp) {
	while (inp % 2 == 0) {
		printf("%d\n", 2);
		inp = inp /2;
	}
	sieveOfE(inp); // all primes less than the input
	int flag = 0;
	while (flag == 0) {
		for (int i = 1; i < countPrimes(inp); i++) {
			if (testBitIs1(i) ==1) {
				if ( inp == (2*i + 1)) {
					printf("%d\n", inp);
					flag = 1;
					break;
				}
				if ( ( inp % (2*i+1) )== 0) {
					inp = inp / (2*i + 1);
					printf("%d\n", 2*i + 1);
				}
			}
	}
	}
}

