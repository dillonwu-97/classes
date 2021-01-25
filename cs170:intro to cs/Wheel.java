/*
	Author: 		Dillon Wu
	Written: 		September 6, 2017

	Compilation: 	javac Wheel.java
	Execution: 		java Wheel

	Makes the turtle draw a polygon with the same type of polygon
	on its adjacent sides. 
*/

public class Wheel {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		//  polywheel(minion, 3, 100);			//  Test case 1
		//	polywheel(minion, 4, 50);			//  Test case 2
		polywheel(minion, 6, 50);			//  Test case 3

	}


	//	Parameter numSides specifies the number of sides of the polygon.
	//	Parameter length specifies the length of each side. 

	public static void polywheel (Turtle t, int numSides, double length) {
		for (int i = 0; i < numSides; i++) {
			polygon(t, numSides, length);
			t.forward(length);
			t.right(360 / numSides);
		}

	}

	//  This creates the original polygon.

	public static void polygon (Turtle t, int numSides, double length) {
		for (int i = 0; i < numSides; i++) {
			t.forward(length);
			t.left(360 / numSides);
		}
	}

}