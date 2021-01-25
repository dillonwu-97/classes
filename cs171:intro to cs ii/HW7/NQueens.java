/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       April 12, 2018

   Compilation:   javac NQueens.java
   Execution:     java NQueens

   For an n number of queens, this prints out all possible combinations that the queens
   can be placed on an n x n chessboard, and not hit each other.
*/

import java.util.Stack;
public class NQueens {


  
  public static int solve(int n) {
    Stack<Integer> input = new Stack<Integer>();
    // I added this to address the unique case when n == 1.
    if (n == 1) {
      input.push(0);
      printSolution(input);
      total = 1;
      return total;
    } else {
      place(n, 0, 0, input);  
      return total;   
    }
        
  }

  // this is to keep track of the total number of times I found a solution
  public static int total;

  
  public static void place (int n, int row, int col, Stack<Integer> input) {
    
    int cTracker = 0; // this is always set at 0; it resets the column each time 
    
    // base case; it occurs when r == 0, i.e. I've backtracked all the way to the beginning 
    // and when col == n, i.e. when the column has exceeded the number of available columns
      if (row == 0 && col == n) {
        // debugging
        //System.out.println("stack size: " + input.size());
        //System.out.println("row number: " + row);
        //System.out.println("col number: " + col);
        return; // end the recursive statement
                         
      } else {
        if (finalTest(row, col, input) == 0) {
          // this is when none of the numbers collide
          input.push(col);
          // I push in the column number and move onto the next row
          place(n, row + 1, cTracker, input);
          //System.out.println("matrix after success:" + row +", " + col);
              
          // if there is a conflict and the end of the row is hit            
          // then you backtrack; this is the backtracking algorithm
          // only backtrack when there is at least one element on the stack
        } else if (input.size() !=0 && ((col == n-1 && finalTest(row, col, input)==1) || (col>= n))) {
          // only execute this statement when the column numbers are on the edge and are the same; 
          // in this case, you move back two rows
          if (col == input.peek() && col == n-1 && row >=2) {
            //System.out.println("matrix after backtrack method:" + row +", " + col);
            //System.out.println("trigger 1; peek: " + input.peek());
            input.pop();
            //System.out.println("trigger 1 middle; peek: " + input.peek());
            //System.out.println("trigger 1 middle; size: " + input.size());
            place(n, row - 2, input.pop() + 1, input);
            //System.out.println("trigger 1; peek after: " + input.peek());
            //System.out.println("matrix after backtrack method:" + row +", " + col);
        } else {
            //System.out.println("matrix after backtrack method:" + row +", " + col);
            //System.out.println("trigger 2; peek: " + input.peek());
            place (n, row-1, 1 + input.pop(), input);
            //System.out.println("trigger 2; peek after: " + input.peek());
            //System.out.println("matrix after backtrack method 2:" + row +", " + col);

        }
              
      } else if (row == n && col == 0) {
        // when you have exceeded the last row and your column is at 0, print the solution
        // because you have found a solution
        printSolution(input);
        total++; // adding one to the total number of solutions
        place (n, row - 1, 1 + input.pop(), input); // backtracking to find other solutions
        //System.out.println("trigger");

      } else if (finalTest(row, col, input) == 1) {
        // this is to move along a row; move along when there is a conflict 
        place(n, row, col+1, input);
        //System.out.println("matrix after column addition method:" + row +", " + col);


      }
    }
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
  
  int n = 11;
  
  // pass in parameter n from command line
  if (args.length == 1) {
    n = Integer.parseInt(args[0].trim());
    if (n < 1) {
      System.out.println("Incorrect parameter");
      System.exit(-1);
    }//if   
  }//if
  
  int number = solve(n);
  System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
 }//main()
  

}