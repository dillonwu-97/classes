/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac Reverse.java
	Execution: 		java Reverse

	This reverses the order of integers in a given array.
*/

public class Reverse {
	
	public static int[] reverseCopy (int[] x) {
		int[] result = new int[x.length]; // Making a new array; same length as original
		int last = x.length - 1;
		for (int i = 0; i < x.length; i++) {
			result[i] = x[last]; // Basically, just putting the last value of the original into the first position of the new
			last--; // We started from the top, now we going to the bottom.
		}
		return result;
	}

	// This makes the array readable by converting it into a String.
	public static String arrayToString (int[] x) {
		String result = "{";
		for (int i = 0; i < x.length - 1; i ++) {
			result = result + x[i] + ", ";
		}
		if (x.length > 0) {
			result = result + x[x.length - 1];
		}
		result = result + "}";
		return result;
	}
  	
  	public static void main (String [] args) {

  		// Declaring the arrays
  		int[] x1 = new int[3];
  		x1[0] = 1;
  		x1[1] = 2;
  		x1[2] = 3;

  		int[] x2 = new int[4];
		x2[0] = 4;
		x2[1] = 1;
		x2[2] = 19;
		x2[3] = 6;

		int[] x3 = new int[3];
		x3[0] = 3;
		x3[1] = 0;
		x3[2] = -1;

		int[] x4 = new int[0];

		int[] x5 = {1,1,1};

		// The test cases
  		System.out.println (arrayToString (reverseCopy (x1))); 
  		System.out.println (arrayToString (reverseCopy (x2)));
  		System.out.println (arrayToString (reverseCopy (x3))); // Test with negatives
  		System.out.println (arrayToString (reverseCopy (x4))); // Test when array is empty
  		System.out.println (arrayToString (reverseCopy (x5))); // Test when there is nothing to reverse

  	}
}