public class Quiz12 {
	
	public static void main (String[] args) {
		String[] a = {"x", "z", "y"};
		String[] b = {"x", "z", "p", "w"};
		System.out.println(search("q", b));
		System.out.println(search(b[search("z", a)], a));

	}

	public static int search(String s, String[] x) {
		if (x.length % 2 == 0) {
			for (int i = 0; i < x.length; i++) {
				System.out.println(s + " " + x[i]);
				if (s.equals(x[i])) {
					return i;
				}
			}

		} else {
			for (int i = x.length - 1; i >= 0; i--) {
				System.out.println(s + " " + x[i]);
				if (s.equals(x[i])) {
					return i;
				}
			}
		}
		return -1;
	}
}