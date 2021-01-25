public class TestGeometry {
	public static void main(String[] args ) {
		Triangle t = new Triangle (100);
		System.out.println("t: " + t);
		System.out.println("perimeter of t: " + t.perimeter());

		Square s = new Square (200);
		System.out.println("s: " + s);
		System.out.println("perimeter of s: " + s.perimeter());

		Polygon p = new Polygon (300, 5);
		System.out.println("p: " + p);
		System.out.println("perimeter of p: " + p.perimeter());
	}
}