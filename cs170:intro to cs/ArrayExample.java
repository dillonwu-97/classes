public class ArrayExample {
	
	public static void main (String [] args) {
		int[] a; // declare an array of int
		a = new int[4]; // creates an array with four slots 
		a[0] = 7; // puts 7 into the first slot of the array
		a[1] = 3;
		a[2] = 15;
		a[3] = 9;
		System.out.println(a[2]);

		System.out.println(a); // ? 

		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();

		int[] b = new int[0]; // 
		int[] c = new int[7];
		c[0] = 1;
		c[1] = 2;
		c[2] = 6;
		c[3] = 15;
		c[4] = 2;
		c[5] = -1;
		c[6] = 33;

		System.out.println("sum: " + sumAll(a)); // 34
		System.out.println("sum: " + sumAll(b)); // 0

		System.out.println(arrayToString2(a));
		System.out.println(arrayToString2(b));

		System.out.println(minValue2(a));

		System.out.println(minIndex(a));
		System.out.println(minIndex(c));

		System.out.println(minIndex2(a));
		System.out.println(minIndex2(c));
	}

	// calculate the sum of all the elements in the array x

	public static int sumAll (int[] x) {
		int result = 0;
		for (int i = 0; i < x.length; i++) {
			result += x[i];
		}
		return result;
	}

	// return a string representation of an array
	// such as [a[0], a[1], ..., a[n-1]]
	// make sure the method doesn't crash
	// if the array is empty (zero elements)
	// Example: [7, 3, 15, 9]
	public static String arrayToString (int[] x) {
		String result = "[";
		for (int i = 0; i < x.length - 1; i++) {
			result = result + x[i] + ", ";
		}
		if (x.length > 0) {
			result = result + x[x.length - 1];
		}
		return result + "]";
	}

	public static String arrayToString2 (int[] x) {
		String result = "";
		for (int i = 0; i < x.length - 1; i++) {
			result = result + x[i] + ", ";
		}
		if (x.length > 0) {
			result = result + x[x.length - 1];
		}
		result = "[" + result + "]";
		return result;
	}

	// find the minimum element in array x
	// assume x is not empty 

	public static int minValue (int[] x) {
		int result = x[0];
		for (int i = 1; i < x.length; i++) {
			if (result < x[i]) {
				result = result;
			} else { 
				result = x[i];
			}
		}
		return result;
	}

	// A Better Version
	public static int minValue2 (int[] x) {
		int result = x[0];
		for (int i = 1; i < x.length; i++) {
			if (result > x[i]) {
				result = x[i];
			}
		}
		return result;
	}


	// find the INDEX of the minimum element in array x
	// assume x is not empty
	public static int minIndex(int[] x) {
		int result = x[0];
		int index = 0;
		for (int i = 1; i < x.length; i++) {
			if (result > x[i]) {
				index = i;
			}

		}
		return index;
	}

	// ALTERNATIVE
	public static int minIndex2 (int[] x) {
		int result = 0; // first INDEX
		for (int i = 1; i < x.length; i++) {
			if (x[result] > x[i]) {
				result = i;
			}
		}
		return result;
	}































}