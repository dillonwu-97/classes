public class Recursion2 {
	public static void main(String[] args) {
		System.out.println(addDigits(5));
	}

	// sums all the digits of n (n >= 0)
	public static int addDigits (int n) {
		if (n < 10) {
			return n; 
		} else { 
			 return n % 10 + addDigits (n / 10);
		}
	}
}