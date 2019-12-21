public class HeapSortHW {
	
	public static void main (String[] args) {
		
		System.out.println("Test 1: Ascending Order");
		int[] test1 = new int[20];
		for (int i = 0; i < 20; i++) {
			test1[i] = i+1;
			System.out.println(test1[i]);
		}

		System.out.println("Sorting Test 1: Ascending Order Array");
		heapSort(test1);
		for (int i = 0; i < test1.length; i++) {
			System.out.println(test1[i]);
		}

		System.out.println("Test 2: Descending Order");

		int[] test2 = new int[20];
		int j = 20;
		for (int i = 0; i < 20; i++) {
			test2[i] = j;
			j--;
			System.out.println(test2[i]);
		}

		System.out.println("Sorting Test 2: Descending Order Array");
		heapSort(test2);
		for (int i = 0; i < test2.length; i++) {
			System.out.println(test2[i]);
		}

		System.out.println("Test 3: Random Order");

		int[] test3 = new int[20];
		for (int i = 0; i<20;i++) {
			test3[i] = (int)(Math.random() * 20);
			System.out.println(test3[i]);
		}
		System.out.println("Sorting Test 3: Random Order Array");
		heapSort(test3);
		for (int i = 0; i < test3.length; i++) {
			System.out.println(test3[i]);
		}

	}

	public static void heapSort(int[] array) {
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

	public static void swap (int[] array, int a, int b) {
		int temp;
		temp = array[b];
		array[b] = array[a];
		array[a] = temp;
	}
}