public class Sierpinski {
	
	public static void main(String[] args) {
		Turtle t = new Turtle();
		t.delay(20);
		sierpinski(t, 400, 1);
		sierpinski(t, 400, 3);

		// how many small triangles?
		// L0: 1
		// L1: 3
		// L2: 3 * 3 = 9
		// LN: 3^N

		// what is the size of each small triangle?
		// L0: 400
		// L1: 400 / 2 = 200
		// L2: 400 / (2 * 2) = 100
		// LN: 400 / 2 ^ N

		// how many times is sierpinski called?
		// level 0: 1
		// L1: 1 + 3
		// L2: 1 + 3 + 9
		// LN: 1 + 3 + 9 + ... + 3^N

	}

	public static void sierpinski (Turtle t, double size, int level) {
		if (level == 0) {
			triangle (t, size);
		} else {
			sierpinski(t, size/2, level - 1);
			t.forward(size / 2);
			sierpinski(t, size / 2, level - 1);
			t.left(120);
			t.forward(size / 2);
			t.right(120);
			sierpinski(t, size / 2, level - 1);
			t.left(60);
			t.backward(size / 2);
			t.right(60);
		} 
	}

	public static void triangle (Turtle t, double length) {
		for (int i = 0; i < 3; i++) {
			t.forward(length);
			t.left(120);
		}
	}
}