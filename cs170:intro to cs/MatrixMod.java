/* Write a recursive method matrixMod(int[][] x, int rows, int cols, int k) that
takes a 2D array of integers, its number of rows, its number of columns, and an
integer k. The method changes each element of the original 2D array x with its
previous value modulo k. The method returns nothing.

Example:
int[][] x = {{5, 2, 15}, {8, 5, 6}};
after matrixMod(x, 2, 3, 5) x will contain:
{ {0, 2, 0},
  {3, 0, 1} } */

public class MatrixMod {

    // write your method here
    
    public static void main(String[] args) {
        // test your method here
        /*int[][] x1 = {{5, 2, 15}, {8, 5, 6}};
        printArray(matrixMod(x1, 2, 3, 5));
        */
        int[][] x = {{5, 2, 15}, {8, 5, 6}};
        matrixMod(x, 2, 3, 5);
        printArray(x);
    }

    /*public static int[][] matrixMod(int[][] x, int rows, int cols, int k) {
        if (cols == 1) {
            x[rows-1][cols-1] = x[rows-1][cols-1] % k;
            return x;
        } else if (rows >= 2) {
            x[rows-1][cols-1] = x[rows-1][cols-1] % k;
            return matrixMod(x, rows - 1, cols, k);
        } else {
            x[rows-1][cols-1] = x[rows-1][cols-1] % k;
            return matrixMod(x, rows, cols-1, k);
        }
    }
    */
    public static void matrixMod(int[][] x, int rows, int cols, int k) {
        if (rows == 0 || cols == 0) {
            return;
        } else if (x[rows - 1][cols -1] < k) {
            return;
        } else {
            x[rows - 1][cols - 1] %= k;
            matrixMod(x, rows, cols - 1, k);
            matrixMod(x, rows - 1, cols, k);
        }
    }

    // prints a 2D array followed by a blank line
    public static void printArray(int[][] x) {
        for (int row = 0; row < x.length; row++) {
            for (int col = 0; col < x[row].length; col++) {
                System.out.print(x[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
