/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac CompLang.java
	Execution: 		java CompLang

	Converts a decimal number to binary 
*/


public class CompLang {
	

	public static void main (String[] args) {
		System.out.println(decimalToBinary(156));
		System.out.println(decimalToBinary(8));
		System.out.println(decimalToBinary(23));

	}

	// I have the right answer with thise code, but it's in reverse

	public static String decimalToBinary (int n) {
		String result = ""; 
		for (int i = n; i > 0; i = i/2) {
			if (i % 2 == 0) {
				result = result + 0;
			}  else {
				result = result + 1;
			}
		}
		result = reverse(result);
		return result;
	}

	// This is to reverse the digits

	public static String reverse (String words) {
		String newString = "";
		for (int i = words.length() - 1; i >= 0; i --) {
			newString = newString + words.charAt(i); 
		}
		return newString;
	}


}