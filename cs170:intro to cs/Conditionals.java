public class Conditionals {
	
	public static void main (String [] args) {
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
		System.out.println(stateOfWater(0)); // edge case
		System.out.println(stateOfWater(30));
		System.out.println(stateOfWater(100)); // edge case
		System.out.println(stateOfWater(150));

		System.out.println(letGrade(1000));
		System.out.println(letGrade(900));
		System.out.println(letGrade(800));
		System.out.println(letGrade(700));
		System.out.println(letGrade(600));
		System.out.println(letGrade(650));
		System.out.println(letGrade(750));
		System.out.println(letGrade(850));
		System.out.println(letGrade(550));
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



	// Returns a letter grade given the number of points
	public static String letGrade (double numGrade) {
		String grade;
		if (numGrade >= 900) {
			grade = "A";
		} else if (numGrade >= 800) {
			grade = "B";
		} else if (numGrade >= 700) {
			grade = "C";
		} else if (numGrade >= 600) {
			grade = "D";
		} else {
			grade = "F";
		}
		return grade;
	}

	// 








}