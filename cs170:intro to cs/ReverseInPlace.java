/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac ReverseInPlace.java
	Execution: 		java ReverseInPlace

	This reverses the order of integers within the original array.
*/

public class ReverseInPlace {
	
	// Nothing is returned so void is used.
	public static void reverse (int[] x) {
		int[] temp = new int[x.length]; // Creating a temporary array
		for (int i = 0; i < x.length; i++) {
			temp[i] = x[i];
		} // Giving the temp all the values of the original
		for (int i = 0; i < temp.length; i++) {
			x[i] = temp[temp.length - i - 1]; // replacing the original with its values except in reverse order
		}
	}

	// Turning the array into something readable.
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

	public static void main (String[] args) {
		// Creating the arrays.
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

		// Showing before the modification
		System.out.println(arrayToString(x1));
		System.out.println(arrayToString(x2));
		System.out.println(arrayToString(x3));
		System.out.println(arrayToString(x4));
		System.out.println(arrayToString(x5));

		// Applying the modification
		reverse(x1);
		reverse(x2);
		reverse(x3);
		reverse(x4);
		reverse(x5);

		// Printing the changed arrays.
		System.out.println(arrayToString(x1));
		System.out.println(arrayToString(x2));
		System.out.println(arrayToString(x3));
		System.out.println(arrayToString(x4));
		System.out.println(arrayToString(x5));
	}

}