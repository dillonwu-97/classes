public class Midterm1 {
	
	public static void main (String[] args) {
		int[] test = {5,1,2,3,4};
		topN(test, 4);
	}

	public static int[] topN (int[] x, int count) {
		int[] result = new int[count];
		int max = x[0];
		int min = x[0];
		int track = 0;
		for (int i = 1; i < x.length; i++) {
			if (min > x[i]) {
				min = x[i];
			}

			if (max < x[i]) {
				max = x[i];
			}

			result[track] = max;
			x[i] = min;
			track++;
			if (track == count) {
				break;
			}
		}
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		return result;


	}
}