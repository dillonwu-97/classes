/* THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING CODE
WRITTEN BY OTHER STUDENTS.
Dillon Wu */

#include <stdlib.h>
#include <stdio.h>

typedef struct _seg {
	int bits[256];
	struct _seg *next;
} seg;

typedef struct {
	seg *segpt;
	int intnum;
	int bitnum;
} coordinate; 

#define BITSPERSEG  (8*256*sizeof(int)) //8192

//Global head variable
seg *head;
seg *pt;

// Calculates the pointer to the segment containing bit for j

seg* whichseg (int j) {
	// if it's the first pointer, return 1, if second, return 2 etc.
	// at 8191, still on first seg, if 8192, on the second seg
	// global head pointer?
	// index from bit 0
	int index = (j-1) / (BITSPERSEG);
	seg *temp = head;
	//printf("Index from whichseg %d\n", index);
	for (int i = 0; i < index; i++) {
		//printf("Pointer for whichseg: %p\n", head);
		//printf("i from whichseg: %d\n", i);
		temp = temp->next;
		//printf("Pointer for whichseg: %p\n", temp);
	}
	return temp;

}

//power function: x^y
int power (int x, int y) {
	int result = 1;
	for (int i = 0; i < y; i++) {
		result = x * result;
	}
	return result;
}

//calculates the index of array containing bit for j
int whichint (int j ) {
	//printf("%s\n", "Meow");
	int i = ((j-1)%8192) / 32;
	//printf("%d\n", i);
	return i;
}

//calculates the 0-31 bit position for j
int whichbit (int j) {
	//printf("%s\n", "Hi");
	int i = (j-1)%32 ; //so #1 maps to index 0 
	//printf("%d\n",i);
	return i;
}

//returns the coordinate using which functions
coordinate getcoord (int j) {
	coordinate c;
	//printf("%s\n", "Hello");
	c.segpt = whichseg(j);
	c.intnum = whichint(j);
	c.bitnum = whichbit(j);
	//printf("%d\n", coord->intnum);
	//printf("getcoord intnums %d\n",c.intnum);
	return c;
}

//sets bits to 1 at the coordinate
//probs need to use or here
void markcoord (coordinate c) {
	//if it is the n'th bit, conduct or operation on x and 2^(31-n) where x is the number of
	//the respective coordinate 
	int n = 31 - c.bitnum;
	seg *temp = c.segpt;
	//printf("The index being flipped is %d\n", n);
	//printf("The old value at the index is %d\n", (*temp).bits[c.intnum]);
	(*temp).bits[c.intnum] = ((*temp).bits[c.intnum]) | (power(2, n));
	//printf("The new value at the index is %d\n", (*temp).bits[c.intnum]);


}

//returns either 0 or 1 at coordinate
int testcoord (coordinate c) {
	// for some reason, all of the bits are initialized as -1 instead of 0?? there is not enough
	// space being allocated for all of the bits...
	//printf("Pointer: %p\n", pt);
	seg* temp = c.segpt;
	int x = (*temp).bits[c.intnum];
	//printf("intnum%d\n", c.intnum);
	//printf("real btnum: %d\n", c.bitnum);
	//printf("x: %d\n", x);
	x = x >> (31 - c.bitnum);
	//printf("The x value is: %d\n", x);
	if (x %2 == 0) {
		return 0;
	} else {
		return 1;
	}
}

//this uses getcoord and markcoord to mark all of the nonprimes
void marknonprime (int j) {
	coordinate c;
	for (int i = 2; i*i <= j; i++) {
		int flag = 1; // ignore the first number 
		//printf("i: %d\n", i);
		for (int k = i; k <= j; k = k+i) {
			pt = head;
			if (flag == 1) {
				//printf("flag: %d\n", k);
				flag = 0;	
			} else {
				//printf("k: %d\n", k);
				c = getcoord(k);
				//printf("Pointer: %p\n", c.segpt);
				markcoord(c);
				//printf("intnum: %d\n", c.intnum);
				//printf("bitnum: %d\n", c.bitnum);
			}	
		}
	}
}

// this uses getcoord and testcoord to check if a number is prime?
int testprime (int j) {
	coordinate c;
	c = getcoord(j);
	return testcoord(c);
}

//
int whichnum (coordinate c) {
	int result = c.intnum * 32 + c.bitnum + 1;
	int counter = 0;
	seg* tmp = head;
	while (c.segpt!= tmp) {
		//printf("pointer: %p\n", tmp);
		tmp = tmp->next;
		counter++;
	}
	result = counter * 8192 + result;
	return result;
}

// this increments a coordinate by incr
coordinate incrcoord (coordinate c, int inc) {
	int temp = whichnum (c);
	temp = temp + inc;
	//printf("temp: %d\n", temp);
	return getcoord(temp);
}



