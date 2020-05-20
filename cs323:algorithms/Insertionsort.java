// Dillon Wu
// Problem #1: Insertionsort
/* Problem question: In a language of your choosing, write code for insertion sort. 
You may not use any built-in sorting functions. Graph the result of 1000 runs of sorting
arrays with 128, 1024, 4096, and 16384 randomly assigned integers in the range [0 . . . 100].
Attach printed, commented code along with the graph
*/

public class Insertionsort {
	
	public static void main (String[] args) {	
		int[] test = new int[128];

		for (int i = 0; i < test.length; i++) {
			test[i] = (int) (Math.random() * 100);
		}
		insertSort(test);
		System.out.println("Result");
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
		
		}
	}

	public static void insertSort(int[] array) {
		int loc = 0; // tracks the location of the end swap position

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
}