import java.util.Scanner;

public class Arithmetic {
	
	public static void main (String[] args) {
		System.out.println("Enter a string computation: ");
		Scanner math = new Scanner(System.in);
		//System.out.println(stringify(math));
		output(math);
	}

	public static void output(Scanner math) {
		String temp = "";
		while (!math.hasNext("Q")) {
			temp = stringify(math);
			System.out.println(operate(temp));
			temp = "";
		}
	}

	public static String stringify(Scanner math) {
		String result = "";
		result = result + math.nextLine(); // need to get rid of the spaces
		result = removeSpace(result);
		operate(result);
		return result;
	}

	public static int operate (String math) {
		int result = 1;
		String temp = "";
		int op1 = 0;
		int op2 = 0;
		String operand = "";
		for (int i = 0; i < math.length(); i++) {
			// dealing with negatives
			if (i == 0 && math.charAt(i) == '-') {
				temp = temp + math.charAt(i);
			}
			// dealing with operations
			if (math.charAt(i) != '*' && math.charAt(i) != '-' && math.charAt(i) != '/' && math.charAt(i)!= '+') {
				temp = temp + math.charAt(i);
			} else if (i != 0) {
				op1 = convert(temp);
				//System.out.println("op1: " + op1);
				operand = operand + math.charAt(i);
				temp = "";
				if (math.charAt(i+1) == '-') {
					temp = temp + math.charAt(i+1);
					i = i+1;
				}
			}
			op2 = convert(temp);
		}
		if (operand.equals("*")) {
			result = op1 * op2;
		} else if (operand.equals("+")) {
			result = op1 + op2;
		} else if (operand.equals("/")) {
			result = op1 / op2;
		} else {
			result = op1 - op2;
		}
		return result;
	}

	public static String removeSpace(String math) {
		String result = "";
		for (int i = 0; i < math.length(); i++) {
			if (math.charAt(i) != ' ') {
				result = result + math.charAt(i);
			}
		}
		return result;
	}

	public static int convert (String number) {
		int result = 0;
		int multiplier = 1;
		for (int i = number.length() - 1; i >= 0; i--) {
			result = result + intify(number.charAt(i)) * multiplier;
			multiplier = multiplier * 10;
			// convert number to a negative
			if (number.charAt(i) == '-') {
				result = result * -1;
			}
		}
		return result;
	}
	

	public static int intify (char number) {
		int result = 0;
		if (number == '1') {
			result = 1;
		} else if (number == '2') {
			result = 2;
		} else if (number == '3') {
			result = 3;
		} else if (number == '4') {
			result = 4;
		} else if (number == '5') {
			result = 5;
		} else if (number == '6') {
			result = 6;
		} else if (number == '7') {
			result = 7;
		} else if (number == '8') {
			result = 8;
		} else if (number == '9') {
			result = 9;
		}
		return result;
	}
}