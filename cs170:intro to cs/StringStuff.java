public class StringStuff {

	public static void main (String[] args) {
		char c = 'A'; // one character
		String s1 = "ABCD"; // String with many characters
		String s2 = "A"; // String with one character
		String s3 = ""; // empty string
		// char cs = ''; // ERROR

		System.out.println(c);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);

		// A char is actually a number.
		// See www.asciitable.com
		System.out.println('A' + 1); // 66 (int)
		System.out.println((char)('A' + 1)); // B (char)
		System.out.println("A" + 1); // A1 (String)

		// Operations with strings
		char k = s1.charAt(2);
		System.out.println(k); // C

		int n = s1.length();
		System.out.println(n); // 4

		String s4 = "Hello Everyone";
		System.out.println(s4.substring(3, 8)); // lo Ev
		System.out.println(s4.substring(8)); // eryone
		System.out.println(s4.toUpperCase());
		System.out.println(s4.toLowerCase());

		// Compare two strings
		// Don't use == when you compare two strings 
		String x = "hey";
		String y = "you";
		String z = "hey";
		System.out.println(x.equals(y)); // false
		System.out.println(x.equals(z)); // true
		System.out.println(x.compareTo(y)); // < 0
		System.out.println(y.compareTo(x)); // > 0
		System.out.println(x.compareTo(z)); // = 0

		System.out.println(mySubstring (s4, 3, 8));
		System.out.println(mySubstring (s4, 3, 8, 2)); // l v
		System.out.println(mySubstring (s4, 3, 8, 3)); // lE

		System.out.println(reverse ("abcde"));

		System.out.println(mergeStrings ("ABCD", "1234"));
		System.out.println(mergeStrings ("AB", "1234"));

	}

	// Examples:
	// mySubstring ("Hello Everyone", 3, 8) returns "lo Ev"
	// mySubstring ("")

	public static String mySubstring (String s,  int start, int end) {
		String newString = "";
		for (int i = start; i < end; i++) {
			newString = newString + s.charAt(i);
		}
		return newString;

	}
	public static String mySubstring (String s,  int start, int end, int step) {
		String newString = "";
		for (int i = start; i < end; i = i + step) {
			newString = newString + s.charAt(i);
		}
		return newString;
	}

	// Example: reverse( "ABCDE") --> "EDCBA"
	public static String reverse (String s) {
		String newString = "";
		for (int i = s.length() - 1; i >= 0; i--) {
			newString = newString + s.charAt(i);
		}
		return newString;
	}

	public static String mergeStrings (String s1, String s2) {
		String newString = "";
		if (s1.length() == s2.length()) {
			for (int i = 0; i < s1.length(); i ++) {
				newString = newString + s1.charAt(i) + s2.charAt(i);
			} 
		} else if {
			 (s1.length() < s2.length()) {
				for (int i = 0; i < s1.length(); i ++) {
					newString = newString + s1.charAt(i) + s2.charAt(i);
				}
				for (int j = s1.length; j < s2.length; j++) {
					newString = newString + s1.charAt(j) + s2.charAt(j);
				}
			} 
		}
	return newString;

	










	
}
