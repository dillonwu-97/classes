/*
	Author: 		Dillon Wu
	Written: 		October 15, 2017

	Compilation: 	javac Averages.java
	Execution: 		java Averages

	This takes a matrix / 2D array and calculates the test scores of students
*/

public class Averages {
	
	public static double[] studentAverages (int [][] x) {
		
		// Initializing the final array
		double[] result = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = 0;
		}

		for (int i = 0; i < x.length; i++) { // rows
			for (int j = 0; j < x[0].length; j++) { // columns; x[0].length gives column length
				result[i] = result[i] + x[i][j]; // adding all the values in the row
			}
			result[i] = result[i] / x[0].length; // dividing the sum by the number of values in the row
		}
		return result;
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

	public static void main (String[] args) {

		// HW example
		int[][] x1 = new int[2][3];
		x1[0][0] = 50;
		x1[0][1] = 100;
		x1[0][2] = 0;
		x1[1][0] = 100;
		x1[1][1] = 100;
		x1[1][2] = 80;

		int[][] x2 = new int[3][4];
		x2[0][0] = 50;
		x2[0][1] = 100;
		x2[0][2] = 0;
		x2[0][3] = 50;

		x2[1][0] = 100;
		x2[1][1] = 100;
		x2[1][2] = 100;
		x2[1][3] = 100;
		
		x2[2][0] = 0;
		x2[2][1] = 100;
		x2[2][2] = 100;
		x2[2][3] = 80;

		int[][] x3 = new int[0][0];

		int[][] x4 = {{50,50,50}, {0,0,0}, {100, 100, 75}};

		int[][] x5 = {{-50,-50,50}, {0,0,0}, {100, 100, 75}};



		System.out.println(arrayToString(studentAverages (x1)));	
		System.out.println(arrayToString(studentAverages (x2)));	
		System.out.println(arrayToString(studentAverages (x3))); // When there is no 2darray.
		System.out.println(arrayToString(studentAverages (x4)));	
		System.out.println(arrayToString(studentAverages (x5))); // When the kid be stupid enough to get a negative score	



	}
}