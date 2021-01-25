/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac ReverseTest.java
	Execution: 		java ReverseTest

	This function tests to see if two strings are the reverse of one another.
*/

public class ReverseTest {

	public static void main (String[] args) {

		System.out.println(isReverse("happy", "yppah")); // true; traced this one
		System.out.println(isReverse("cool", "coal")); // false; traced this one
		System.out.println(isReverse("","")); // true; traced this one
		System.out.println(isReverse("hella","olleh")); // false; edge case
		System.out.println(isReverse("yello","olleh")); // false; edge case
		System.out.println(isReverse("hell", "hello")); // different lengths

	}	

// Note: I tried creating String variables so that I didn't have to use substring in the ifelse 
// statement, but that didn't work. 	
	public static boolean isReverse(String s1, String s2) {  	
		if (s1.length() != s2.length()) {	// this is in case the lengths of the strings are different.
			return false;
		} else if (s1.equals("")) {			// base case
			return true;							
		} else if(s1.charAt(0) == s2.charAt(s2.length()-1)) {	// if the first and last characters are the same
			return isReverse(s1.substring(1, s1.length()), s2.substring(0, s2.length()-1)) && true; 
		} else {
			return isReverse(s1.substring(1, s1.length()), s2.substring(0, s2.length()-1)) && false;
		}
	}

}