/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Vowels.java
	Execution: 		java Vowels

	Shows how many vowels are in a word / sentence 
*/

public class Vowels {
	
	public static void main (String[] args) {
		System.out.println(countVowels("hello")); // returns 2
		System.out.println(countVowels("aeiou")); // returns 5
		System.out.println(countVowels("klptnm")); // returns 0
		System.out.println(countVowels("U")); // returns 1;
		System.out.println(countVowels("HAPPY TIMES")); // returns 3;

	}

	// I created an "if" for each vowel
	
	public static int countVowels (String s) {
		int count = 0;
		String newWord = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			if (newWord.charAt(i) == 'a') {
				count = count + 1;
			} else if (newWord.charAt(i) == 'e') {
				count = count + 1;
			} else if (newWord.charAt(i) == 'i') {
				count = count + 1;
			} else if (newWord.charAt(i) == 'o') {
				count = count + 1;
			} else if (newWord.charAt(i) == 'u') {
				count = count + 1;
			} else {
				count = count + 0;
			}
		}
		return count;
	}
}