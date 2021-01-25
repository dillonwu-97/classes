/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
	Author: 		Dillon Wu
	Written: 		February 14, 2018

	Compilation: 	javac HW2Example.java
	Execution: 		java HW2Example

	This code has methods which compare various dimensions of rectangles and circles.
*/


public class HW2Example{

	// This takes two shapes and returns true if the area of the circle is bigger than
	// or equal to the area of the rectangle and false otherwise
	public static boolean isLarger (Circle a, Rectangle b) {
		if (a.getArea() >= b.area()) {
			return true;
		} else {
			return false;
		}
	}

	// this takes an input of two circles and returns true if the first circle contains 
	// the center of the second circle. 

	public static boolean containsCenter (Circle a, Circle b) {
		
		// write a method called getX and getY that get the origin from Circle
		if (a.containsPoint(b.getX(), b.getY())) {
			return true;
		} else {
			return false;
		}
	}

	// One parameter takes a circle and then a rectangle.
	// It returns the length of the perimeter of the longer of the two objects.
	// If the lengths are the same, then just return the length
	public static double isLonger (Circle a, Rectangle b) {
		if (a.getCircumference() >= b.perimeter()) {
			return a.getCircumference();
		} else {
			return b.perimeter();
		}
	}

	// One parameter takes a rectangle, and then a circle.
	// It returns the length of the perimeter of the longer of the two objects
	// If the lengths are the same, then just return the length
	public static double isLonger (Rectangle b, Circle a) {
		if (a.getCircumference() >= b.perimeter()) {
			return a.getCircumference();
		} else {
			return b.perimeter();
		}
	}

	// One parameter takes a circle and then a rectangle.
	// The method returns the area of the larger of the two objects. 
	public static double isDeeper (Circle a, Rectangle b) {
		if (a.getArea() >= b.area()) {
			return a.getArea();
		} else {
			return b.area();
		}
	}

	// One parameter takes a circle and then a rectangle.
	// The method returns the area of the larger of the two objects. 
	public static double isDeeper (Rectangle b, Circle a) {
		if (a.getArea() >= b.area()) {
			return a.getArea();
		} else {
			return b.area();
		}
	}

// To test the homework
	
/*
	public static void main (String[] args) {
		System.out.println("-- Cylinder Test --");
		
		Cylinder one = new Cylinder(5.0, 0.0, 1.0, 10.0);
		System.out.println(one.getArea());
		System.out.println(one.getHeight());
		one.setHeight(20);
		System.out.println(one.getHeight());
		System.out.println(one.getArea());
		System.out.println(one.getRadius());
		one.setRadius(10);
		System.out.println(one.getArea());
		System.out.println(one.getRadius());
		System.out.println(one.getX());
		System.out.println(one.getY());
		one.setCenter(10, 2);
		System.out.println(one.getX());
		System.out.println(one.getY());

		System.out.println("-- Circle Test --");

		Circle testa = new Circle();
		Circle testb = new Circle(5);
		Circle testc = new Circle(5, 5, 5);

		System.out.println(testb.getRadius());
		System.out.println(testc.getRadius());

		System.out.println(testa.getArea());
		System.out.println(testb.getArea());
		System.out.println(testc.getArea());

		System.out.println(testa.getCircumference());
		System.out.println(testb.getCircumference());
		System.out.println(testc.getCircumference());


		System.out.println(testa.containsPoint(0,0));
		System.out.println(testb.containsPoint(0,0));
		System.out.println(testc.containsPoint(0,0));
		System.out.println(testb.containsPoint(3,3));
		System.out.println(testc.containsPoint(5,5));

		System.out.println(testa.toString());
		System.out.println(testb.toString());
		System.out.println(testc.toString());

		System.out.println("-- Rectangle Test --");

		Rectangle trya = new Rectangle();
		Rectangle tryb = new Rectangle(5, 10, 0, 0);

		System.out.println(trya.getLength());
		System.out.println(trya.getHeight());
		System.out.println(tryb.getLength());
		System.out.println(tryb.getHeight());

		System.out.println("-- HW2 Example Test --");

		System.out.println(containsCenter(testa, testb));
		System.out.println(containsCenter(testa, testc));
		System.out.println(isLarger(testa, trya));
		System.out.println(isLonger(testa, trya));
		System.out.println(isDeeper(testa, trya));
		System.out.println(isLonger(trya, testa));
		System.out.println(isDeeper(trya,testa));
		
	}
*/
}