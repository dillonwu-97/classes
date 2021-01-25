public class squareLine {
	public static void main (String[] args) {
		Turtle bread = new Turtle();
		for (int j=0; j<5; j++) {
			for (int i=0; i<4; i++) {
				bread.forward(40);
				bread.left(90);
			}
			bread.penup();
			bread.forward(100);
			bread.pendown();
		}
		bread.penup();
		bread.backward(500);
	}
}