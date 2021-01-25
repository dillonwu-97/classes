public class Hanoi2 {
	
	public static void main(String[] args) {
		hanoi(4, "A", "C", "B");

	}

	public static void hanoi(int n, String source, String target, String temp) {
		if (n==1) {
			System.out.println("Move " + source + " to " + target);
		} else {
			hanoi(n - 1, source, temp, target);
			hanoi(1, source, target, temp);
			hanoi(n - 1, temp, target, source);
		}
	}
}