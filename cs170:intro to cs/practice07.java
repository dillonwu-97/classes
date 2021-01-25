public class practice07 {

	public static void main (String[] args) {

		int[][] x1 = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
		printArray(checkMult(x1, 3));

		System.out.println(0 % 5);
	}

	public static boolean[][] checkMult(int[][] x, int k) {
		boolean[][] result = new boolean[x.length][x[0].length];
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[i].length; j++) {
				if (x[i][j] % k == 0) {
					result[i][j] = true;
				} else {
					result[i][j] = false;
				}
			}
		}
		return result;
	}

	

	public static void printArray(boolean[][] x) {
        for (int row = 0; row < x.length; row++) {
            for (int col = 0; col < x[row].length; col++) {
                System.out.print(x[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


}