/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Words.java
	Execution: 		java Words

	Counts the number of words in the string
*/



public class Words {
	
	// These are all the possible combinations

	public static void main (String [] args) {
		System.out.println (wordCount("Hello World")); // 2
		System.out.println (wordCount("Good bye me")); // 3
		System.out.println (wordCount("one two three four")); // 4
		System.out.println (wordCount("one      two    three    four")); // 4
		System.out.println (wordCount(" one two three four ")); // 4
		System.out.println (wordCount("     one two three four ")); // 4
		System.out.println (wordCount("     one two three    four      ")); // 4
		System.out.println (wordCount("1  two   three    four           ")); // 4
		System.out.println (wordCount("  1 1  two   three    four")); // 5

	}

	
	// If there is a space to the right or left of a letter, then it must be a word.
	// The only exceptions are if it is the first or last word.

	public static int wordCount (String s) {
		int count = 0;
		if (s.charAt(s.length()-1) != ' ' && s.charAt(0) != ' ') {
			for (int i = 1; i < s.length(); i++) {
				if (s.charAt(i-1) != ' ' && s.charAt(i) == ' ') {
					count = count + 1;
				}
			}
			return count + 1;	
		} else if (s.charAt(s.length() - 1) != ' ') {
			for (int i = 0; i < s.length() - 1; i++) {
				if (s.charAt(i+1) != ' ' && s.charAt(i) == ' ') {
					count = count + 1;
				}
			}
			return count;
		} else {
			for (int i = 1; i < s.length(); i++) {
				if (s.charAt(i-1) != ' ' && s.charAt(i) == ' ') {
					count = count + 1;
				}
			}
			return count;
		}
	}

}

	

