/*
	Author: 		Dillon Wu
	Written: 		September 6, 2017

	Compilation: 	javac Star.java
	Execution: 		java Star

	Makes the turtle draw a star with a star on the end of each arm. 
*/


public class Star {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle ();
		minion.delay(0);
		//multistar(minion, 7, 100);			//  Test case 1
		//multistar (minion, 10, 70);			//  Test case 2
		multistar(minion, 25, 120);			//  Test case 3
	}

	//  Multistar creates a star with stars on the end of its arms.
	//  Parameter n specifies the number of rays.
	//  Parameter length specifies the length of the rays. 

	public static void multistar (Turtle t, int n, double integer) {
		for (int i = 0; i < n; i++) {
			t.forward(integer); 
			ministar(t, n, integer);
			t.backward(integer);
			t.left(360.0 / n);
		}
	}

	//  Ministar creates the base star.
	//  The base star has no stars on the end of its arms.
	//  The parameter definitions are the same as multistar. 

	public static void ministar( Turtle t, int n, double integer) {
		for (int i = 0; i < n; i ++) {
			t.forward(integer / 4);
			t.backward(integer / 4);
			t.left(360.0 / n);
		}
	}
}