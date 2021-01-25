/*
	Author: 		Dillon Wu
	Written: 		October 17, 2017

	Compilation: 	javac Swears.java
	Execution: 		java Swears

	This program takes a sentence and a couple of swear words and bleeps them out.
*/

public class Swears {
	
	// I broke the function down into two smaller functions.

	public static String swearFilter (String text, String[] swear) {
		String resultFin = text;
		
		for (int i = 0; i < swear.length; i++) {
			resultFin = swearReplace(resultFin, swear[i]);
		}
		return resultFin;
	}
	
	// This takes a sentence and one swear word, and replaces only that swear word.
		
	public static String swearReplace (String text, String word) {
		String result = "";

		if (text.length() < word.length()) {
			return text;
		}
		// It goes through each string of words that match the length of the string of the swear
		for (int i = 0; i < text.length() - word.length() + 1; i++) {
			
			String potentialCurse = text.substring(i, i + word.length());
			
			// This is to make sure capital letters in the text are accounted for.
			String potentialCurseL = potentialCurse.toLowerCase();
			
			// Words needs to be lower case in case the swear is capitalized.
			if (potentialCurseL.equals(word.toLowerCase())) {
				result = result + replace(potentialCurse);
				i = i + word.length() - 1;
				//System.out.println(result);
			} else {
				result = result + text.charAt(i);
				//System.out.println(result);
			}
		}
		result = result + text.substring(text.length() - (word.length() - 1), text.length());
		return result;
	}
	

	// This replaces the word with a string containing the word's first and last letters,
	// with stars in between.
	public static String replace (String s) {
		String stars = "";
		for (int i = 1; i < s.length() - 1; i++) {
			stars = stars + "*";
		}
		String fullWord = s.charAt(0) + stars + s.charAt(s.length()-1);
		return fullWord;
	}

	public static void main (String[] args) {

		// Testing replace method
		// System.out.println(replace("whole"));

		// Testing the sentence replace method.
		String test1 = "A duck was sailing on a ship shipping whole wheat bread. Duck that SHIP!!!";
		//System.out.println(swearReplace (test1, "duck"));

		String test2 = "";

		// Declaring the arrays
		String[] x1 = new String[3];
		x1[0] = "duck";
		x1[1] = "whole";
		x1[2] = "ship";

		String[] x2 = new String[3];
		x2[0] = "sail";
		x2[1] = "on"; // Nothing to bleep out for two letter words
		x2[2] = "WHEAT";

		String[] x3 = {}; 

		String test3 = "DUCK CS170!!!";

		String test4 = "duck this ship. The whole ducking class needs to be easier.";

		System.out.println(swearFilter (test1, x1));
		System.out.println(swearFilter (test1, x2));
		System.out.println(swearFilter (test1, x3)); // When nothing is a swear.
		System.out.println(swearFilter (test2, x1)); // Should return an empty string.
		System.out.println(swearFilter (test2, x3)); // Should return an empty string. 
		System.out.println(swearFilter (test3, x1)); // The string is all caps.
 		System.out.println(swearFilter (test4, x1)); // The string is all lower case.


	}
}