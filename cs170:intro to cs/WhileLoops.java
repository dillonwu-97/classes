public class WhileLoops {
	public static void main (String[] args) {
		manyHellos(3);
		System.out.println(sumN(3)); // returns 6
		System.out.println(sumN(5)); // returns 15
		System.out.println(sumN(12)); // returns 78
		System.out.println(sumN(4)); // returns 10


		System.out.println(yearsToTarget(1000, 0.05, 2000)); //15

		System.out.println(breakEvenQuantity(1, 0.7, 1000)); // 2000
		System.out.println(breakEvenQuantity(1, 1.5, 1000)); // INFINITE LOOP


	}

	// Example 1: Simple repitition
	public static void manyHellos (int n) {
		int i = n; // inititalization
		while (i > 0) {	// loop header; condition
			// loop body
			System.out.println ("Hello" + i);
			i--; // same as i = i - 1
		}
		System.out.println("Goodbye");
	}


	// Example 2: Accumulation
	// Problem: sum up the first n integers
	// (including n) using a while loop
	public static int sumN(int n) {
		int sum = 0;
		while (n > 0) {
			sum +=  n;
			n--;
		}
		return sum;
	}

	// Example 3: Compound Interest
	// Problem: Given an initial amount of money, 
	// and a fixed (compound) interest rate,
	// how many years will it take to reach
	// a certain final amount?
	public static int yearsToTarget (double initMoney, double interestRate, 
													  double targetMoney) {
		double currentMoney = initMoney;
		int year = 0;
		System.out.println("Year\tCurrent Money"); // t is a tab
		System.out.println(year + "\t" + currentMoney); //t is a tab

		while (currentMoney < targetMoney) {
			//currentMoney = currentMoney + currentMoney * interestRate; 
			currentMoney *= 1 + interestRate;
			year++;
			System.out.println(year + "\t" + currentMoney); // t is a tab

		}
		return year;
	}

	// Solving Example 3 with a FOR loop is possible but less intuitive;
	// for (doubling = money = initMoney; money < targetMoney; money *= 1 + interestRate)

	// Example Buy or make?

	public static int breakEvenQuantity (double p, double c, double k) {
		int quantity = 0;
		double buyCost = 0;
		double produceCost = k;
		while (buyCost < produceCost) {
			quantity++;	
			buyCost = buyCost + p;
			produceCost = produceCost + c;
		}
		return quantity;
	}










}