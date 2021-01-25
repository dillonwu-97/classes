public class Rectangle {
	public static void main (String[] args) {
		Turtle steve = new Turtle();
		for (int i=0; i<2; i++) {
			steve.forward(100);
			steve.left(90);
			steve.forward(60);
			steve.left(90);
		}
	}
}