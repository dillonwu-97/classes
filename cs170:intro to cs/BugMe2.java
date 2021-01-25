public class BugMe2 {

	public static void main (String[] args) {

		System.out.print(yearsToTarget(1000, 0.05, 2000)); //15

		System.out.println("--- Test smallest Number ---");
		System.out.println(smallestNumber(5,2)); // 2.0
		System.out.println(smallestNumber(2,5)); // 2.0
		System.out.println(smallestNumber(5,5)); // 5.0

		System.out.println(longestString("hello", "hello world"));
		System.out.println(longestString("economics", "mathematics"));
		System.out.println(longestString("mathematics", "economics"));
		System.out.println(longestString("bbb", "aaa")); // aaa
		System.out.println(longestString("aaa", "bbb")); // bbb

		System.out.println(stateOfWater(-10));
		System.out.println(stateOfWater(-0)); // edge case
		System.out.println(stateOfWater(30));
		System.out.println(stateOfWater(100)); // edge case
		System.out.println(stateOfWater(150));

		
		System.out.println("--- Test sumMult5 ---");
		System.out.println(sumMult5(0)); // expect 0
		System.out.println(sumMult5(3)); // expect 0
		System.out.println(sumMult5(5)); // expect 5
		System.out.println(sumMult5(12)); // expect 10

		System.out.println(multiplyString("h", 5)); 

	}
	
	// Example 3: Compound Interest
	// Problem: Given an initial amount of money, 
	// and a fixed (compound) interest rate,
	// how many years will it take to reach
	// a certain final amount?
	public static int yearsToTarget (double targetMoney, double interestRate, 
													  double initMoney) {
		double currentMoney = initMoney;
		int year = 0;
		System.out.println("Year" + "\t" + "Current Money"); // t is a tab
		System.out.println(year + "\t" + currentMoney); //t is a tab

		while (currentMoney < targetMoney) {
			//currentMoney = currentMoney + currentMoney * interestRate; 
			currentMoney *= 1 + interestRate;
			year = year + 1;
			System.out.println(year + "\t" + currentMoney); // t is a tab
		}
		
		return year;
	}

	// Returns the smallest of two numbers
	public static double smallestNumber (double x, double y) {
		if (x < y) {
			return x;
		} else {
			return y;
		}
	}

	// Returns the longest string
	public static String longestString (String s1, String s2) {
		int x = s1.length();
		int y = s2.length();
		if (x > y) {
			return s1;
		} else {
			return s2;
		}
	}

	// Returns the state of water
	public static String stateOfWater (double temp) {
		String state;
		if (temp < 0) {
			state = "solid";
		} else if (temp <= 100) {
			state = "liquid";
		} else {
			state = "gas";
		}
		return state;
	}
	

	
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
			result = result + s;
		}
		return result;
	}



}