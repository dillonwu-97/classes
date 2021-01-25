/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       March 23, 2018

   Compilation:   javac Josephus.java
   Execution:     java Josephus

   This program solves the Josephus problem and its many variants.
*/


import java.io.*;
import java.util.*;

public class Josephus{
	
        public static void main(String[] args){
                /* 
                 * Sample testing code
                 * Feel free to edit for additional tests of your CircularList 
                 * imlementation as well as the solve method
                 */

                CircularList cl = new CircularList();
                for(int i=0; i<7; i++) {
                        cl.insert(new ListElem(i)); // number = 7
                }

                // Testing out my CircularList code
                //System.out.println("Size: " + cl.getSize());
                //System.out.println("Testing printlist");
                //cl.printList();
                //System.out.println("Finish testing printlist");
                //System.out.println("Remove: " + cl.remove());
                //System.out.println("Size after remove: " + cl.getSize());

                /*
                System.out.println("Rotating once");
                cl.rotate();
                cl.printList(); // starting value should be 8 --> 0 --> ending should be 9
                System.out.println("Rotating again");
                cl.rotate();
                cl.printList(); // starting value should be 7 --> 0 --> ending should be 9, 8
                */

                System.out.println(solve(cl,3)); // remove every 3rd person 

                CircularList cl2 = new CircularList();
                for(int i=1; i<2; i++) {
                        cl2.insert(new ListElem(i));
                }
                System.out.println(solve(cl2,1));

                CircularList cl3 = new CircularList();
                for(int i=50; i>0; i--) {
                        cl3.insert(new ListElem(i)); // remove every 5th person of 51 people
                }
                System.out.println(solve(cl3,5));


        }

	public static int solve(CircularList cl, int m){
		// while size is not 1, keep rotating
		int result = 0;
                while (cl.getSize() != 1) {
                        for (int i = 0; i < m-1; i++) {
                                cl.rotate();
                        }
                        System.out.println("removing " + cl.remove());
                        //cl.remove();
                }
                result = cl.remove();
                return result;
	}



}
