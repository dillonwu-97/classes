// Team name: Team Six
// Member names: Dillon Wu, Katherine Walton, Yuxin He, Sichen Zhu, Haolin Ya
// Run through the program the number of times there are cards, 

public class MySort {
	
	public static void main (String[] args) {
		double[] numbers = {2,2,2,4,5,6,7};
		double[] numbersRan = {2,6,4,7,5,2,2};
		mySort(numbersRan);
		double[] numbersRan2 = {2,6,4,7,5,2,2,10,9,11,13};
		mySort(numbersRan2);
		System.out.println(arrayToString(numbersRan));
		System.out.println(arrayToString(numbersRan2));

	}

	public static void mySort(double[] x) {
		double[] temp = new double[2];
		for (int j = 0; j < x.length; j++) {
			for (int i = 0; i < x.length-1; i++) {
				if (x[i] > x[i+1]) {
					temp[0] = x[i+1];
					temp[1] = x[i];
					x[i] = temp[0];
					x[i+1] = temp[1];
				} 
			}	
		}
	}

	public static String arrayToString(double[] x) {
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