int main (int argc, char *argv[]) {

	//Testing the Main Function
	/*
	int *z;
	int w = 3;
	z = &w;
	w = *z;
	printf("%d\n", w);
	*/
	//printf("%s\n", "hello");
	//printf("%p\n", head);
	//printf("%p\n", head->bits);

	// Asking for inputs from terminal
	// This was taken from sample.c
	// Node allocation from input

	//printf("%lu\n", BITSPERSEG);

	int howmany;
	int segSize;
	int i;

	if (argc == 2) {
		sscanf (argv[1], "%d", &howmany);
	} else {
		scanf ("%d", &howmany);
	} 
	//printf("howmany: %d\n", howmany);
	segSize = (howmany)/BITSPERSEG + 1;
	//printf("segSize: %d\n", segSize);

	head= (  seg * ) calloc(1, sizeof(seg));
	pt=head;
	for (i=1; i<segSize; i++) { //Just Forward Links for Now
		pt->next = (  seg *) calloc(1, sizeof (seg)); 
		pt=pt->next;
		//printf("Hi: %d\n", i);
	}

	//printf("Done allocating %d nodes\n",i);
	int temp;

	//Testing whichseg
	//seg* temp = whichseg(howmany);
	//printf("whichseg returns: %p\n", temp);

	//Testing whichint
	//int test = whichint(howmany);
	//printf("whichint returns: %i\n", test);

	//Testing whichbit
	//test = whichbit(howmany);
	//printf("whichbit returns: %i\n", test);

	//Testing getcoord


	//Testing markcoord 
	/*
	coordinate testc;
	testc.segpt = head;
	testc.intnum = 0;
	testc.bitnum = 3;
	markcoord(testc);
	*/

	//Testing testcoord
	//test = testcoord(testc);
	//printf("testcoord returns: %d\n", test);

	//looking at the bitmap
	// Why is this returning different things even though the program isn't being changed?
	/*
	pt = head;
	int counter = 0;
	while (pt != NULL) {
		counter++;
		printf("Counter: %d\n", counter);
		printf("%d\n", pt->bits[0]);
		pt = pt->next;
	}
	*/
	
	//Testing marknonprime
	//1 means not prime, 0 means prime

	/*
	printf("%s\n", "Testing");
	marknonprime(howmany);
	printf("%s\n", "Bookmark");
	temp = testprime(howmany);
	printf("Testing prime: %d\n", temp);
	printf("%s\n", "Finish Testing");
	*/


	// Printing out the number of primes less than or equal to N
	/*
	pt = head;
	printf("%s\n", "Testing");
	while (pt!=NULL) {
		for (int i = 0; i < 256; i++) {
			printf("%d\n", pt->bits[i]);
		}
		pt = pt->next;
	}
	printf("%s\n", "Finish Testing");
	*/

	//testing whichnum
	//int hello = whichnum(getcoord(howmany));
	//printf("testing whichnum: %d\n ", hello);

	//this is the Sieve portion
	pt = head;
	marknonprime(howmany);
	int result = 0;
	for (int i = 1; i <= howmany; i++) {
		//printf("Counter: %d\n", i);
		if (testprime(i) == 0) {
			//printf("prime: %d\n", i);
			result++;
			//printf("current result: %d\n", result);
		} else {
			//printf("Not prime: %d\n", i);
		}
	}
	//offset by 1 because 1 is not prime, and 2 is not odd
	//printf("The number of odd primes less than or equal to %d is: %d\n", howmany, result-1);
	printf("Calculating odd primes up to %d...\n", howmany);
	printf("Found %d odd primes\n", result - 2);
	printf("Enter Two Even Numbers for Triple Prime Differential\n");
	int x[2];

	// triple prime function
	pt = head;
	coordinate d = getcoord(3);
	int solutions = 0;
	int track = 3;
	int largest = 0;

	while (scanf("%d %d", &x[0], &x[1]) != EOF) {
		//printf("%d\n", x[1]);
		//printf("%d\n", track);
		//printf("%d\n", howmany);
		while (track + x[1] <= howmany) {
			if (x[0] == 1) {
				d = getcoord(2);
				if (testcoord(d)==0 && testcoord(incrcoord(d,x[0]))==0 && testcoord(incrcoord(d,x[1]))==0) {
					largest = 2;
					solutions = 1;
					break;
				} else {
					break;
				}
			} else {
				//printf("track is %d\n", track);		
				if (testcoord(d)==0 && testcoord(incrcoord(d,x[0]))==0 && testcoord(incrcoord(d,x[1]))==0) {
					//printf("testcoord: %d\n", testcoord(incrcoord(d,12)));
					//printf("d is %d\n", whichnum(d));		
					largest = whichnum(d);
					solutions++;
				}
				d = incrcoord(d,2);
				track+=2;
			}
		}
		if (x[0] > x[1]) {
			printf("%s\n", "No solutions");
		} else if (largest!=0) {
			printf("%d solutions, largest (%d,%d,%d)\n", solutions, largest, largest+x[0], largest+x[1]);	
		} else {
			printf("%s\n", "No solutions");
		}
		solutions = 0;
		track = 3;
		largest = 0;
		d = getcoord(3);
	}



}