public class Arrays2d {
	
	public static void main (String[] args) {
		int[] a = {10, 7, 5, 2, 13};
		int[][] x = new int[2][3];
		x[0][0] = 100;

        System.out.println(arrayToString(a, 5));
        double [][] y = new double[2][3];

        y[0][0] = 0;
        y[0][1] = 1;
        y[0][2] = 2;
        y[1][0] = 3;
        y[1][1] = 4;
        y[1][2] = 5;

        System.out.println(matrixToString(y, 2, 3));

        double [][] yt =transpose(y,2,3);
        System.out.println(matrixToString(yt,3,2));



    }

    public static String arrayToString(int[] a, int n) {
        String result = "[";
        for (int i = 0; i < n - 1; i++) {
            result = result + a[i] + ", ";
        }
        if (n > 0) {
            result = result + a[n - 1];
        }
        result += "]";
        return result;
    }


	public static String matrixToString (double[][] x, int n, int m) {
		String result = "[\n";
		for (int row = 0; row < n; row++) {
			result += " [";
			for (int col = 0; col < m; col++) {
				result += x[row][col] + ", ";
			}
			result += x[row][m-1] + "]\n";
		}
		result += "]";
		return result;
	}

    public static double[][] transpose (double[][] x, int n, int m) {
        double [][] result = new double[m][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                result[col][row] = x[row][col];
            }
        }
        return result;
    }

     public static boolean isTriangular (double[][] x, int n) {
        // lower triangular
        boolean isLowerTriangular = true;
        for (int row = 0; row < n - 1; row++) {
            for (int col = row + 1; col < n; col++) {
                if (x[row][col] != 0) {
                    isLowerTriangular = false;
                }

            }
        }

        // upper triangular
        boolean isUpperTriangular = true;
        for (int row = 1; row < n; row++) {
            for (int col = 0; col < row; col++) {
                if (x[row][col] != 0) {
                    isLowerTriangular = false;
                }

            }
        }
        return isUpperTriangular || isLowerTriangular;
    }

   

  



}