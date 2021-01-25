/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Palindrome.java
	Execution: 		java Palindrome

	Determines if the word is a palindrome 
*/

public class Palindrome {
	

	public static void main (String [] args) {
		
		// This is the testing for the helper methods.
		System.out.println(frontHalf("kayak"));
		System.out.println(backHalf("kayak"));
		System.out.println(reverse("hello"));
		System.out.println(reverse(backHalf("kayak")));

		// This is the testing for the assignment.
		System.out.println(isPalindrome(""));
		System.out.println(isPalindrome("kayak"));
		System.out.println(isPalindrome("racecar"));
		System.out.println(isPalindrome("hello"));

	}

	public static boolean isPalindrome (String word) {
		if (frontHalf(word).equals(reverse(backHalf(word)))) {
			return true;
		} else {
			return false;
		}

	}

	// I divide the word into two parts, and then try to match them up

	public static String frontHalf (String word) {
		return word.substring(0, word.length()/2 + 1);
	}

	public static String backHalf (String word) {
		return word.substring(word.length()/2, word.length());
	}

	public static String reverse (String word) {
		String newString = "";
		for (int i = word.length()-1; i >= 0; i--) {
			newString = newString + word.charAt(i);
		}
		return newString;
	}
}