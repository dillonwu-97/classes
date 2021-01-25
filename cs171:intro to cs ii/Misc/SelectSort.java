public class SelectSort {
	
	public static void main (String[] args) {
		int[] test = {5,8,1};
		swap(test, 2, 3);
		for (int i = 0; i < test.length; i++) {
			System.out.println(array[i]);
		}
	}

	/*
	public static void selectSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			while (a[i] < a[i-1]) {
				swap (array, a[i], a[i-1]);
			}
		}
	}
	*/

	// this line of code swaps the position of a and b
	public static void swap (int[] array, int a, int b) {
		int temp;
		temp = array[b];
		array[b] = array[a];
		array[a] = temp;
	}
}