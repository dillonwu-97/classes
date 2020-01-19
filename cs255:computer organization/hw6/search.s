*//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS
*// Dillon Wu
	xdef BinarySearch

BinarySearch:
******************************************************
* Write your recursive binary search assembler subroutine here
*
*    BinarySearch: input stack frame (see pdf for details)
*                    
******************************************************
* Java code for binary search algorithm 
*int binarySearch(int A[], int key, int low, int high) { 
*       if (high > low) 
*       { 
*            int mid = (high+low)/2; 
*            if (A[mid] == key) 
*               return mid;  
*            if (A[mid] > key) 
*               return binarySearch(A, key, low, mid); 
*            if (A[mid] < key) 
*                return binarySearch(A, key, mid+1, high); 
*        } else {
*            return -1; 
*        }
*} 
	
*Prelude / setup
	move.l	a6, -(a7)
	move.l	a7, a6
	suba.l	#4, a7		*this is saved for the local variable

*First if statement
	move.l	8(a6), D1	*D1 contains high
	move.l	12(a6), D2	*D2 contains low
	cmp.l	D2, D1		*D1 must be > D0, else if D1 <= D2, go to Negative zone
	ble	Negative
	
*Entering the if block since high was not negative
	add.l	D2, D1		*high + low
	divs	#2, D1		*(high + low)/2
	ext.l	D1		*store quotient in 32 bits
	move.l	D1, (A7)	*move mid as a local variable into the stack

*Now that (A7) contains mid, register D1 is freed up, and we can start going into each if statement
*This chunk handles if (A[mid] == key); 16(A6) contains key, (A7) contains mid, and 20(A6) contains start of the *array
	move.l	(A7), D0	*D0 contains mid in case we need to return it	
	muls	#4, D1		*D1 still contains mid; multiply by 4 to get index location
	movea.l	20(A6), A0	*Move address from A7 + 20 --> A0; don't want to change A7 so change A0 instead
	adda.l	D1, A0		*A0 now contains location of A[mid]
	move.l	(A0), D2	*D2 contains A[mid]
	move.l	16(A6), D1	*D1 is freed up; move the key value to D1
	cmp.l	D2, D1	
	bne	Lesser
	bra 	Finish		*If A[mid] = key, then D0 contains mid (the index) and we exit 	

*This chunk handles if A[mid] > key; if A[mid] > key, then key must be in the lower half
*D0 contains mid, D2 contains A[mid], D1 contains key; D2 is not needed so we can use it for something else
Lesser:
	cmp.l	D2, D1		*Compare A[mid] and key	
	bgt	Greater		*If A[mid] < key, then branch 

	move.l	#A, -(A7)       *Put address of the array on stack frame; don't need to change this
        move.l 	16(A6), -(A7)    *Put the key on the stack frame; don't need to change this   
	move.l 	#0, -(A7)       *Put the low value on the stack frame
        move.l 	D0, -(A7)    	*Put the new high value onto the stack frame

	bsr	BinarySearch

*Need to deallocate high, low, key, and A after returning from binarysearch

	
	bra	Finish
	

*This chunk handles if A[mid] < key; if A[mid] < key then key must be in the upper half
Greater:
	move.l	8(A6), D2	*D2 isn't used; move high to D2
	move.l	#A, -(A7)       *Put address of the array on stack frame; don't need to change this
        move.l 	16(A6), -(A7)    *Put the key on the stack frame; don't need to change this   
	add.l	#1, D0		*Subtract 1 from mid to get the new high
	move.l 	D0, -(A7)       *Put the low value on the stack frame
        move.l 	D2, -(A7)    	*Put the new high value onto the stack frame	
	
	bsr	BinarySearch

*Need to deallocate high, low, key, and A after returning from binarysearch

	
	bra 	Finish

*Entering else-block, i.e. if high < 0
Negative:
	move.l	#-1, D0		*return -1 if there is nothing to search for, D0 contains -1	

*The wrap up, i.e. popping out what's left in the stack, and going to the return address
Finish:
	move.l	A6, A7		*Deallocate by moving A6 address to A7
	move.l	(A7)+, A6	*Restore frame pointer
	rts

* *****************************************************************************
* If you need local variables, you can add variable definitions below this line
* *****************************************************************************


        end
