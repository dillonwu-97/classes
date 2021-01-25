/*
	Author: 		Dillon Wu
	Written: 		September 9, 2017

	Compilation: 	javac Landscape.java
	Execution: 		java Landscape

	Uses the turtle to create a painting of a castle.
	Call me Michelangelo.
*/

public class Landscape {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(0);
		kingdom(minion, 10);
		//walls(minion, 10);
		//castle(minion, 10);
		//arcs(minion, 10);
		//forest(minion, 10);
		//star(minion, 10);
		//towers(minion, 10);
		//mountain(minion, 10);
		//mountainScape(minion, 10);
	}

	//	The parameter length is the length of each base unit.
	// 	leftMove helps the turtle turn left and move a "length" amount.

	public static void leftMove(Turtle t, double length) {
		t.left(90);
		t.forward(length);
	}

	//	rightMove helps the turtle turn right and move a "length" amount. 

	public static void rightMove(Turtle t, double length) {
		t.right(90);
		t.forward(length);
	}

	//	The method kingdom combines all the methods to create the final picture.
	//	Specifically, it uses castle, arcGarden, forest, starCluster, and mountainScape.
	
	public static void kingdom (Turtle t, double length) {
		castle(t, length);
		t.forward(length * 7.0);
		leftMove(t, length * 3.0);
		t.right(90);
		arcGarden(t, length);
		arcs(t, length);
		t.penup();
		rightMove(t, length * 2.0);
		t.left(90);
		t.pendown();
		forest(t, length);
		t.penup();
		t.backward(5.0 * length);
		rightMove(t,length);
		t.left(90);
		t.pendown();
		forest(t, length);
		t.penup();
		leftMove(t, length * 17.0);
		leftMove(t, length * 47.0);
		t.left(180);
		t.pendown();
		mountainScape(t, length);
		starCluster(t, length); 
		t.penup();
		leftMove(t, length * 31.0);
		leftMove(t, length * 3.0);
		t.pendown();
	}

	//	The method castle uses towers, walls, and arcs to create the final castle.

	public static void castle (Turtle t, double length) {
		towers(t, length);
		t.forward(length * 5.0);
		walls(t, length);
		t.forward(length* 8.0);
		arcs(t, length);
		t.penup();
		t.forward(length * 4.0);
		walls(t, length);
		t.forward(length * 8.0);
		towers(t, length);
	}

	//	arcGarden create the three arcs above the forest.

	public static void arcGarden (Turtle t, double length) {
		for (int i = 0; i < 2; i++) {
			arcs(t, length);
			t.penup();
			t.forward(5.0 * length);
			leftMove(t, 2.0 * length);
			t.right(90);
		}
	}

	//	arcs utilizes the column and halfCircle methods.

	public static void arcs (Turtle t, double length) {
		column(t, length);
		t.penup();
		t.forward(3.0 * length);
		t.pendown();
		column(t, length);
		leftMove(t, 3.0 * length);
		t.right(90);
		halfCircle(t, length);
		t.penup();
		t.forward(3.0 * length);
		t.pendown();
		halfCircle(t, 2.0 * length);
		rightMove(t, length * 3.0);
		t.left(90);


	}

	public static void column (Turtle t, double length) {
		leftMove(t, length*3);
		t.right(90);
		t.penup();
		t.forward(length);
		t.pendown();
		rightMove(t, 3.0 * length);
		t.left(90);
		t.backward(length);
	}

	//	halfCircle approximates to create an arc but is not exact.

	public static void halfCircle (Turtle t, double length) {
		double x = length * 2.0 * Math.PI/360;
		t.left(90);
		for (int i = 0; i < 180; i++) {
			t.forward(x);
			t.left(360/360);
		}
		t.left(90);
	}

	//  The method walls starts from the bottom and uses wallOne and wallTwo as the layers to make the wall.

	public static void walls (Turtle t, double length) {
		for (int i = 0; i < 3; i++) {
			wallOne(t, length);
			t.backward(8.0 * length);
			leftMove(t, length * .5);
			t.right(90);
			wallTwo(t, length);
			t.backward(8.0 * length - (0.5 * length));
			leftMove(t, length * 0.5);
			t.right(90);
		}
		wallOne(t, length);
		t.backward(length * 8.0);
		rightMove(t,length * 3.0);
		t.left(90);

	}

	public static void block (Turtle t, double length) {
		for (int i = 0; i < 2; i++) {
			t.forward(length);
			leftMove(t, length * .5);
			t.left(90);
		}
	}

	public static void halfBlock (Turtle t, double length) {
		for (int i = 0; i < 4; i++) {
			t.forward(.5 * length);
			t.left(90);
		}
	}

	//	This method makes the line of blocks that don't have half blocks.

	public static void wallOne(Turtle t, double length) {
		for (int i = 0; i < 8; i++) {
			block(t, length);
			t.penup();
			t.forward(length);
			t.pendown();
		}	
	}

	//	This makes the line of blocks that have a half block at the head of the line.

	public static void wallTwo(Turtle t, double length) {
		halfBlock(t, length);
		t.forward(length * .5);
		for (int i = 0; i < 7; i++) {
			block(t, length);
			t.penup();
			t.forward(length);
			t.pendown();
		}
		halfBlock(t,length);
 	}

 	//	I decided not to use the leftMove and rightMove methods for the angle calculations
 	//	so as to avoid confusing myself.

	public static void towers (Turtle t, double length) {
		t.forward(5.0 * length);
		leftMove(t, length * 10.0);
		t.right(90 - Math.toDegrees (Math.atan (1.0 / 2.0)));
		t.forward(Math.sqrt (Math.pow (length, 2.0) + Math.pow((2.0 * length), 2.0)));
		t.left(90 - Math.toDegrees (Math.atan (1.0 / 2.0)));
		t.forward(2.0 * length);
		t.left(90);
		towerStones (t, length);
		t.forward(length);
		leftMove(t, length * 2.0);
		t.left(90 - Math.toDegrees (Math.atan (1.0 / 2.0)));
		t.forward(Math.sqrt (Math.pow (length, 2.0) + Math.pow((2.0 * length), 2.0)));
		t.right(90 - Math.toDegrees (Math.atan (1.0 / 2.0)));
		t.forward(10.0 * length);
		t.left(90);
		windowSet(t, length);
	}

	//  This creates the stones on top of the tower.

	public static void towerStones (Turtle t, double length) {
		for (int i = 0; i < 4; i++) {
			t.forward(length);
			leftMove(t, length);
			rightMove(t, length);
			rightMove(t, length);
			t.left(90);
		}
	}

	public static void windows (Turtle t, double length) {
		for (int i = 0; i < 4; i++) {
			t.forward(length);
			t.left(90);
		}
	}

	//  This moves turtle from the starting position at the bottom of the castle to 
	//	where it needs to be to start building the windows.

	public static void windowSet (Turtle t, double length) {
		leftMove(t, length * 4.0);
		t.right(90);
		t.penup();
		t.forward(length);
		t.pendown();
		windownPack(t, length);
		t.penup();
		t.backward(length);
		t.left(90);
		t.backward(length * 10.0);
		t.right(90);
	}

	//	The method windowPack makes the six windows. 

	public static void windownPack (Turtle t, double length) {
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 2; j ++) {
				windows(t, length);
				t.penup();
				t.forward(2.0 * length);
				t.pendown();
			}
			t.penup();
			t.backward(4.0 * length);
			t.left(90);
			t.forward(2.0 * length);
			t.right(90);
			t.pendown();
		}
	}

	//	The method forest makes a set of four trees. 

	public static void forest(Turtle t, double length) {
		for (int i = 0; i < 4; i++) {
			tree(t, length);
			t.penup();
			t.forward(2.0 * length);
			leftMove(t,3.0 * length);
			t.right(90);
			t.pendown();
		}
		t.penup();
		rightMove(t, 3.0 * length);
		t.left(90);
		t.backward(11.0 * length);
		t.pendown();

	}

	public static void tree (Turtle t, double length) {
		leaf(t, length);
		t.penup();
		t.forward(length);
		t.pendown();
		leftMove(t, 4.0 * length);
		t.right(90);
		rightMove(t, 4.0 * length);
		t.left(90);

	}

	//	The method leaf makes all the triangle hats of one tree.

	public static void leaf (Turtle t, double length) {
		for (int i = 0; i < 5; i++) {
			t.left(45);
			t.forward(length * Math.sqrt(2));
			rightMove(t, length * Math.sqrt(2));
			t.left(45);
			t.penup();
			t.backward(2.0 * length);
			rightMove(t, .5 * length);
			t.left(90);
			t.pendown();
		}
		t.penup();
		rightMove(t, length * .5);
		t.left(90);

	}

	public static void star (Turtle t, double length) {
		for (int i = 0; i < 8; i++) {
			t.forward(length);
			t.backward(length);
			t.left(45);
		}
	}

	//	It was impossible to use for-loops in this method because of the 
	//	arbitrary placement of the stars.

	public static void starCluster (Turtle t, double length) {
		t.penup();
		t.forward(9.0 * length);
		leftMove(t, length * 12.0);
		t.right(90);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(length * 16.0);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(19.0 * length); 
		rightMove(t, length);
		t.left(90);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(8.0 * length);
		leftMove(t, 5.0 * length);
		t.left(90);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(14.0 * length);
		rightMove(t, length);
		t.left(90);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(17.0 * length);
		rightMove(t, 2.0 * length);
		t.left(90);
		t.pendown();
		star(t, length);
		t.penup();
		leftMove(t, 3.0 * length);
		rightMove(t, 8.0 * length);
		t.pendown();
		star(t, length);
		t.penup();
		t.forward(8.0 * length);
		t.pendown();
		star(t, length);
	}

	//	The method mountain makes only one mountain.

	public static void mountain (Turtle t, double length) {
		t.left(Math.toDegrees (Math.atan (1.0 / 2.0)));
		t.forward(Math.sqrt (Math.pow(8.0 * length, 2.0) + Math.pow((16.0 * length), 2.0)));
		t.right(90);
		t.forward(Math.sqrt (Math.pow(6.0 * length, 2.0) + Math.pow((12.0 * length),2.0)));
		t.left(90 - Math.toDegrees (Math.atan (1.0 / 2.0)));
	}

	//	The method mountainScape makes three mountains and puts them together.

	public static void mountainScape (Turtle t, double length) {
		mountain(t, length);
		t.penup();
		t.backward(4*length);
		leftMove(t, 8*length);
		t.right(90);
		t.pendown();
		mountain(t, length);
		t.penup();
		t.backward(length);
		leftMove(t, 2*length);
		t.right(90);
		t.pendown();
		mountain(t, length);
		t.penup();
		t.backward(length * 61);
		leftMove(t, length * 2.0);
		t.right(90);
		t.pendown();
	}

}