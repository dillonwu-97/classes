public class GrowingArrows {
	
	public static void main (String [] args) {
	Turtle t = new Turtle();
	growingArrows(t, 150, 5);
	}

	public static void arrow (Turtle t, double length, int branches) {
		for (int i = 1; i < branches + 1; i++) {
			t.left (45);
			t.forward(length * 1/i);
			t.backward(length * 1 / i);
			t.right(90);
			t.forward(length * 1/i));
			t.backward(length * 1 / i);
			t.left(45)
			t.forward(length / branches); 

		}
	}

}