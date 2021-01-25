// Circle Test
/*
public class HW2Test extends Circle {
	public static void main (String[] args) {
		Circle one = new Circle(5, 0, 0);
		System.out.println(one.containsPoint(4, 1));
	}
}
*/

public class HW2Test extends Cylinder {
	public static void main (String[] args) {
		Cylinder one = new Cylinder(5.0, 0.0, 1.0, 10.0);
		System.out.println(one.getArea());
		System.out.println(one.getHeight());

		Circle testa = new Circle();
		Circle testb = new Circle(5);
		Circle testc = new Circle(5, 5, 5);

		Rectangle trya = new Rectangle();
		Rectangle tryb = new Rectangle(5, 10, 0, 0);

		System.out.println(testa.getRadius());
		System.out.println(testb.getRadius());
		System.out.println(testc.getRadius());

		System.out.println(testa.getArea());
		System.out.println(testb.getArea());
		System.out.println(testc.getArea());

		System.out.println(testa.getCircumference());
		System.out.println(testb.getCircumference());
		System.out.println(testc.getCircumference());

		// if there is a static, then don't need to declare an instance. 
		HW2Example hw2;
		System.out.println(hw2.containsCenter(testa, testb));
		System.out.println(testb.containsPoint(0,0));
		System.out.println(testc.containsPoint(0,0));
		System.out.println(testb.containsPoint(3,3));
		System.out.println(testc.containsPoint(5,5));

		System.out.println(trya.getLength());
		System.out.println(trya.getHeight());
		System.out.println(tryb.getLength());
		System.out.println(tryb.getHeight());
		
	}
}