public class WhileLoops {
	public static void main (String[] args) {
		manyHellos(3);
		
	}

	// Example 1: Simple repitition
	public static void manyHellos (int n) {
		int i = n; // inititalization
		while (i > 0) {	// loop header; condition
			// loop body
			System.out.println ("Hello" + i);
			i--; // same as i = i - 1
		}
		System.out.println("Goodbye");
	}
}