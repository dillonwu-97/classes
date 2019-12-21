* Collaboration Statement:
* 
* 
*
	xdef Start, Stop, End
	xdef Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10
	xdef A, B, C
	xdef i, j, k
	xdef head
	xdef ans_b, ans_w, ans_l

Start:
*******************************************************************
* Put your assembler instructions here
* Write the answer to each question after the corresponding label.
* DO NOT REMOVE ANY LABEL IN THIS ASSIGNMENT
* *** Failure to do so will result in point dedections !!! ***
*******************************************************************


*         ans_b = A[9];
Q1:	move.l	#A, A0
	move.b	9(A0), ans_b
	



*         ans_l = B[5];
*	Since a short has a size of 2 bytes, you have to multiply 5 by 
*	2 to move the proper distance away from the base
Q2:	movea.l	#B, A0
	movea.w	10(A0), A1
	move.l	A1, ans_l



*         ans_l = C[k];
Q3:	movea.l	#C, A0
	move.l	A0, D0
	move.l	k, D1
	muls	#4, D1
	add	D0, D1
	move.l	D1, A1
	move.l	(A1), ans_l
	
	





*         ans_w = A[k - 6];
Q4:	movea.l	#A, A0
	move.l	A0, D0
	move.l	k, D1
	sub.l	#6, D1
	add	D0, D1
	move.l	D1, A1
	move.b	(A1), D2
	move.w	D2, A2
	move.w	A2, ans_w





*         ans_w = C[i + j];
Q5:	movea.l	#C, A0
	move.l	A0, D1
	move.b	i, D3
	move.w	j, D4
	add	D3, D4
	muls	#4, D4 // multiply by 4 because C is storing integers
	add	D4, D1
	move.l	D1, A1
	move.l	(A1), D2
	move.w	D2, ans_w
	
	






*         ans_l = A[k] + B[i];
* The first block takes care of A[k]
* The second block takes care of B[i]
* The third block adds the two and stores it in ans_l
Q6:	movea.l	#A, A0 // A0 contains location
	move.l	A0, D0 // D0 contains location NOT VALUE
	move.l	k, D4  // D4 contains the value k
	add	D4, D0     // add D4 to location value D0 to get NEW LOCATION
	move.l	D0, A2
	move.b	(A2), D4 // return the value at A2 and store the VALUE AT D4
	ext.l	D4

	movea.l	#B, A1
	move.l	A1, D1	
	move.b	i, D3
	muls	#2, D3
	add	D3, D1
	move.l	D1, A3
	move.w	(A3), D3
	ext.l	D3

	add.b	D4, D3
	move.l	D3, A5
	move.l	A5, ans_l

	
	
	



		



*         ans_l = B[B[j - 1] + 448];
*		  x = B[j-1] + 448
*		  B [x]
Q7:	movea.l #B, A0
	move.l 	A0, D1
	
* moving i	
	move.w  j,	D2
	sub 	#1, D2 // gives j-1 in D2
	muls    #2, D2 // gives 2 bytes * [j-1], j-1 being the number of bytes you want to move
	add.w   D2, D1 // move j-1 from location B
	add.w   #448, D1 // gives me B[j-1] + 448

	muls	#2,	D1
	movea.l A0, D0
	add.w 	D1, D0
	move.w  D0, A2
	move.l  (A2), ans_l









*         ans_w = B[15]
Q8:	movea.l #B, A0
	move.l 	A0, D0
	move.l  #15, D1
	muls    #2, D1
	add     D1, D0 // gives B[15]
	move.l  D0, A1
	move.w (A1), ans_w




*	  ans_l = head.value2;
Q9:

		


*	  ans_w = head.next.next.value1;
Q10:





*************************************************
* Don't write any code below this line
*************************************************

Stop:	nop
	    nop

*************************************************
* Don't touch these variables
* You do NOT need to define more variables !!!
*************************************************

************************************************************************
* Note the use of the even directive to locate the variables ans_w and j
* at an EVEN address due to the variables ans_b and i being bytes
* Short and int variables MUST start on an even address (or you 
* will get an "odd address" error)
************************************************************************

ans_b: ds.b 1
	   even
ans_w: ds.w 1
ans_l: ds.l 1

i:     dc.b  5
	   even
j:     dc.w  4
k:     dc.l  7

A:     dc.b   -11, 22, -33, 44, -55, 66, -77, 88, -99, 109

B:     dc.w   111, -222, 333, -444, 555, -666, 777, -888, 999, -5189

C:     dc.l   1111, -2222, 3333, -4444, 5555, -6666, 7777, -8888, 9999, -9983


head:   dc.l  list1

list3:  dc.l 2468
        dc.w 88
	dc.l list4
list2:  dc.l 1470
        dc.w 78
	dc.l list3
list4:  dc.l 4567
        dc.w 65
	dc.l list5
list1:  dc.l 1357
        dc.w 98
	dc.l list2
list5:  dc.l 9876
        dc.w 54
	dc.l 0


End:
	end
