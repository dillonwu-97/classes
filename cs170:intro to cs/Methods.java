public class Methods {
	
	public static void main(String[] args) {
		int x; 
		int y; 
		x = 1;
		y = addOne(x);
		System.out.println("x contains " + x);
		System.out.println("y contains " + y);
		System.out.println(sum(x,y)); // 3
		// System.out.println(z); // ERROR
	}

	public static int addOne (int x) {
		return x + 1; 
	}

	public static int sum(int x, int y) {
		int z = x + y; 
		return z;
	}

}