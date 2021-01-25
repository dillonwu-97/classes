/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac CreditFraud.java
	Execution: 		java CreditFraud

	This checks if the credit card number will pass as an actual number.
*/

public class CreditFraud {
	
	// This is the final function, composed of all the functions from below.
	public static boolean luhnCheckSum (int[] x) {
		if (x.length == 0) {
			System.out.println("Please enter a number.");
			return false;
		}
		// This is if I wanted to be realistic; credit cards have 16 digits.
		/* if (x.length != 16) {
			System.out.println("Please enter a number.");
			return false;
		} */
		if (checkSum(newDigits(x)) % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// This function returns the sum of the digits in the credit card.
	public static int checkSum (int[] x) {
		int sum = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] > 10) {
				sum = sum + sumStringDigits(x[i]);
			} else {
				sum = sum + x[i];
			}
		}
		return sum;
	}

	// This function sums the digits in an integer. 
	public static int sumStringDigits (int x) {
		String start = Integer.toString(x);
		//System.out.println("yes:" + start);
		int end = 0;
		for (int i = 0; i < start.length(); i++) {
			end = end + Integer.parseInt(start.substring(i, i+1));
			//System.out.println("no:" + end);

		}
		return end;
	}
	
	// This creates the new array that will be used for the functions above.
	// It takes the original credit card numbers and accomplishes the multiplication conversion.
	public static int[] newDigits (int[] x) {
		int[] modNum = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			if (i % 2 == 0) {
				modNum[i] = 2 * x[i];
			} else {
				modNum[i] = x[i];
			}
		}
		return modNum;
	}

	// This is to print the array as a string.
	public static String arrayToString(int[] x) {
    	String result = "{";
    	for (int i = 0; i < x.length - 1; i++) { // all the elements except the last one
     		result += x[i] + ", ";
    	}
    	if (x.length > 0) { // protection from index out of bound error
      		result += x[x.length - 1]; // last element
   		}
    	result += "}";
    	return result;
  	}

	public static void main(String[] args) {
		// This creates the tests
		int[] x1 = new int[6];
		x1[0] = 4;
		x1[1] = 5;
		x1[2] = 6;
		x1[3] = 3;
		x1[4] = 9;
		x1[5] = 2;

		int[] x2 = {4,9,9,1,6,5,7};
		int[] x3 = {1};
		int[] x4 = {};
		int[] x5 = {1,1,1,1,1,1,1,1};

		// This is to test the functions that I created
		// What each of them does is described above.
		
		//System.out.println(arrayToString(newDigits(x1))); 

		//System.out.println(sumStringDigits(12));

		//System.out.println(checkSum(newDigits(x1)));

		// These are the test cases
		System.out.println(luhnCheckSum(x1)); // test from the HW
		System.out.println(luhnCheckSum(x2)); // test from the HW
		System.out.println(luhnCheckSum(x3)); // one digit
		System.out.println(luhnCheckSum(x4)); // empty array
		System.out.println(luhnCheckSum(x5)); // definitely false


	}
}