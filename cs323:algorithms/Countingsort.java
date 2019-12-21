import java.util.Arrays;
// Dillon Wu
// Problem #1: Counting Sort
/* Problem question: In a language of your choosing, write code for countingsort. 
You may not use any built-in sorting functions. Graph the result of 1000 runs of sorting
arrays with 128, 1024, 4096, and 16384 randomly assigned integers in the range [0 . . . 100].
Attach printed, commented code along with the graph
*/
public class Countingsort {
	
	public static void main (String[] args) {

		/*
		int[] test2 = {4, 3, 2, 4};
		countingSort(test2);
		for (int i = 0; i < test2.length; i++) {
			System.out.println(test2[i]);
		}
		*/

		
		int[] test = new int[128];
		//int number = 100;
		/*
		for (int i = 0; i < test.length; i++) {
			test[i] = number;
			number--;
		}
		*/
		for (int i = 0; i < test.length; i++) {
			test[i] = (int) (Math.random() * 100);
		}
		/*
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}
		*/

		countingSort(test);
		System.out.println("Result");
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}

	}

	public static void countingSort (int[] orig) {
		int[] result  = new int[orig.length];
		int[] track = new int[100]; // to account for numbers from 0 to 100

		// this increments the corresponding position in the array track 
		// this part is good
		for (int i = 0; i < orig.length; i++) {
			track[ orig[i] ]++;
			//System.out.println(track [orig[i]]);
		}

		// move the counters into starting position

		for (int i = 1; i < 100; i++) {
				track[ i ] = track [i-1] + track [i];
		}

		// putting the values into the result array
		for (int i = 0; i < orig.length; i++) {
			System.out.println("orig: " + orig[i]);
			System.out.println("track: " + track [orig[i]]);
			// start at the position in track, and keep going down until you
			// hit a "base", i.e. the position of the next lowest value
			result[ track [orig[i]] - 1 ] = orig[i];
			track[ orig[i] ]--;
		}

		// moving the result array to the original array
		for (int i = 0; i < orig.length; i++) {
			orig[i] = result[i];
		}

	}
}