/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac AverageLength.java
	Execution: 		java AverageLength

	Shows the average length of the strings in the array.
*/

public class AverageLength{
	
	public static void main (String[] args) {
		
		// Declaration of all the arrays and strings in the array.
		String[] x1 = new String[2];
		x1[0] = "Hello";
		x1[1] = "Q";
		
		String[] x2 = new String[0];

		String[] x3 = new String[3];
		x3[0] = "Hello";
		x3[1] = "Goodbye";
		x3[2] = "America";

		String[] x4 = {"Hello", "Goodbye"};

		//Testing the method
		
		System.out.println(avgLength(x1));
		System.out.println(avgLength(x2));
		System.out.println(avgLength(x3));
		System.out.println(avgLength(x4));


	}

	public static double avgLength (String[] x) {
		double result = 0; // This is the temporary result.
		double finalResult = 0; // This is the final result that sums the temporary results.
		// Without this part, the method returns NaN when the array is empty.
		if (x.length == 0) {
			return result;
		}
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[i].length(); j++) {
				result = result + 1;
			}
			finalResult = finalResult + result; // stores the result 
			result = 0; // resets the result
		}
		finalResult = finalResult / x.length;
		return finalResult;
	}

}