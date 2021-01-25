/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac Tally.java
	Execution: 		java Tally

	This counts the number of vowels in a String and returns it in an array.
*/

public class Tally {
	
	public static int[] tally (String s) {
		// a = 1, e = 2, i = 3, o = 4, u = 5
		// I need to make a different variable for each value; also there are five positions in the final array.
		int[] result = new int[5];
		int aCount = 0;
		int eCount = 0;
		int iCount = 0;
		int oCount = 0;
		int uCount = 0;
		// To account for both capital and lowercase
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == 'a' || s.charAt(i) == 'A') {
				aCount = aCount + 1;
			}
			if (s.charAt(i) == 'e' || s.charAt(i) == 'E') {
				eCount = eCount + 1;
			}
			if (s.charAt(i) == 'i' || s.charAt(i) == 'I') {
				iCount = iCount + 1;
			}
			if (s.charAt(i) == 'o' || s.charAt(i) == 'O') {
				oCount = oCount + 1;
			}
			if (s.charAt(i) == 'u' || s.charAt(i) == 'U') {
				uCount = uCount + 1;
			}
		}
		result[0] = aCount; //a
		result[1] = eCount; //e
		result[2] = iCount; //i
		result[3] = oCount; //o
		result[4] = uCount; //u
		return result;
	}

	// Transforms the array into something readable.
	public static String arrayToString (int[] x) {
		String result = "{";
		for (int i = 0; i < x.length - 1; i++) {
			result = result + x[i] + ", ";
		}
		if (x.length > 0) {
			result = result + x[x.length - 1];
		}
		result = result + "}";
		return result;
	}

	public static void main (String[] args) {
		
		System.out.println (arrayToString (tally ("HEY! Apples and bananas"))); // Mix of capital and lower;
		System.out.println (arrayToString (tally ("h aeiouuu wdddd"))); // all lower
		System.out.println (arrayToString (tally ("H AEIOU SDFSDFSDFSDFDS"))); // ALL CAPS
		System.out.println (arrayToString (tally ("12345eee"))); // When there are numbers
		System.out.println (arrayToString (tally (""))); // When there's nothing




	}
}