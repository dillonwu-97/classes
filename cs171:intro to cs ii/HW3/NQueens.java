/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       February 21, 2018

   Compilation:   javac NQueens.java
   Execution:     java NQueens

   For an n number of queens, this prints out all possible combinations that the queens
   can be placed on an n x n chessboard, and not hit each other.
*/

import java.util.Stack;

public class NQueens {
 
  // 1 throws a null exception so this is just to address that problem
  // if it is anything other than a 1, then the *real* program runs ^0^
    public static int solve (int number) {
      Stack<Integer> input = new Stack<Integer>();
      int result = 0;
      if (number == 1) {
        input.push(0);
        printSolution(input);
        result = 1;
      } else {
        result = placement(number, input);
      }
      return result;
    }

    // number represents the number of queens 
    // cTracker tracks the column, 0 is the first column
    public static int placement (int number, Stack<Integer> input) {
      // tracker keeps track of which column position we are in
      int rTracker = 0; // tracks the row position
      int cTracker = 0;
      int totalPrints = 0; // keeps track of how many prints there are      

      // if the stack is empty, put a number in
      // tracker needs to represent the row 
      while (rTracker <= number) {
        // cTracker represents the column position; if I need to backtrack, then I start from the 
        //column position
        // the program keeps running until I choose to break it

        // if I have the solution, print it, and then backtrack and continue
        if (rTracker == number && cTracker == 0) {
          printSolution(input);
          totalPrints++;

          //then backtrack and continue
          cTracker = 1 + input.peek();
          input.pop();
          rTracker--;
          // need to backtrack in this case otherwise you get stuck in a loop where you are
          // repeating the last row over and over again
          if (rTracker == number - 1 && cTracker == number) {
            cTracker = 1 + input.peek();
            input.pop();
            rTracker--;
          }

        }

        // to break out of the second while statement
        // this happens once the program is complete and there is nothing left to print
        if (cTracker == number && rTracker == 0) {
          break;
        }

        // extremely useful for determining where the trackers are
        //System.out.println("main row number:" + rTracker);
        //System.out.println("main col number:" + cTracker);
        // keep running so long as the column tracker is less than the number of queens i 
        // need to put on the board
        while (cTracker <= number) {
          
            // 0 means there are no conflicts
            if (finalTest(rTracker, cTracker, input) == 0) {
              input.push(cTracker);
              rTracker++;
              cTracker=0; // resets the column
              //System.out.println("row number after okay:" + rTracker);
              //System.out.println("col number after okay:" + cTracker);
              break; 
              // if there is a conflict and the end of the row is hit            
            } else if (cTracker == number-1 && finalTest(rTracker, cTracker, input)==1) {
              cTracker = 1 + input.peek();
              input.pop();
              rTracker--;
              //System.out.println("row number after backtrack method:" + rTracker);
              //System.out.println("col number after backtrack method:" + cTracker);
              
              // to prevent peeking into an empty stack
              if (cTracker == number && rTracker == 0) {
                break;
              }
              
              // moves back a row and starts from the position of column + 1
              // this prevents problems when i reach the end of a row
              if (cTracker ==number) {
                cTracker = 1 + input.peek();
                input.pop();
                rTracker--;
                //System.out.println("row number if hits a wall:" + rTracker);
                //System.out.println("col number if hits a wall:" + cTracker);                
              }
              break;
            }
            // this is if I can't put numbers in the current column
            if (finalTest(rTracker, cTracker, input) == 1) {
              cTracker++;
              //System.out.println("col number after test fails:" + cTracker);
            
            }
          }
        }
        return totalPrints;
      }

    // this combines the diagonal and column tests
    public static int finalTest (int row, int column, Stack<Integer> input) {
      // 1 means none of the column positions can be used
      int result = 1;
      if (columnTest(column, input) == 0 && diagTest(row, column, input) == 0) {
        result = 0;
      }
      return result;
    }

    
    public static int columnTest (int column, Stack<Integer> input) {
      // this returns true if there are no other elements that are the same as int column
      int result = 0;
    // if there are two or more elements in the stack
      if (input.size()>0) {
          for (int i = input.size(); i > 0; i--) {
            if (column == check(i,input)) {
                result = 1;
            }
          }
      }
      return result;
    }

    // this returns true if there is no element on the diagonal
    public static int diagTest (int row, int column, Stack<Integer> input) {
    // diagNum keeps track of the diagonal numbers that can't be used
      int result = 0;
    // runs through all the numbers in the stack
    // if current row - previous row = current column - previous column, then they are on a diagonal
    // start at input.size() because all the numbers in the stack represent the previous columns
      for (int i = input.size(); i > 0; i--) {
          //if (column - check(i,input) == row - (i-1)) {
          // the idea behind this is to test of the diagonal has a slope of 1 or -1
          if ((column - check(i, input)==  row-(i-1)) ||
              ((column - check(i, input))* -1) == (row-(i-1)) ) {
            return 1;
          
          }
      }
      return result;
    }

    // checks what value it is in the n position
    public static int check(int position, Stack<Integer> y) {
      int result = 0;
      Stack<Integer> storage = new Stack<Integer>();
      // clones the original stack
      clone(y, storage);
      
      for (int i = y.size(); i > 0; i--) {
        if (i == position) {
          result = y.pop();
        } else {
          y.pop();
        }
      }
      clone(storage, y);
      return result;

    }
    

    // clones stack original and puts it into an empty stack, which is the cloned one
    public static void clone(Stack<Integer> original, Stack<Integer> empty) {
      int temp = 0;
      Stack<Integer> storage = new Stack<Integer>();
      // makes the original size a constant; if I use original.size in the loop, then the size will
      // keep on changing since I am popping elements out of the stack
      int oSize = original.size();
      // this puts the stack into a temporary stack; the element order will be reversed.
      for (int i = 0; i < oSize; i++) {
        temp = original.pop();
        storage.push(temp);       
      }
      // this puts the temporary stack into the original stack and cloned stack. The elements will 
      // be reversed again, but this is okay because it just means it will go back to its original positions. 
      for (int i = 0; i < oSize; i++) {
        temp = storage.pop();
        original.push(temp);
        empty.push(temp);
      }
    }

  //this method prints out a solution from the current stack
  //(you should not need to modify this method)
  private static void printSolution(Stack<Integer> s) {
    for (int i = 0; i < s.size(); i ++) {
      for (int j = 0; j < s.size(); j ++) {
        if (j == s.get(i))
          System.out.print("Q ");
        else
          System.out.print("* ");
      }//for
      System.out.println();
    }//for
    System.out.println();  
  }//printSolution()
  
  // ----- the main method -----
  // (you shouldn't need to change this method)
  public static void main(String[] args) {
  
  int n = 8;
  //int m = 4;
  //int k = 5;
  /*
  for (int n = 1; n < 9; n++) {
    int number = solve(n);
    System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
  }
  */
  
  // pass in parameter n from command line
  
  if (args.length == 1) {
    n = Integer.parseInt(args[0].trim());
    if (n < 1) {
      System.out.println("Incorrect parameter");
      System.exit(-1);
    }//if   
  }//if
  
  int number = solve(n);
  //int number1 = solve(m);
  //int number2 = solve(k);
  System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
  //System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
  //System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
 }//main()
  
}
