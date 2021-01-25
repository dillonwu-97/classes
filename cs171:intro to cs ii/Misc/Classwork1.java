public class Classwork1 {
	
	public static void main (String[] args) {
		int[] first = {3,6,23,4,11,91};
		int[] first1 = {3,4,5,6,7,8};
		int[] first2 = {9,8,7,6,6,4};
		int[] second = new int[6];
		int[] second1 = new int[6];
		int[] second2 = new int[6];

		sort(first,second,6);
		sort(first1,second1,6);
		sort(first2,second2,6);
		System.out.println(arrayToString(second));
		System.out.println(arrayToString(second1));
		System.out.println(arrayToString(second2));

	}

	public static void sort(int[] a, int[] b, int n) {
		int count = 0;
		int num = 0; // storing number
		int pos = 0; //storing position
		for (int i = 0; i < n; i++) {
			num = a[i];
			pos = i;
			for (int j = 0; j < n; j++) {
				//System.out.println(num);
				if (num == 0) {
					num = a[j];
					pos = j;
				}
				if (a[j] != 0 && num > a[j]) { // a[i] needs to be less than everything else in j
					num = a[j];
					pos = j;
				} // compares i to j, so if at i smaller, doesn't matter if it gets replaced
					// must go through all numbers again and again, replace 0 each time so if interacts with 0, then no go
			}
			//a[j] = 0
			a[pos] = 0;
			b[count] = num;
			count++;
			//System.out.println(arrayToString(a));
			//System.out.println(num);

		}
	}
	
	/*
	public static void sort(int[] a, int[] b, int n) {
		int count = 0;
		for (int i = 0; i < n; i++) {
			b[count] = a[i];
			if (a[i] < b[count]) { // a[i] needs to be less than everything else in j
				b[count] = a[i];
			}
			count++;
		}
	}
	*/

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