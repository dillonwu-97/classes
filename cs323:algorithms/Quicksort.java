import java.util.Arrays;
// Dillon Wu
// Problem #1: Quick Sort
/* Problem question: In a language of your choosing, write code for quick sort. 
You may not use any built-in sorting functions. Graph the result of 1000 runs of sorting
arrays with 128, 1024, 4096, and 16384 randomly assigned integers in the range [0 . . . 100].
Attach printed, commented code along with the graph
*/

public class Quicksort {
	
	public static void main (String[] args) {
		
		int[] test = new int[100];
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
		
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}
		System.out.println("Result");

		quickSort(test, 0, test.length-1);
		//System.out.println("Result");
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		}

		int[] test2 = {5, 4, 3,2,1};
		System.out.println(partition(test2, 0, 4));
		for (int i = 0; i < test2.length; i++) {
			System.out.println(test2[i]);
		}

		quickSort(test2, 0, 4);
		for (int i = 0; i < test2.length; i++) {
			System.out.println(test2[i]);
		}


	}

	public static void quickSort (int[] array, int low, int high) {
		if (low < high) {

	        int pi = partition(array, low, high);

		    quickSort(array, low, pi - 1);  // Before pi
		    quickSort(array, pi + 1, high); // After pi
	    }
	}

	public static int partition(int[] array, int low, int high) {
        int pivot = low;
        int i = high+1;
        for (int j = high; j > low; j--) {
            if (array[j] > array[pivot]) {
                i--;
                swap (array, i, j);
            }
        }
        i--;
        swap (array, pivot, i);
        return i;   
    }

    public static void swap (int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
/*
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

	public static void swap (int[] array, int a, int b) {
		int temp;
		temp = array[b];
		array[b] = array[a];
		array[a] = temp;
	}
	*/
}