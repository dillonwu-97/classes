import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GuessingGame2 {

	// fill in code here
	// define data members

	public int guessTries;
	public int startNum;
	public ArrayList<Integer> test = new ArrayList<Integer>();
	public int thous; // thousands number
	public int hun; // hundreds number
	public int tens; // tens number
	public int ones; // ones number

	public int counter; // to reset the array 
	public ArrayList<Integer> testCopy = new ArrayList<Integer>(); // also used for resetting the array

	public GuessingGame2 ( ) {
		// fill in code here
		// initialization
		guessTries = 1;
		for (int i = 1000; i < 10000; i++) {
			test.add(i);
		}
		startNum = test.get((int)(Math.random()*test.size()));
		thous = startNum / 1000 * 1000;
		hun = (startNum - thous) / 100 * 100;
		tens = (startNum - thous - hun) / 10 * 10;
		ones = startNum - thous - hun - tens;

		// copies the original array
		for (int i = 1000; i < 10000; i++) {
			testCopy.add(i);
		}
		counter = 0;


	}

	public int myGuessIs() {
		// fill in code here
		return startNum;
	}
	
	public int totalNumGuesses() {
		// fill in code here
		// this should return the total number of guesses taken
		return guessTries;
	}
 
	public void updateMyGuess(int nmatches) {
		// fill in code here
		// update the guess based on the number of matching digits claimed by the user
		// Notes: index 0 = 1000 so index 8999 = 9999
		// what happens if this is zero? Does it still work?
		thous = thousRound(startNum);
		hun = hunRound(startNum);
		tens = tensRound(startNum);
		ones = firstRound(startNum);
		if (nmatches == 0) {
			// get rid of the thousandths place numbers
			for (int x = 0; x < test.size(); x++) {
				if ((thousRound(test.get(x)) == thous) || (hunRound(test.get(x)) == hun) || 
					(tensRound(test.get(x)) == tens) || (firstRound(test.get(x)) == ones)) {
					test.set(x, 0);
				}
			}
		}
			
		if (nmatches == 1) {
			for (int i = 0; i < test.size(); i++) {
				// if none of th4e digits in the variable case is the same as
				// startNum, then you replace it with 0.
				if (oneSame(startNum, test.get(i)) == false) {
					test.set(i, 0);
				}
			}
		}

		if (nmatches == 2) {
			for (int i = 0; i < test.size(); i++) {
				// if two of the digits are the same, keep the number or else you discard it
				if (twoSame(startNum, test.get(i)) == false) {
					test.set(i, 0);
				}
			}
		}

		if (nmatches == 3) {
			for (int i = 0; i < test.size(); i++) {
				// if three of the digits are the same, keep the number or else you discard it
				if (threeSame(startNum, test.get(i)) == false) {
					test.set(i, 0);
				}
			}
		}
		// getting a new startNum
		startNum = test.get((int)(Math.random()*test.size()));
		while (startNum == 0) {
			startNum = test.get((int)(Math.random()*test.size()));
		}

		guessTries++;

		for (int i = 0; i < test.size(); i++) {
			System.out.println(test.get(i));
		}

		// if all numbers in array are 0, then reset
		/*
		for (int i = 0; i < test.size(); i++) {
			if (test.get(i) == 0) {
				counter++;
			}
		}
		if (counter == 9000) {
			for (int i = 0; i < 9000; i++) {
				test.set(i, testCopy.get(i));
			}
		}
		*/

		// FASTER WAY: IF SEEN VARIABLE BEFORE, THEN DELETE IT
	
	}
	public int thousRound (int num) {
		int result = num/1000;
		return result;
	}

	public int hunRound (int num) {
		int result = ((num%1000) / 100) * 100;
		return result;
	}

	public int tensRound (int num) {
		int result = (num%100)/10 *10;
		return result;
	}

	public int firstRound (int num) {
		int result = num%10;
		return result;
	}
	// this returns true if exactly three of the digits are the same
	// if 
	// this returns true if one or more of the digits are the same
	public boolean oneSame (int control, int variable) {
		boolean result = false;
		if (thousRound(control) == thousRound(variable) && variable !=0) {
			result = true;
			return result;
		} else 
		if (hunRound(control) == hunRound(variable) && variable !=0) {
			result = true;
			return result;
		} else
		if (tensRound(control) == hunRound(variable) && variable !=0) {
			result = true;
			return result;
		} else
		if (firstRound(control) == firstRound (variable) && variable !=0) {
			result = true;
			return result;
		} else {
			return result;
		}
	}

	// this returns true if there are two digits that are the same
	// it will do so with a counter

	public boolean twoSame (int control, int variable) {
		boolean result = false;
		// 1000,100
		if (thousRound(control) == thousRound(variable) && hunRound(control) == hunRound(variable) && variable !=0) {
			result = true;
			return result;
		} else
		// 1000, 10
		if (thousRound(control) == thousRound(variable) && tensRound(control) == tensRound(control) && variable !=0) {
			result = true;
			return result;
		} else
		// 1000, 1
		if (thousRound(control) == thousRound(variable) && firstRound(control) == firstRound(control) && variable !=0) {
			result = true;
			return result;
		}  else
		// 100, 10
		if (hunRound(control) == hunRound(variable) && tensRound(control) == tensRound(variable) && variable !=0) {
			result = true;
			return result;
		} else
		// 100, 1
		if (hunRound(control) == hunRound(variable) && firstRound(control) == firstRound(variable) && variable !=0) {
			result = true;
			return result;
		} else
		// 10, 1
		if (firstRound(control) == firstRound (variable) && tensRound(control) == tensRound(control) && variable !=0) {
			result = true;
			return result;
		} else {
			return result;
		}
	}

	public boolean threeSame (int control, int variable) {
		// 1000, 100, 10
		boolean result = false;
		if (thousRound(control) == thousRound(variable) && hunRound(control) == hunRound(variable) && 
			tensRound(control) == tensRound(variable) && variable !=0) {
			result = true;
		} 
		// 100, 10, 1
		if (hunRound(control) == hunRound(variable) && tensRound(control) == tensRound(variable) && 
			firstRound(control) == firstRound(variable) && variable !=0) {
			result = true;
		} 
		// 1000, 100, 1
		if (thousRound(control) == thousRound(variable) && hunRound(control) == hunRound(variable) && 
			firstRound(control) == firstRound (variable) && variable !=0) {
			result = true;
		}
		// 1000, 10, 1
		if (thousRound(control) == thousRound(variable) && firstRound(control) == firstRound (variable) &&
			tensRound(control) == tensRound(variable) && variable !=0) {
			result = true;
		} 
		return result;
	}
	
	// fill in code here (optional)
	// feel free to add more methods as needed
	
	// you shouldn't need to change the main function
	public static void main(String[] args) {

		GuessingGame2 gamer = new GuessingGame2( );
  
		System.out.println("Let's play a game. Think of a number between 1000 and 9999");
        
        System.out.println("Press \"ENTER\" when you are ready");
        
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        
		int numMatches = 0;
		int myguess = 0;
		
		do {
			myguess = gamer.myGuessIs();
			if (myguess == -1) {
				System.out.println("I don't think your number exists!");
				System.exit(0);
			}
			System.out.println("I guess your number is " + myguess + ". How many digits did I guess correctly?");
			String userInput = scanner.nextLine();
			
			// quit if the user input nothing (such as pressed ESC)
			if (userInput == null)
				System.exit(0);
			// parse user input, pop up a warning message if the input is invalid
			try {
				numMatches = Integer.parseInt(userInput.trim());
			}
			catch(Exception exception) {
				System.out.println("Your input is invalid. Please enter a number between 0 and 4");
				continue;
			}
			// the number of matches must be between 0 and 4
			if (numMatches < 0 || numMatches > 4) {
				System.out.println("Your input is invalid. Please enter a number between 0 and 4");
				continue;
			}
			if (numMatches == 4)
				break;
			// update based on user input
			gamer.updateMyGuess(numMatches);
			
		} while (true);
		
		// the game ends when the user says all 4 digits are correct
		System.out.println("Aha, I got it, your number is " + myguess + ".");
		System.out.println("I did it in " + gamer.totalNumGuesses() + " turns.");
	}
}
