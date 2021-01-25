/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       April 5, 2018

   Compilation:   javac Sorting.java
   Execution:     java Sorting.java

   This program tests the running speed of different sorting algorithms. BubbleSort and the
   Nonrecursive MergeSort algorithsm are newly coded. 
*/

import java.text.DecimalFormat;
import java.util.Arrays;

public class Sorting {
	
	// This is the code that does bubble search
	public static void BubbleSort(long[] a) {
		long temp = 0;
		int N = a.length;
		for (int i = 0; i < N-1; i++) {
			for (int j = 0; j < N-1; j++) {
				if (a[j] > a[j+1]) {
					temp = a[j+1];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}
		}

		
	}

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of nonrecursive MergeSort ----------
	//-----------------------------------------------------------------------
	
	public static void MergeSortNonRec(long[] a) {
		int N = a.length; // length designated by N
		long[] orig = a; // a copy of the original array
		long[] aux = new long[N]; // this is an auxillary array

		for (int i = 1; i < N; i = i*2) { // breaks down the array into smaller sections
			for (int j = 0; j<N; j = j + i*2) {
				mergeM(orig, aux, j, j + i, j + i*2);
			}
			// this creates a temporary array to store the values of the original array
			// the original array now contains the elements in the auxillary array
			// the auxillary array contains the elements in the original array
			long[] temp = orig;
			orig = aux;
			aux = temp;
		}
		
		// this is to copy the values from the original array into the [truly]
		// original array / result array
		for (int k = 0; k < N; k++) {
			a[k] = orig[k];
		}
	}

	// this merge function is exclusive to mergesortnonrec
	// it organizes the arrays that are iterated
	private static void mergeM(long[] orig, long[] aux, int lo, int mid, int hi) {

		if (mid > orig.length) {
			mid = orig.length;
		} // this is in case mid is higher than the original length
		if (hi > orig.length) {
			hi = orig.length;
		} // this is in case hi is higher than the original length;
		if (lo > orig.length) {
			lo = orig.length;
		} // this is in case lo is higher than the original length;

		int i = lo; // store the lowest value in i
		int j = mid; // store the lowest value in j; these will change

		// storing the new values in the auxillary array
		for (int k = lo; k < hi; k++) {
			
			// finished with one of the sets, but the rest of the numbers in
			if (j == hi) {
				aux[k] = orig[i];
				i++;
			} else if (i == mid) {
				aux[k] = orig[j];
				j++;

			// this is to compare the smallest element in the two sorted sets
			} else if (orig[j] < orig[i]) {
				aux[k] = orig[j];
				j++;
			} else {
				// else nothing changes and move onto the next element
				aux[k] = orig[i];
				i++;
			}
		}
	}
	
	//-------------------------------------------------------------
	//---------- Below is an implementation of Selection Sort -----
	//-------------------------------------------------------------		
	public static void SelectionSort(long[] a) {
		int N = a.length;
	    for (int i = 0; i < N; i++) {
	    	int min = i;
	        for (int j = i+1; j < N; j++) {
	        	if (a[j] < a[min]) min = j;
	        }
	        exch(a, i, min);
	    }
	}//SelectionSort()
	
	
	//-----------------------------------------------------------------------
	//---------- Below is an implementation of Insertion Sort ----------
	//-----------------------------------------------------------------------
	public static void InsertionSort(long[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j, j-1);
            }
        }
	}//InsertionSort()

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of recursive MergeSort ----------
	//-----------------------------------------------------------------------
	
    private static void merge(long[] a, long[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (aux[j] < aux[i]) 	a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }
    }

    
    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(long[] a, long[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void MergeSort(long[] a) {
        long[] aux = new long[a.length];
        sort(a, aux, 0, a.length-1);
    }
    
	//------------------------------------------------------
	//---------- below are several helper methods ----------
	//------------------------------------------------------
	
	// This tests whether your sorted result is correct by comparing it to reference result
	public static boolean testSort(long[] a) {
		long[] a2 = new long[a.length];
		System.arraycopy(a, 0, a2, 0, a.length);
		Arrays.sort(a);
		for(int i = 0; i < a.length; i++)
			if(a2[i] != a[i]) 
				return false;
		return true;
	}//testSort()
	
	
	// This creates an array with n randomly generated elements between (0, n*10]
	private static long[] randArray(int n) {
		long[] rand = new long[n];
		for(int i=0; i<n; i++)
			rand[i] = (int) (Math.random() * n * 10);
		return rand;
	}//randArray()
	
	private static void startTimer() { 
		timestamp = System.nanoTime();
	}//startTimer()
	
	private static double endTimer() {
		return (System.nanoTime() - timestamp)/1000000.0;
	}//endTimer()
	        
    // exchange a[i] and a[j]
    private static void exch(long[] a, int i, int j) {
        long swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
	
	private static long timestamp;
	
	//---------------------------------------------
	//---------- This is the main method ----------
	//---------------------------------------------		
	public static void main(String[] args) {
	
		// run experiments
		final int BUBBLE = 0, SELECT = 1, INSERT = 2, MERGEREC = 3, MERGENONREC = 4;
		int[] algorithms = {BUBBLE, SELECT, INSERT, MERGEREC, MERGENONREC};
		
		// max defines the maximum size of the array to be tested, which is 2^max
		// runs defines the number of rounds to be performed per test, in order to get an average running time.
		int max = 14, runs = 5;
		double[][] stats = new double[algorithms.length][max];
		for(int i=0; i<algorithms.length; i++) {             //loop through each sorting algorithm
			switch(i) {
				case BUBBLE: System.out.print("Running Bubble Sort ..."); break;
				case SELECT: System.out.print("Running Selection Sort ..."); break;
				case INSERT: System.out.print("Running Insertion Sort ..."); break;
				case MERGEREC: System.out.print("Running MergeSort Recursive ..."); break;
				case MERGENONREC: System.out.print("Running MergeSort Non Recursive ..."); break;
			}//switch
			for(int j=0; j<max; j++) {        //loop through each array size 
				double avg = 0;
				for(int k=0; k<runs; k++) {    // loop through each run
					long[] a = randArray((int) Math.pow(2, j+1));
					startTimer();
					switch(i) {
						case BUBBLE: BubbleSort(a); break;
						case SELECT: SelectionSort(a); break;
						case INSERT: InsertionSort(a); break;
						case MERGEREC: MergeSort(a); break;
						case MERGENONREC: MergeSortNonRec(a); break;
					}//switch
					avg += endTimer();
					if (testSort(a) == false)
						System.out.println("The sorting is INCORRECT!" + "(N=" + a.length + ", round=" + k + ").");
				}//for
				avg /= runs;
				stats[i][j] = avg;
			}//for
			System.out.println("done.");
		}//for
		
		DecimalFormat format = new DecimalFormat("0.0000");
		System.out.println();
		System.out.println("Average running time:");
		System.out.println("N\t Bubble Sort\t Selection Sort\t Insertion Sort\tMergeSortRec\tMergeSortNon");
		for(int i=0; i<stats[0].length; i++) {
			System.out.print((int) Math.pow(2, i+1) + "\t  ");
			for(int j=0; j<stats.length; j++) {
				System.out.print(format.format(stats[j][i]) + "\t  ");
			}//for
			System.out.println();
		}//for
	}//main()
	
}//end of class
