/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac LoopyMcLoop.java
	Execution: 		java LoopyMcLoop

	This function creates a square loop in two different ways: using recursion, and using iteration
*/

public class LoopyMcLoop {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		// minionFred is used to test if the minion is traveling the correct distance.
		/*
		Turtle minionFred = new Turtle();  // This is to make sure the first turtleSpiral call moves 100 units
		minionFred.forward(100);
		minionFred.right(90);
		minionFred.forward(90);
		*/

		// Test Cases for Recursion

		//turtleSpiral(minion, 100, 90); // should produce 2 lines
		//turtleSpiral(minion, 100, 100); // should produce 1 line
		//turtleSpiral(minion, 100, 91); // should produce 1 line
		//turtleSpiral(minion, 10, 90); // should produce nothing
		//turtleSpiral(minion, 0, 0); // should produce nothing
		//turtleSpiral(minion, 100, 0); // should produce nothing
		//turtleSpiral(minion, 100, 10); 

		// Test Cases for Iteration

		//turtleSpiralIter(minion, 100, 90); // should produce 2 lines
		//turtleSpiralIter(minion, 10, 90); // should produce nothing
		//turtleSpiralIter(minion, 0, 0); // should produce nothing
		//turtleSpiral(minion, 100, 100); // should produce 1 line
		//turtleSpiral(minion, 100, 91); // should produce 1 line
		//turtleSpiralIter(minion, 100, 0); // should produce nothing
		//turtleSpiralIter(minion, 100, 10); 

	}

	public static void turtleSpiral(Turtle t, double size, double minSize) {
		if (minSize == 0 || size < minSize) {
			return; // if minSize is 0, then turtle moves forever
		} else if (size >= minSize && size*.9 < minSize) {  // in case size == minSize
			t.forward(size);
		} else {
			t.forward(size);
			t.right(90);
			turtleSpiral(t, size * .9, minSize); 	// to make it 10% shorter, multiply by .9
		}

	}

	public static void turtleSpiralIter (Turtle t, double size, double minSize) {
		if (minSize != 0) { // minSize can't be 0 or else it goes ad infinitum
			for (double i = size; i >= minSize ; i = i - i *.10 ) {
				t.forward(i); 
				t.right(90);
			}
		} else {
			return;
		}
	}
}