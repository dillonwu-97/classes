public class MySort {
	
	public static void main (String[] args) {
		double[] numbers = {2,2,2,4,5,6,7};
		double[] numbersRan = {2,4,6,7,5,2,2};
		mySort(numbersRan);
		arrayToString(numbersRan);

	}

	public static void mySort(double[] x) {
		for (int i = 0; i < x.length + 1; i++) {
			if (x[i] < x[i+1]) {
				swap(x, i, i+1)
			} else {
			}
		}
	}

	public static void swap(double[] x, double first, double second) {
		double temp = new double[2];
		temp[0] = second;
		temp[1] = first;
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
}