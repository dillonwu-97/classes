/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac CarFax.java
	Execution: 		java CarFax

	This calculates the total cost you have to pay for one car or another. It also determines which 
	delivers a better bang per buck.

	The difference between printing something and returning it is that printing something means
	you are informing the user of what is going on inside the computer. The return function 
	is involved in the execution of the program, describes how the function
	is giving back the value, and prescribes the type of value.
*/



public class CarFax {
	
	public static void main (String [] args) {
		System.out.println(compareCars(5));
		// At 5 years, you buy Car A.
		System.out.println(compareCars(10));
		// At 10 years, you buy Car B.
	}

	public static String compareCars (int years) {
		double aCost = 20000;
		double bCost = 30000;
		double aYearlyMilage = (15000 / 25) * 2.5; // ($$$ / year)
		double bYearlyMilage = (15000 / 32) * 2.5; // ($$$ / year)
		double aMaintanence = 1300;
		double bMaintanence = 1000;
		System.out.println("0" + "\t" + aCost + "\t" + bCost);
		for (int i = 0; i < years; i++) {
			aCost = aCost + aYearlyMilage * (i+1) + aMaintanence * Math.pow(1.15, i);
			// I use (i + 1) to calculate cost of mileage because you are still using
			// money to drive in the first year. However, for maintanence, I started
			// at 0 because at year 1, maintanence is at the base cost, which is $1300.
			// Therefore, interest does not yet need to be added to the base cost.
			bCost = bCost + bYearlyMilage * (i+1) + bMaintanence * Math.pow(1.1, i);
			System.out.println(i + "\t" + aCost + "\t" + bCost);
			}
		if (aCost < bCost) {
				return "Car A";
			} else {
				return "Car B";
			}
	}
}
