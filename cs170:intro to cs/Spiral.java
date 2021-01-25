/*
	Author: 		Dillon Wu
	Written: 		September 6, 2017

	Compilation: 	javac Spiral.java
	Execution: 		java Spiral

	Makes the turtle draw a spiral of a particular polygon.
*/

public class Spiral {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(20);
		polyspiral(minion, 3, 20, 5);
		//polyspiral (minion, 5, 10, 4);
		//polyspiral(minion, 8, 5, 3);
	}

/*	
	Parameter n specifies the number of sides of the polygon.
	Parameter base specifies the length of the smallest side.
	Parameter round specifies the number of revolutions.
	
	The turtle retraces its steps in the second for-loop.
*/

	public static void polyspiral (Turtle t, int n, double base, int rounds) {
		for (int i = 0; i < rounds * n; i++) {
			t.forward(i * base);
			t.right (360.0 / n); 
		}

		for (int i = rounds * n-1; i >= 0; i--) {
			t.left(360.0 / n);
			t.backward (i * base);
		}
	}

}
