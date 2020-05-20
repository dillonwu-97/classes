// Dillon Wu
// Problem #1: Mergesort
/* Problem question: In a language of your choosing, write code for mergesort. 
You may not use any built-in sorting functions. Graph the result of 1000 runs of sorting
arrays with 128, 1024, 4096, and 16384 randomly assigned integers in the range [0 . . . 100].
Attach printed, commented code along with the graph
*/
import java.util.Arrays;

public class Mergesort {
	
	public static void main (String args[]) {
		int[] test = {4, 2, 3, 10, 100, 2, 3, 2, 1};
		mergeSort(test);

		int[] test2 = {5, 4};

		System.out.println("Printing results");
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}
	}

	public static void mergeSort (int[] array) {
		// creating two arrays that split the original array in half
		int[] first = Arrays.copyOfRange(array, 0, array.length/2);
		int[] second = Arrays.copyOfRange(array, array.length/2, array.length);

		// recursive algorithm
		if (array.length > 1) {
			mergeSort(first);
			mergeSort(second);
			merge(array, first, second);
		} 
	}

	// the merge function: it takes the original array, and the two half arrays
	public static void merge (int[] original ,int[] x, int[] y) {
		// the pointers keep track of the index of the respective arrays
		int xTrack = 0;
		int yTrack = 0;
		int arrayTrack = 0;

		// while we have not reached the end of the original array 
		while (original.length != arrayTrack) {
			
			// this basically says if the tracker has passed the length of array x,
			// then you move the remaining elements in y into the original array
			if (xTrack >= x.length) {
				original[arrayTrack] = y[yTrack];
				arrayTrack++;
				yTrack++;

			// same as above except x and y are reversed
			} else if (yTrack >= y.length) {
				original[arrayTrack] = x[xTrack];
				arrayTrack++;
				xTrack++;

			// this bottom functions ask which value in array x and y
			// are smaller. The smaller element gets put into the original array
			} else if (x[xTrack] < y[yTrack]) {
				original[arrayTrack] = x[xTrack];
				arrayTrack++;
				xTrack++;
			} else if (y[yTrack] <= x[xTrack]) {
				original[arrayTrack] = y[yTrack];
				arrayTrack++;
				yTrack++;
			}
		}
	}

}