import java.util.Arrays;
// Dillon Wu
// Problem #1: Radix Sort
/* Problem question: In a language of your choosing, write code for radix sort. 
You may not use any built-in sorting functions. Graph the result of 1000 runs of sorting
arrays with 128, 1024, 4096, and 16384 randomly assigned integers in the range [0 . . . 100].
Attach printed, commented code along with the graph
*/

public class Radixsort {
	


	public static void main (String[] args) {
		int[] test = new int[128];
		for (int i = 0; i < test.length; i++) {
			test[i] = (int) (Math.random() * 100);
		}
		
		radixSort(test);
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}


	}

	public static void radixSort (int[] array) {

		countingSortMod(array, 10, "modulo");
		countingSortMod(array, 10, "division");
	}

	// modified version of counting sort that takes a string "modulo", or "division", and multiplies the
	// original number in the array by that factor
	// division is for finding tens place and greater; modulo is for finding ones
	public static void countingSortMod (int[] orig, int factor, String task) {
		int[] result  = new int[orig.length];
		int[] track = new int[10]; // to account for numbers from 0 to 100
		int [] track2 = new int[10]; // the second track before moving them into result

		// this increments the corresponding position in the array track 
		// this part is good

		if (task.equals("modulo")) {
			for (int i = 0; i < orig.length; i++) {
				track[ orig[i] % factor]++;
			//System.out.println(track [orig[i]]);
			}
		} else if (task.equals("division")) {
			for (int i = 0; i < orig.length; i++) {
				track[ orig[i] / factor]++;
			//System.out.println(track [orig[i]]);
			}
		}

		// move the counters into starting position

		for (int i = 1; i < 10; i++) {
				track2[i] = track[i-1] + track2[i-1];
		}

		// putting the values into the result array
		for (int i = 0; i < orig.length; i++) {
			//System.out.println("orig: " + orig[i]);
			//System.out.println("track: " + track [orig[i]]);
			// start at the position in track, and keep going down until you
			// hit a "base", i.e. the position of the next lowest value
			
			if (task.equals("modulo")) {
				result[ track2 [orig[i] % 10]] = orig[i];
				track2[ orig[i]%10 ]++;
			} else if (task.equals("division")) {
				result[ track2 [orig[i] / 10]] = orig[i];
				track2[ orig[i]/10 ]++;
			}
		}

		// moving the result array to the original array
		for (int i = 0; i < orig.length; i++) {
			orig[i] = result[i];
		}
	}
}