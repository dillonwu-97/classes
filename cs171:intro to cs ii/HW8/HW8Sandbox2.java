public class HW8Sandbox2 {
	
	
	public static String capDetect (String x) {
		String y = x;
		for (int i = 0; i < x.length(); i++) {
			if (Character.isUpperCase(x.charAt(i))==true) {
				y = x.toLowerCase();
			}
		}
		return y;
	}

	private static boolean specChar(Character c) {
		
		boolean result = true;
		if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) { 
			result = false;
		}
		return result;

	}


	public static void main (String[] args) {
		String test = new String("'heLLo");
		System.out.println(test);
		System.out.println(capDetect(test));

		System.out.println(specChar('1'));
		System.out.println(specChar('0'));
		System.out.println(specChar('9'));
		System.out.println(specChar('h'));
		System.out.println(specChar('&'));

	}

	

}