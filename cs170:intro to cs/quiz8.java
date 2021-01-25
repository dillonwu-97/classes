public class quiz8 {
	
	public static void main (String[] args) {
		int[][] a = { {1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
		System.out.println(f(a,3,4));
	}

	public static int f(int[][]x, int rows, int cols) {
		if (rows == 1 || cols == 1) {
			System.out.println("base");
			return x[0][0];
		} else {
			if (x[rows - 1][cols - 1] % 2 == 0) {
				System.out.println("print" + rows + cols);
				return x[rows-1][cols-1] + f(x, rows - 1, cols - 1);
			} else {
				System.out.println("decreasing " + cols);
				return f(x, rows, cols - 1);
			}
		}
	}
}