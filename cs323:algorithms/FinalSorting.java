import java.text.DecimalFormat;
import java.util.Arrays;

public class FinalSorting {
	
	//-----------------------------------------------------------------------
	//---------- Below is an implementation of InsertionSort ----------
	//-----------------------------------------------------------------------

	
	public static void heapSortIt(int[] array) {
		int heapSize = array.length-1;
		buildMaxHeap(array);
		for (int i = heapSize; i > 0; i--) {
			swap(array, 0, i);
			maxHeapify(array, 1, i);
		}
	}

	public static void buildMaxHeap (int[] array) {
		int heapSize = array.length;
		for (int i = heapSize/2; i > 0; i--) {
			maxHeapify (array, i, heapSize);
		}
	}

	// n = start location
	// length = range of the heap in the array
	public static void maxHeapify (int[] array, int n, int length) {
		int largest = n-1; // starting from index 0
		int heapSize = length - 1;
		int current = largest;
		while (2 * largest < heapSize) {
			int l = 2 * current +1; // left node index
			int r = 2 * current +2; // right node index
			boolean flag = false; // this means a right node exists
			if (r > heapSize) {
				flag = true; // this means a right node doesn't exist
			}
			if (flag == false && array[current] > array[r] && array[current] > array[l]) {
				break;
			}
			if (l <= heapSize && array[l] > array[current]) {
				largest = l; 
			}
			if (r <= heapSize && array[r] > array[largest]) {
				largest = r;
			}
			swap(array, largest, current);
			if (current == largest) {
				break;
			}
			current = largest;
		}
	}

	public static void insertSort(int[] array) {
		int loc = 1; // tracks the location of the end swap position

		for (int j = 0; j < array.length-1; j++) {
			for (int i = loc; i > 0; i--) {
				if (array[i] < array[i-1]) {
					swap (array, i, i-1);
				}
			}
			loc++;
		}
	}

	// this line of code swaps the position of a and b
	public static void swap (int[] array, int a, int b) {
		int temp;
		temp = array[b];
		array[b] = array[a];
		array[a] = temp;
	}

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of MergeSort ----------
	//-----------------------------------------------------------------------
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

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of QuickSort ----------
	//-----------------------------------------------------------------------

	public static void quickSort (int[] array, int start, int end) {

		// partition index
		int part = end;
		int track = start;

		if (start < end) {
			for (int i = 0; i < part; i++) {
				if (array[track] < array[i]) {
					swap(array, track, i);
				}
			}
			swap(array, track, part);
			part = track;
			quickSort(array, start, part-1);
			quickSort(array, part+1, end);
		}


	}

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of CountingSort ----------
	//-----------------------------------------------------------------------

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
			//System.out.println("orig: " + orig[i]);
			//System.out.println("track: " + track [orig[i]]);
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

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of RadixSort ----------
	//-----------------------------------------------------------------------

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

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of keeping track of time ----------
	//-----------------------------------------------------------------------	


	private static void startTimer() { 
		timestamp = System.nanoTime();
	}//startTimer()
	
	private static double endTimer() {
		return (System.nanoTime() - timestamp)/1000000.0;
	}//endTimer()

	private static long timestamp;

	private static int[] randArray(int n) {
		int[] rand = new int[n];
		for(int i=0; i<n; i++)
			rand[i] = (int) (Math.random() * 100);
		return rand;
	}//randArray()

	public static void main(String[] args) {
	
		// run experiments
		final int INSERTION = 0, MERGE = 1, QUICK = 2, COUNTING = 3, RADIX = 4, HEAP = 5;
		int[] algorithms = {INSERTION, MERGE, QUICK, COUNTING, RADIX, HEAP};
		
		// max defines the maximum size of the array to be tested, which is 2^max
		// runs defines the number of rounds to be performed per test, in order to get an average running time.
		int max = 14, runs = 6;
		double[][] stats = new double[algorithms.length][max];
		for(int i=0; i<algorithms.length; i++) {             //loop through each sorting algorithm
			switch(i) {
				case INSERTION: System.out.print("Running Insertion Sort ..."); break;
				case MERGE: System.out.print("Running Merge Sort ..."); break;
				case QUICK: System.out.print("Running Quick Sort ..."); break;
				case COUNTING: System.out.print("Running Counting Sort ..."); break;
				case RADIX: System.out.print("Running Radix Sort ..."); break;
				case HEAP: System.out.print("Running Heap Sort ..."); break;
			}//switch
			for(int j=0; j<max; j++) {        //loop through each array size 
				double avg = 0;
				for(int k=0; k<runs; k++) {    // loop through each run
					int[] a = randArray((int) Math.pow(2, j+1));
					startTimer();
					switch(i) {
						case INSERTION: insertSort(a); break;
						case MERGE: mergeSort(a); break;
						case QUICK: quickSort(a, 0, a.length-1); break;
						case COUNTING: countingSort(a); break;
						case RADIX: radixSort(a); break;
						case HEAP: heapSortIt(a); break;
					}//switch
					avg += endTimer();
				}//for
				avg /= runs;
				stats[i][j] = avg;
			}//for
			System.out.println("done.");
		}//for
		
		DecimalFormat format = new DecimalFormat("0.0000");
		System.out.println();
		System.out.println("Average running time:");
		System.out.println("N\t Insertion Sort\t Merge Sort\t Quick Sort\t Counting Sort \tRadix Sort \tHeap Sort");
		for(int i=0; i<stats[0].length; i++) {
			System.out.print((int) Math.pow(2, i+1) + "\t  ");
			for(int j=0; j<stats.length; j++) {
				System.out.print(format.format(stats[j][i]) + "\t  ");
			}//for
			System.out.println();
		}//for
	}//main()

}