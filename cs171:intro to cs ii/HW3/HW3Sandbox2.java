import java.util.*;
public class HW3Sandbox2 {

    public static void main(String args[]) {

        // creating stack
        Stack<Integer> st = new Stack<Integer>();

        // populating stack
        st.push(1);
        st.push(3);
        //st.push(0);

        //System.out.println(st.size());
        Stack<Integer> ab = new Stack<Integer>();
        //clone(st, ab);
        //System.out.println(st.size());
        //System.out.println(ab.size());
        //System.out.println(ab.pop());
        //System.out.println(ab.pop());
        //System.out.println(ab.pop());


        
        //System.out.println(check(1,st));
        //System.out.println(check(2,st));
        //System.out.println(check(3,st));
        //System.out.println(check(2,st));
        //System.out.println(check(0,st));
        //System.out.println(check(2,st));

    
        //System.out.println(finalTest(2,0,st));
        //System.out.println(finalTest(2,1,st));
        //System.out.println(finalTest(2,0,st));
        //System.out.println(finalTest(3,2,st));

/*
        System.out.println(columnTest(1,st));
        System.out.println(columnTest(3,st));
        System.out.println(columnTest(0,st));
        System.out.println(columnTest(4,st));
  */
        //placement(11, ab); 
        System.out.println(finalPlacement(11, ab));
        //placement(2, ab);
        //placement(3, ab);
        //placement(4, ab);
        //placement(5, ab);
        //placement(6, ab); 
        //placement(7, ab);
        //placement(8, ab);
        //placement(9, ab);
        //placement(10, ab); 
        

        /*
        System.out.println(check(1, ab));
        System.out.println(columnTest(1, ab));
        System.out.println(diagTest(1, 1, ab)); // should be false
        System.out.println(columnTest(2, ab));
        System.out.println(diagTest(2, 2, ab)); // should be false 
        System.out.println(diagTest(2, 1, ab)); // should be true
        */
        //System.out.println(ab.size());


        
        

        
    }

    // 1 throws a null exception so this is just to address that problem
    public static int finalPlacement (int number, Stack<Integer> input) {
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

    // iterator?
    // number represents the number of queens 
    // cTracker tracks the column, 0 is the first column
    public static int placement (int number, Stack<Integer> input) {
      // tracker keeps track of which column position we are in
      int rTracker = 0; // tracks the column position
      int cTracker = 0;
      int totalPrints = 0;
      //backtrack counter?      

      // if the stack is empty, put a number in
      // tracker needs to represent the row 
      while (rTracker <= number) {
        // j represents column
        // cTracker represents the column position; if I need to backtrack, then I start from the columnposition
        // it was at +1 
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
        if (cTracker == number && rTracker == 0) {
          break;
        }

        // extremely useful for determining where the trackers are
        //System.out.println("main row number:" + rTracker);
        //System.out.println("main col number:" + cTracker);
        while (cTracker < number) {
          
            
            if (finalTest(rTracker, cTracker, input) == 0) {
              input.push(cTracker);
              rTracker++;
              cTracker=0; // resets the column
              //System.out.println("row number after okay:" + rTracker);
              //System.out.println("col number after okay:" + cTracker);
              break;            
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

              
              if (cTracker ==number) {
                cTracker = 1 + input.peek();
                input.pop();
                rTracker--;
                //System.out.println("row number if hits a wall:" + rTracker);
                //System.out.println("col number if hits a wall:" + cTracker);
                
              }
              break;
              
            }
            if (finalTest(rTracker, cTracker, input) == 1) {
              cTracker++;
              //System.out.println("col number after test fails:" + cTracker);
            
            }
          }


        }


        
        return totalPrints;

      }

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
        // I have to put in i - 1 because I'm looking at the indices, which does not have a 0
          //if (column - check(i,input) == row - (i-1)) {
          // one possible alternative is to do 
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

}
