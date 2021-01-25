/*
	Author: 		Dillon Wu
	Written: 		September 6, 2017

	Compilation: 	javac BlockPyramid.java
	Execution: 		java BlockPyramid

	Makes the turtle create a pyramid with blocks. 
*/

public class BlockPyramid {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		//minion.delay(200);
		minion.delay(0);
		pyramid(minion, 200, 10);			//	Test case 1
		// pyramid (minion, 200, 5);			//  Test case 2
		// pyramid (minion, 300, 10);			//	Test case 3

	}

/* 
	Parameter base specifies the total length of the base of the pyramid.
	Parameter levels indicates the layers of blocks.

	I couldn't use modularized loops in this situation because I needed 
	the variable "i" for the subsequent for-loops. 
*/

	public static void pyramid (Turtle t, double base, int levels) {
		
		for (int i = 1; i <= levels; i ++) {

			for (int j = 0; j < 2*i - 1; j++) {			//  Moves turtle forward and makes a block
				block(t, (base/levels));
				t.forward(base/levels);
			}
		 	for (int k = 2*i - 2; k >= 0; k--) {		//  Moves turtle backward
		 		t.backward(base/levels);
			 }
			t.penup();
			t.backward(base / levels);
			t.right(90);
			t.forward(base / levels);
			t.left(90);
			t.pendown();
		}

		reverse(t, base, levels);
	}

	//	Sends the turtle back to starting position.

	public static void reverse (Turtle t, double base, int levels) {
		for (int i = 0; i < levels; i++) {
			t.penup();
			t.left(90);
			t.forward(base / levels);
			t.right(90);
			t.forward(base / levels);
		}
		t.pendown();
	}

	//	Creates the basic block used to build the pyramid.

	public static void block (Turtle t, double size) {
		for (int i = 0; i < 4; i++) {
			t.forward(size);
			t.left(90);
		}
	}
}