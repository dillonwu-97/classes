/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Digits.java
	Execution: 		java Digits

	Counts the number of times a certain digit repeats itself. 
*/

public class Digits {

	// These are all the test cases in the hw pdf.

	public static void main (String[] args) {

		System.out.println(digitCount(456234, 8));	// 0
		System.out.println(digitCount(456234, 5));	// 1
		System.out.println(digitCount(456234, 4));	// 2
		System.out.println(digitCount(-456234, 4)); // 2
		System.out.println(digitCount(0, 0));		// 1

	}

	// I convert the digits into a string and then count the characters in the string.

	public static int digitCount (int number, int digit) {
		String strNum = "" + number;
		String strDig = "" + digit;
		int result = 0;
		for (int i = 0; i < strNum.length(); i++) {
			if (("" + strNum.charAt(i)).equals(strDig)) {
				result = result + 1;
			}
		}
		return result;
	}
}