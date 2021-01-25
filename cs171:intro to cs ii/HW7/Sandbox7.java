import java.util.Stack;
public class Sandbox7 {


	
	public static int solve(int n) {
    	Stack<Integer> input = new Stack<Integer>();
		if (n == 1) {
        	input.push(0);
        	printSolution(input);
        	total = 1;
        	return total;
      	} else {
        	place(n, 0, 0, input);  
        	return total; 	
      	}
        
	}//solve()

	public static int total;

	// row = starting row, which will be 0
	public static void place (int n, int row, int col, Stack<Integer> input) {
		
		int cTracker = 0; // this is always set at 0
		
		// base case
		if (row <= 0 && col >= n) {
			//System.out.println("stack size: " + input.size());
			//System.out.println("row number: " + row);
			//System.out.println("col number: " + col);
			return;
			
			// now, I need to backtrack and find the other solutions

            //System.out.println("peek" + input.peek());
                       
		} else {
			if (col >= 0 && finalTest(row, col, input) == 0) {
           		// to prevent the number from exceeding a wall
           		input.push(col);

				place(n, row + 1, cTracker, input);
        		//System.out.println("matrix after success:" + row +", " + col);

				// placement is successful so you reset
              
              // if there is a conflict and the end of the row is hit            
			  // then you backtrack; this is the backtracking algorithm
        	} else if (((col == n-1 && finalTest(row, col, input)==1) || (col>= n))) {
            	// this if statement occurs when the column number at the current position
            	// and the one before it both are equal to n-1.
            	
            		//System.out.println("matrix after backtrack method:" + row +", " + col);
            		//System.out.println("trigger 2; peek: " + input.peek());
            		place (n, row-1, 1 + input.pop(), input);
            		//System.out.println("trigger 2; peek after: " + input.peek());
            		//System.out.println("matrix after backtrack method 2:" + row +", " + col);

            	
            	
            } else if (row == n && col == 0) {
				//System.out.println("What I am printing / final line: PRE " + row + "," + col);

				printSolution(input);
				total++;
				place (n, row - 1, 1 + input.pop(), input);
				//System.out.println("What I am printing / final line: " + row + "," + col);

            } else if (finalTest(row, col, input) == 1) {
				//printSolution(input);
        		
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
  
  int n = 4;
  
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