public class Accumulation {
	
	public static void main (String [] args) {
		System.out.println("--- Test sumN ---");
		System.out.println(sumN(0)); // expect 0
		System.out.println(sumN(1)); // expect 1
		System.out.println(sumN(2)); // expect 3
		System.out.println(sumN(5)); // expect 15

		System.out.println("--- Test multN ---");
		System.out.println(multN(0)); // expect 1 because you start result from 1
									  // and the loop never runs
		System.out.println(multN(1)); // expect 1
		System.out.println(multN(2)); // expect 2
		System.out.println(multN(5)); // expect 120
		
		System.out.println("--- Test sumMult5 ---");
		System.out.println(sumMult5(0)); // expect 0
		System.out.println(sumMult5(3)); // expect 0
		System.out.println(sumMult5(5)); // expect 5
		System.out.println(sumMult5(12)); // expect 10
		System.out.println(sumMult5(15)); // expect 15
		System.out.println(sumMult5(16)); // expect 15
		
		System.out.println(multiplyString("h", 5)); 

	}

	// Sum all the integers between 1 and n (included)
	public static int sumN (int n) {
		int result = 0;
		for (int i = 1; i < n + 1; i++) {
			result = result + i; // update the result
		}
		return result; 	
	}

	// Multiply all the integers between 1 and n (included)
	public static int multN(int n) {
		int result = 1;
		for (int i = 1; i < n + 1; i++) {
			result = result * i; // update the result
		}
		return result;
	}

	// What is the common structure in those examples?
	// Accumulation strategy:
	// 1) Initialize a variable that will contain the result
	// 2) Update the result inside a loop
	// 3) After the loop, collect and return the result 

	// Sums all the integer multiples of 5 up to n (included)
	public static int sumMult5(int n) {
		int result = 0;
		for (int i = 5; i <= n; i += 5) { // means i = i = 5 
			result += i; // same as result = result + i
		}
		return result;

	}

	// Multiply a given string n times
	// example: 
	// multiplyString ("hey", 3) returns "heyheyhey"
	public static String multiplyString (String s, int n) {
		String result = ""; 
		for (int i = 0; i < n; i++) {
			result += s;
		}
		return result;
	}

}







