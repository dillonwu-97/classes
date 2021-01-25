public class Recursion {
	
	public static void main (String[] args) {
		// test powerOfTwo
		System.out.println(powerOfTwo(0));
		System.out.println(powerOfTwo(1));
		System.out.println(powerOfTwo(2));
		System.out.println(powerOfTwo(4));
		System.out.println(powerOfTwo(5));

		// test factorial
		System.out.println(factorial(0));
		System.out.println(factorial(1));
		System.out.println(factorial(2));
		System.out.println(factorial(3));
		System.out.println(factorial(4));

		// test reverse
		System.out.println(reverse("A"));
		System.out.println(reverse("ABCDE"));

		// test palindrome
		System.out.println(palindrome(""));
		System.out.println(palindrome("A"));
		System.out.println(palindrome("kayak"));
		System.out.println(palindrome("kayay"));
		System.out.println(palindrome("kayyak"));

	}

	public static int powerOfTwo (int n) {
		if (n == 0) { // base case
			return 1;
		} else { // recursive case
			return 2 * powerOfTwo(n - 1);
		}
	}

	// 0! = 1
	// 3! = 3 * 2 * 1
	public static int factorial (int n) {
		if (n == 0) {
			return 1;
		} else {
			return n * factorial (n - 1);
		}
	}

	public static String reverse (String s) {
		if (s.length() <= 1) {
			return s;
		} else {
			return s.substring(s.length()-1) + reverse(s.substring(0,s.length()-1));
		}
	}

	public static boolean palindrome(String s) {
		if (s.length() <= 1) {
			return true;
		} else {
			return (s.charAt(0) == s.charAt(s.length()-1) && palindrome(s.substring(1, s.length() - 1)));
		}
	}
}