public class Variables {
	
	public static void main (String[] args) {
		int x; // declare a variable x of type int
		x = 3; // assign value 3 to variable x
		System.out.println(x); // 3
		int y = 5; // declaration and assignment

		int z;
		z = x + y;
		System.out.println("x contains " + x); // 3
		System.out.println("y contains " + y); // 5
		System.out.println("z contains " + z); // 8
		x = 30;
		y = 50;
		System.out.println("x contains " + x); // 30
		System.out.println("y contains " + y); // 50
		System.out.println("z contains " + z); // 8 (the value contained in z has not changed)

		x = x + 1; // 31
		System.out.println("x contains " + x); // 31

		// x = 5.2; // ERROR
		x = (int)5.2; // 5
		System.out.println("x contains " + x); // 5

		// double x; // ERROR: can't redefine x

	}
}