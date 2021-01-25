/*
	Author: 		Dillon Wu
	Written: 		October 20, 2017

	Compilation: 	javac Kagebunshin.java
	Execution: 		java Kagebunshin

	This deletes all repeated integers in an array and returns the originals 
	(in no specific order).
*/

public class Kagebunshin {

	public static int[] withoutDuplicates (int[] x) {
		

		// This is a new array.
		int[] draft = new int[x.length];
		int update = 0; // This is to determine the final number of values in the final array.
		// This is for a situation where there is a 0 in the original array.
		for (int i = 0; i < x.length; i ++) {
			if (x[i] == 0) {
				update = 1;
			}
		}
		int track = 0;
		for (int i = 0; i < x.length; i++) {
			for (int j = draft.length - 1; j >= 0; j--) {
				if (x[i] == draft[j]) {
					track++; // this creates a red flag if there are repeats
				}
			}
			if (track == 0) {
					draft[update] = x[i]; // if the red flag isn't raised, then the draft array is updated.
					update++; 
			}
			track = 0; // the flag is reset.
		}

		// This transfers the unique values in the draft to the final copy.
		int[] result = new int[update];
		int pos = 0;
		for (int i = 0; i < draft.length; i++) {
			if (draft[i] != 0) {
				result[pos] = draft[i];
				pos++; 
			}
		}

		return result;
		
		
	}

	public static String arrayToString(int[] x) {
    	String result = "{";
    	for (int i = 0; i < x.length - 1; i++) { // all the elements except the last one
     		result += x[i] + ", ";
    	}
    	if (x.length > 0) { // protection from index out of bound error
      		result += x[x.length - 1]; // last element
   		}
    	result += "}";
    	return result;
  	}

	public static void main (String[] args) {
		
		// Declaring and testing the arrays.
		int[] x1 = new int[3]; // This array has all unique values.
		x1[0] = 1;
		x1[1] = 2;
		x1[2] = 3;

		System.out.println(arrayToString(withoutDuplicates(x1)));

		int[] x2 = new int[7]; // This array has repeated values.
		x2[0] = 1;
		x2[1] = 2;
		x2[2] = 1;
		x2[3] = 1;
		x2[4] = 3;
		x2[5] = 2;
		x2[6] = 3;

		System.out.println(arrayToString(withoutDuplicates(x2)));

		// To test when there are 0's in the test case
		int[] x3 = {1, 2, 0, 0, 3, 4, 4};
		System.out.println(arrayToString(withoutDuplicates(x3))); // answer is {1,2,3,4,0}

		int[] x4 = {};
		System.out.println(arrayToString(withoutDuplicates(x4))); // when there is nothing in the array

		int[] x5 = {-1, 0, 1, 1, 2, 1}; // when there are negative values.
		System.out.println(arrayToString(withoutDuplicates(x5)));

	}
}