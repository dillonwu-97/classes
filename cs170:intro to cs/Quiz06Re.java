public class Quiz06Re {
	
	public static void main (String[] args) {
		int a = 4;
		int b = 24;
		while (a < b) {
			System.out.println("car " + a + " " + b);
			while (b / a > 1) {
				a = a + 5;
				System.out.println(a);
			}
			System.out.println(b);
			b-=3;
		}
	}


}