public class Bouquet {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(1);
		//simpleFlower(minion, 100);
		//fractalFlower(minion, 200, 1);
		fractalFlower(minion, 250, 3);
		//fractalFlower(minion, 300, 4);
	}

	public static void simpleFlower (Turtle t, double size) {
		t.left(90.0);
			t.forward(size * 2.0 / 3.0);
			for (int i = 0; i < 8; i++) {
				t.left(45.0);
				t.forward(size * 1.0/3.0);
				t.backward(size * 1.0 / 3.0);
			}
		t.backward(size * 2.0/3.0);
	}

	public static void fractalFlower (Turtle t, double size, int level) {
		t.left(90.0);
		t.forward(size * 2.0 / 3.0);
		fractalPetals (t, size, level);
		t.backward(size * 2.0/3.0);
	}

	public static void fractalPetals (Turtle t, double size, int level) {
		if (level == 0) {
			return;
		} else {
			for (int i = 0; i < 8; i++) {
				t.left(45.0);
				t.forward(size * 1.0/3.0 * 2.0/3.0);
				fractalPetals (t, size * 1.0/3.0, level - 1);
				t.backward(size * 1.0 / 3.0 * 2.0 / 3.0);
			}

		}
	}
}