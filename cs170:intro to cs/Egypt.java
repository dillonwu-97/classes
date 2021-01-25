public class BlockPyramid {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(0);
		pyramid(minion, 200, 10);
		pyramid (minion, 200, 5);
		pyramid (minion, 300, 20);

	}

	public static void pyramid (Turtle t, double base, int levels) {
		
		for (int i = 1; i <= levels; i ++) {

			for (int k = 0; k < 2*i-1; k++) {
				block(t, (base/levels));
				t.forward(base/levels);
			}
		 	for (int j = 2*i-2; j >= 0; j--) {
		 		t.backward(base/levels);
			 }
		t.penup();
		t.backward(base/levels);
		t.right(90);
		t.forward(base/levels);
		t.left(90);
		t.pendown();

		}
		for (int i = 0; i < levels; i++) {
			t.penup();
			t.left(90);
			t.forward(base/levels);
			t.right(90);
			t.forward(base/levels);
		}
		t.pendown();

	}



	//public static void blockSide(Turtle t, double base, int levels) {
	//	for (int i = 0; i < 2*levels-1; i++) {
	//		block(t, (base/levels));
	//		t.forward(base/levels);
	//	}
	//	 for (int i = 2*levels-2; i >= 0; i--) {
	//	 	t.backward(base/levels);
	//	 }
	//}

	public static void block (Turtle t, double size) {
		for (int i = 0; i < 4; i++) {
			t.forward(size);
			t.left(90);
		}
	}
}