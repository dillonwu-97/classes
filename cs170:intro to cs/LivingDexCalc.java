public class LivingDexCalc {
	
	public static void main (String[] args) {
		System.out.println(Calc(130));
		System.out.println(Calc(1)); // 1
		System.out.println(Calc(30)); // 1
		System.out.println(Calc(60)); // 2
		System.out.println(Calc(59)); // 2
		System.out.println(Calc(79));
		System.out.println(Calc(570));

	}

	public static int Calc(int x) {
		int result;
		if (x % 30 == 0) {
			result = x / 30 - 1;
		} else {
			result = x / 30;
		}
		result = result + 1;
		return result;
	}
}