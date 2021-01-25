/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac SomeMath.java
	Execution: 		java SomeMath

	Does some basic calculations 
*/


public class SomeMath {
	
	public static void main (String[] args) {
		System.out.println(sumOfSquares(4));	// Expectin' 30
		System.out.println(sumOfSquares(0));	// To make sure zero still returns the right value

		System.out.println(product(3, 2));		// No multiplication; just addition
		System.out.println(product(0, 3));		// To make sure that the zeros work
		System.out.println(product(3, 0));
		System.out.println(product(1, 90));

		System.out.println(compoundInterest(1000, 0.05, 3)); // Computes the compound interest #$$

		System.out.println(polySpiralLength(3, 20, 5)); //
		
	}

	public static int sumOfSquares (int n) {
		int result = 0;
		for (int i = 1; i <= n; i++) {
			result = result + i * i;
		}
		return result;
	}

	public static int product (int x, int y) {
		int result = 0;
		for (int i = 0; i < y; i ++) {
			result = result + x;
		}
		return result;
	}


	public static double compoundInterest (double money, double interestRate, int years) {
		double result = money;
		for (int i = 1; i <= years; i++){
			result = result + result * interestRate;
		}
		return result;
	}

	public static double polySpiralLength (int n, double base, int rounds) {
		double result = 0;
		for (int i = 1; i <= n * rounds; i++) {
			result = result + base * i;
		}
		return result;
	}



}