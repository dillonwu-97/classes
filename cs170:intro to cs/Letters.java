/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Letters.java
	Execution: 		java Letters

	Counts the number of times a certain letter appears
*/
public class Letters {
	
	// These are the test cases that were shown in the hw pdf.

	public static void main (String[] args) {
		System.out.println(letterCount("abc-123-abc-ABC", "b")); // 2
		System.out.println(letterCount("aaaa", "a")); // 4
		System.out.println(letterCount("This is interesting!", "z")); // 0
		System.out.println(letterCount("", "q")); // 0

	}

	// This transforms the strings into chars and counts them

	public static int letterCount (String s, String c) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)== c.charAt(0)) {
				count = count + 1;
			}
		}
		return count;
	}
}