/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
	Author: 		Dillon Wu
	Written: 		February 3, 2018

	Compilation: 	javac GuessingGame.java
	Execution: 		java GuessingGame

	This code tries to get the computer to guess the number you are thinking of. 
*/


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

	// fill in code here
	// define data members

	public int guessTries; // This keeps track of the number of attempts made to guess a number
	public int startNum; // This is the number that we start with 
	public ArrayList<Integer> test = new ArrayList<Integer>();
	public int counter0; // to check the thousandths position
	public int counter1; // to check the 100's position
	public int counter2; // to check the 10's position
	public int counter3; // to check the 1's position
	public boolean flag0; // when 0 numbers match initially
	public boolean flag1; // when 1 number matches initially
	public boolean flag2; // when two numbers match initially
	public boolean flag3; // when three numbers match initially
	public boolean trigger;

	public GuessingGame ( ) {
		// fill in code here
		// initialization
		guessTries = 1;
		startNum = 1000;
		for (int i = 1000; i < 10000; i++) {
			test.add(i);
		}
		counter0 = 0;
		counter1 = 0;
		counter2 = 0;
		counter3 = 0;
		flag0 = false; 
		flag1 = false; 
		flag2 = false; 
		flag3 = false;
		trigger = false;
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
		try{
			
			// if there are three zeros
			// flags only open once
			// In this scenario, there are three digits we already know. If one "good" digit is removed,
			// then there is a trigger to return that digit to its rightful place and test the "goodness" of 
			// other digits.
			if ((nmatches == 3 && flag2 == false && flag1 == false && flag0 == false)) {
				flag3 = true;
			}
			if (flag3 == true) {
				if (nmatches == 3 && startNum%1000 == 0) {
					startNum = test.get(startNum);		
				}
				if (nmatches == 2 && startNum %1000==0) {
					startNum = startNum - 1000;
					startNum = test.get(startNum -1000 + 100);
				} else
				if (nmatches == 2 && (startNum%1000!=0) && startNum %100 ==0) {
					startNum = startNum - 100;
					startNum = test.get(startNum - 1000 + 10);
				} else
				if (nmatches == 2 && startNum%100!=0 && startNum%10 ==0) {
					startNum = startNum - 10;
					startNum = test.get(startNum-1000 + 1);
				} else if (nmatches == 2 && startNum%10!=0) {
					startNum = 1000; // reset in case use missed the number
				}
				// If the hundreds digit needs to be changed and all else kept
				if (nmatches == 3 && startNum%100 == 0 && startNum %1000 !=0) {
					startNum = test.get(startNum - 1000 +100);
					counter1++;
					if (counter1 >= 9) {
						startNum = startNum - 1000;
						counter1 = 0;
					}
				}
				// if the tens digit needs to be changed and all else kept
				if (nmatches == 3 && startNum%10 == 0 && startNum %100 !=0 && startNum %1000!=0) {
					startNum = test.get(startNum - 1000 +10);
					counter2++;
					if (counter2 >= 9) {
						startNum = startNum - 100;
						counter2 = 0;
					}
				}
				// if the ones digit needs to be changed and all else kept
				if (nmatches == 3 && startNum %10 !=0) {
					startNum = test.get(startNum - 1000 +1);
					counter3++;
					if (counter3 >= 9) {
						startNum = startNum - 10;
						counter3 = 0;
					}
				}

			}
			
			// if all else fails, just make everything not 1000, and then open flag0
			// Hard to identify which of the digits in 1000 are the ones to keep and the ones to change, so I 
			// just change the number into something else I know for certain will have 0 accurate digits.
			if ((nmatches == 2 && flag3 == false && flag1== false && flag0==false)) {
				flag2 = true;
			}
			if (flag2 == true) {
				if (startNum == 1000) {
					startNum = 2111;
				}

				if (nmatches == 0) {
					startNum = test.get(startNum);
					counter0++;
					if (counter0 >= 8) {
						startNum = startNum - 1000;
						counter0 = 0;
					}
				}
				if (nmatches == 1) {
					startNum = test.get(startNum-1000 + 100);
					counter1++;
					if (counter1 >= 9) {
						startNum = startNum - 1000;
						counter1 = 0;
					}
				}
				if (nmatches == 2) {
					startNum = test.get(startNum-1000 + 10);
					counter2++;
					if (counter2 >= 9) {
						startNum = startNum - 100;
						counter2 = 0;
					}
				}
				if (nmatches == 3 && ((startNum%1000 !=0))) {
					startNum = test.get(startNum-1000 + 1);
					counter3++;
					if (counter3 >=9) {
						startNum = startNum - 10;
						counter3 = 0;
					}
				}
			}
			
			// Same idea except instead of 2 matches, I have 1 match and I reduce it to 0 matches 
			// so that I can activate flag0.
			if ((nmatches == 1 && flag2 == false && flag3 == false && flag0 == false)) {
				flag1 = true;
			}
			if (flag1 == true) {
				if (nmatches == 1) {
					startNum = test.get(startNum);
				}
				if (nmatches == 0 && startNum%1000 == 0) {
					// this means thousand is the digit that is correct
					startNum = 1000;
				} else {
					trigger = true; // if trigger is true, then that means another digit is the correct one and so 
					// we find a different number
				}
				if (trigger == true) {
					startNum = test.get(startNum - 2000 + 100);
					trigger = false;
				}
				if (nmatches == 0 && startNum%100 == 0 && startNum%1000!= 0) {
					// this means hundreds is the digit that is correct
					startNum = 1011;
				}
/*

				else if (nmatches >= 1 && startNum != 2000 && startNum !=1100) {
					startNum = test.get(startNum - 2000 + 100);
				}
				if (nmatches == 0 && startNum%100 == 0 && startNum%1000!= 0) {
					// this means hundreds is the digit that is correct
					startNum = 1011;
				} else if (nmatches >= 1 && startNum != 1100 && startNum!=2000) {
					startNum = test.get (startNum - 1000 - 100 + 10);
				}
				if (nmatches == 0 && startNum%10 == 0 && startNum%100!=0) {
					// this means tens is the digit that is correct)
					startNum = 1101;
				} else if (nmatches >= 1 && startNum%10!=0) {
					// this means ones place is the digit that is correct
					startNum = 1110;
				}
				*/
			}
			
			// This is if there are no matching digits at the onset. This is the easiest case. Basically,
			// just increment each digit until you come upon the desired number.
			if (nmatches == 0 && flag1==false&&flag2 == false && flag3==false) {
				flag0=true;
			}
			if (flag0 == true) {
				if (nmatches == 0) {
					startNum = test.get(startNum);
				}
				if (nmatches == 1) {
					startNum = test.get(startNum-1000 + 100);
					counter1++;
					if (counter1 >= 10) {
						startNum = startNum - 1000;
						counter1 = 0;
					}
				}
				if (nmatches == 2) {
					startNum = test.get(startNum-1000 + 10);
					counter2++;
					if (counter2 >= 10) {
						startNum = startNum - 100;
						counter2 = 0;
					}
				}
				if (nmatches == 3 && ((startNum%1000 !=0))) {
					startNum = test.get(startNum-1000 + 1);
					counter3++;
					if (counter3 >=10) {
						startNum = startNum - 10;
						counter3 = 0;
					}
				}
			}


		}
		// This is in case the number exceeds 9999.
		catch(Exception e) {
			System.out.println("You missed the number; try again.");
			startNum = 1000;
		}
		guessTries++;
	}
	
	// fill in code here (optional)
	// feel free to add more methods as needed
	
	// you shouldn't need to change the main function
	public static void main(String[] args) {

		GuessingGame gamer = new GuessingGame( );
  
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
