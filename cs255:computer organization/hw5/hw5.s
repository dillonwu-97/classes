* THIS  CODE  WAS MY OWN WORK , IT WAS  WRITTEN  WITHOUT  CONSULTING  ANY SOURCES  OUTSIDE  OF  THOSE  APPROVED  * BY THE  INSTRUCTOR
* Dillon Wu
* ********************************************************************
* Do not touch the following 2 xdef lists:
* ********************************************************************
        xdef Start, Stop, End
        xdef A, B, len_a, len_b, max, min, sum, common

* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
* You can add more xdef here to export labels to emacsim
* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*      Put your assembler program here - between the start and stop label
*      DO NOT define any variables here - see the variable section below
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

*Java code to find max:
*A[0] = max
*for (int i = 0; i < A.length; i++) {
*	if (A[i] > max) {
*		max = A[i]
*	}
*}	
Start:

	movea.l	#A, A0		
	move.l	(A0), D0	* move A[0] --> max
	move.l	#0, D2		* keeps track of i
	move.l	len_a, D3	* tells me the length of the array

maxLoop:
	cmp	D3, D2
	bge	arrayB
	
	move.l	(A0), D1	* keeps track of current
	cmp	D1, D0		* If D0 > D1, do nothing
	bgt	next
	move.l	D1, D0		* Else swap
	

next:
	add.l	#4, A0		* Next array position
	add.l	#1, D2		* Iterate 
	bra	maxLoop

*Now do the same thing for array B
*This is for B:

arrayB:	
	
	movea.l	#B, A0		
	move.l	#0, D2		* keeps track of i
	move.l	len_b, D3	* keeps track of size of array

maxLoopB:
	cmp	D3, D2
	bge	end
	
	move.l	(A0), D1	* keeps track of current
	cmp	D1, D0		* If D0 > D1, do nothing
	bgt	nextB
	move.l	D1, D0		* Else swap
	

nextB:
	add.l	#4, A0		* Next array position
	add.l	#1, D2		* Iterate 
	bra	maxLoopB

end: 
	move.l	D0, max

*Minloop
*Java code to find min:
*A[0] = min
*for (int i = 0; i < A.length; i++) {
*	if (A[i] < min) {
*		min = A[i]
*	}
*}	

	movea.l	#A, A0
	move.l	(A0), D0	*the current min
	move.l	#0, D2		*keeps track of i
	move.l	len_a, D3	*keeps track of the array size

minLoop:
	cmp	D3, D2
	bge	minArrayB	

	move.l	(A0), D1	*keeps track of current
	cmp	D1, D0
	blt	nextMin		*if the min is less than current, do nothing
	
	move.l	D1, D0		*else replace D0 with D1

nextMin:
	add.l	#1, D2		*iterate
	add.l	#4, A0		*next in the array
	bra	minLoop

*Going to array B
minArrayB:	
	movea.l	#B, A0		
	move.l	#0, D2		*D0 stills stores the min value from a
	movel	len_b, D3

minLoopB: 
	cmp	D3, D2
	bge	exitMin

	move.l	(A0), D1	* keeps trak of current
	cmp	D1, D0
	blt	nextMinB
	
	move.l	D1, D0

nextMinB:
	add.l	#1, D2
	add.l	#4, A0
	bra minLoopB

exitMin:
	move.l	D0, min

*Comparing common elements
*Java code:
*for (int i = 0; i < a.length; i++) {
*	for (int j = 0; j < b.length; j++) {
*		if (A[i] == B[j]) {
*			result++
*		}
*	}
*}

	movea.l	#A, A0		*starting out with the location of [A-4]
	add.l	#-4, A0
	move.l	#-1, D0		*starting out at i = -1
	move.l	len_a, D2	*D2 stores the length of A, i.e. when the loop stops
	move.l	len_b, D3	*D3 stores the length of B
	move.l	#0, D4		*stores the common

Loop1: 
	add.l	#1, D0		*add 1 to i
	add.l	#4, A0		*add 4 to the location A0; 	
	move.l	#0, D1		*starting out at j = 0
	movea.l	#B, A1		*starting out with the location of [B]
	cmp	D2, D0 		*if i == len_a, break
	bge 	break

Loop2:	cmp	D3, D1		*if j == len_b, go to loop1
	bge	Loop1
	move.l	(A0), D5
	move.l	(A1), D6
	cmp	D6, D5		*if A[i] != A[j], don't do anything and just iterate
	bne	iterate
	add.l	#1, D4		*else add 1 to the common

iterate:
	add.l	#4, A1		*move to B[j+1]
	add.l	#1, D1		*move to j+1
	bra	Loop2

break:
	move.l	D4, common



*for (int i = 0; i < a.length; i++) {
*	sum = sum + A[i];
*}
*for (int i = 0; i < b.length; i++) {
*	sum = sum + B[i];
*}
*return sum;

	move.l	len_a, D0	*stores length of a in d1
	move.l	#A, A0		*stores A location in A0
	move.l	#0, D1		*tracks i
	move.l	#0, D2		*tracks sum

sumStart: 
	cmp	D0, D1		*if i >= a length, break out of loop
	bge	sumB		* and go to the B array
	add.l	(A0), D2
	add.l	#4, A0		*iterate to the next element in the array, and go from i --> i+1
	add.l	#1, D1
	bra 	sumStart

sumB:
	move.l	#B, A0		*add everything from the B array to the sum in A; follows the same model as in 
	move.l	len_b, D0	* the coding for the sum of array A
	move.l	#0, D1

sumBStart:
	cmp	D0, D1
	bge	sumEnd
	add.l	(A0), D2
	add.l	#4, A0
	add.l	#1, D1
	bra sumBStart

sumEnd:
	move.l	D2, sum





















* ********************************************************************
* Do not touch the stop label - you need it to stop the program
*********************************************************************
Stop:   nop



* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*    Variable Section -   Put your variables here IF you need more
*
*    DO NOT define A, B, len_a, len_b, max, min, sum and common !!!
*    They are already defined below
*
*    You can add more variables below this line if you need them
* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++





* ********************************************************************
* Adjust the arrays (and lengths) below to test different arrays
* ********************************************************************
A:      dc.l  4,5,22,2,1
B:      dc.l  1,2,9,17,11,16,10
len_a:  dc.l  5
len_b:  dc.l  7

* ********************************************************************
* Do not touch anything below this line !!!
* ********************************************************************
max:    ds.l  1
min:    ds.l  1
sum:    ds.l  1
common: ds.l  1

End:    nop
        end
