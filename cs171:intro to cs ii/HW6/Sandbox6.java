public class Sandbox6 {
	
	public static void main (String[] args) {
		long[] test = {2, 1};
		//BubbleSort(test);

		long[] test2 = {189 , 123, 213, 214,23, 12, 1, 2, 3};
		iterativeMergesort(test2);
		for (int i = 0; i < test2.length; i++) {
			System.out.println(test2[i]);
		}

		System.out.println("TEst 4");
		long[] test4 = {1, 102, 19, 113, 121};
		iterativeMergesort(test4);
		for (int i = 0; i < test4.length; i++) {
			System.out.println(test4[i]);
		}
	}
	
	
 	public static void iterativeMergesort(long[] a) {
		int N = a.length; // length designated by N
		long[] orig = a; // a copy of the original array
		long[] aux = new long[N]; // this is an auxillary array
		for (int i = 1; i < N; i = i*2) { // breaks down the array into smaller sections
			for (int j = 0; j<N; j = j + 2*i) {
				mergeM(orig, aux, j, j + i, j + 2 * i);
			}
			// this creates a temporary array to store the values of the original array
			// the original array now contains the elements in the auxillary array
			// the auxillary array contains the elements in the original array
			long[] temp = orig;
			orig = aux;
			aux = temp;
		}
		if (a != orig)
		for (int k = 0; k < N; k++)
			a[k] = orig[k];
	}    

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
			if (j == hi) {
				aux[k] = orig[i];
				i++;
			} else if (i == mid) {
				aux[k] = orig[j];
				j++;
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



}