public class Polygon {
	
	public static void main(String[] args) {
		Turtle carl = new Turtle();
		//square(carl, 50);
		square(carl,200);
		eqTri(carl,200);
		//eqPent(carl,100);
		//poly(carl, 120, 7);
		//poly2(carl,120, 5);
		//dashedLine(carl, 400, 5);
		dashedPoly(carl,100,6,7);
	}

	public static void square(Turtle t, double size) {
		for (int i = 0; i<4; i++) {
			t.forward(size);
			t.left(90);
		}
	}

	//draw a triangle

	public static void eqTri(Turtle a, double size) {
		for (int i = 0; i<3; i++) {
			a.forward(size);
			a.left(120); //360/3
		}
	}

	//draws a pentagon
	public static void eqPent(Turtle a, double size) {
		for (int i = 0; i<5; i++) {
			a.forward(size);
			a.left(72); //360 / 5
		}
	}

	//draws a polygon of any number of sides

	public static void poly(Turtle a, double size, int numSides) {
		for (int i =0; i< numSides; i++) {
			a.forward(size);
			a.left(360/numSides);
		}
	}

	public static void poly2(Turtle a, double size, int numSides) {
		for (int i =0; i< numSides; i++) {
			a.forward(size);
			a.penup();
			a.forward(size);
			a.pendown();
			a.left(360/numSides);
		}
	}

	//draws a dashed line
	public static void dashedLine(Turtle t, double length, int numDashes) {
		for (int i = 0; i <numDashes - 1; i++) {
			t.forward(length / (2* numDashes - 1));
			t.penup();
			t.forward(length / (2* numDashes - 1));
			t.pendown();
		}
		t.forward(length / (2* numDashes - 1)); //last dash
	}

	//draws a dashed polygon
	public static void dashedPoly(Turtle t, double length, int numDashes, int numSides) {
		for (int i =0; i<numSides; i++) {
			dashedLine(t, length, numDashes);
			t.left(360/numSides);
		}
	}



}