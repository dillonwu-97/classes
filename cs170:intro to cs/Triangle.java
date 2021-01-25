public class Triangle {
	
	double side; // length of the side

	public Triangle (double side) {
		this.side = side;

	}
	public String toString() {
		return "Triangle with side: " + side;
	}

	public double perimeter() {
		return side * 3;
	}

	public Square (double side) {
		this.side = side;

	}
	public String toString() {
		return "Square with side: " + side;
	}

	public double perimeter() {
		return side * 4;
	}

	int numSides;
	public Polygon (double side, int numSides) {
		this.side = side;
		this.numSides = numSides;
	}

	public double perimeter() {
		return side * numSides;
	}

}
