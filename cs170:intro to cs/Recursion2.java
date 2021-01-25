public class Recursion2 {
	public static void main(String[] args) {
		System.out.println(addDigits(5));
		System.out.println(addDigits(37));

		int[] x1 = {};
		int[] x2 = {5};
		int[] x3 = {2, 1, 3};

		System.out.println(sum(x1, x1.length)); 
		System.out.println(sum(x2, x2.length));
		System.out.println(sum(x3, x3.length));
	}

	// sums all the digits of n (n >= 0)
	public static int addDigits (int n) {
		if (n < 10) {
			return n; 
		} else { 
			 return n % 10 + addDigits (n / 10);
		}
	}

	// sums n elements of array x (0 <= n <= x.length)
	public static int sum(int[] x, int n) {
		if (n == 0) {
			return 0; 
		} else {
			return x[n - 1] + sum(x, n-1);
		}
	}

	// counts the number of odd elements in x; n = length
	public static int oddCount (int[]x, int n) {
		if (n == 0) {
			return 0;
		} else if (x[n-1] % 2 == 1) { // odd
			return 1 + oddCount (x, n - 1);
		} else {
			return oddCount (x, n - 1);
		}
	}

	public static int oddCount2 (int[] x, int n) {
		if (n==0) {
			return 0;
		} else {
			return x[n - 1] % 2 + oddCount (x, n - 1);
		}
	}

	// returns the minimum element in x
	// assume that x is not empty

	public static int minElement (int[] x, int n) {
		if (n == 1) {
			return x[0];
		} else {
			int k = min(x, n - 1);
			if (x [n - 1] < k) {
				return x[n - 1];
			} else {
				return k;
			}
		}
	}

	// returns the index of the minimum element in x
	// assume that x is not empty
}