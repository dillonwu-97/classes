public class quiz8p2 {
	public static void main (String[] args) {
		System.out.println("result: " + f("WXYZ", "PQ"));
	}

	public static int f(String x, String y) {
		if (x.length() < y.length()) {
			return x.length();
		} else {
			int a = f(x.substring(1), y);
			System.out.println(x + " " + y);
			int b = f(x, y + y);
			return a + b;
		}
	}
}