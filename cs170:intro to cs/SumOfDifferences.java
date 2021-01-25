/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac SumOfDifferences.java
	Execution: 		java SumOfDifferences

	Adds up all the differences between a number and the number after it
*/

public class SumOfDifferences {

	public static int sumOfDiffs (int[] x) {
		int result = 0;
		int finalResult = 0;
		// You stop at the second to last value; nothing to subtract after the last value
		for (int i = 0; i < x.length - 1; i++) {
			result = x[i] - x[i+1];
			finalResult = result + finalResult;
		}
		return finalResult;
	}

	public static void main (String[] args) {
		
		// Declaring the arrays.
		int[] x1 = new int[3];
		x1[0] = 3;
		x1[1] = 4;
		x1[2] = 5;

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

		int[] x5 = {-1, -2, -3}; // 2

		//Testing the Method
		System.out.println(sumOfDiffs(x1));
		System.out.println(sumOfDiffs(x2));
		System.out.println(sumOfDiffs(x3)); // This tests a case with negative numbers.
		System.out.println(sumOfDiffs(x4)); // This tests a case with no values in the array.
		System.out.println(sumOfDiffs(x5)); // This tests a case with just negative numbers.
	}
}