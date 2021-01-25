public class HelloTurtle {
	public static void main(String[] args) {
		Turtle amy = new Turtle(); // create a new turtle
		amy.forward(100); // moves amy 100 steps forward
		amy.left(90); //rotate amy 90 degrees to the left
		amy.backward(50);
		amy.right(45);
		amy.forward(50);
		amy.penup(); //lifts the pen up; nothing will be drawn
		amy.forward(100);
		amy.pendown(); //Amy draws after this
		amy.forward(50);
		amy.color("red");
		amy.forward(100);
	}
}