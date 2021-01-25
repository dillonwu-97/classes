import java.io.IOException;
import java.util.ArrayList;

public class Sandbox {
	public static void main (String[] args) {
		int x = 2;
		int y = 1;
		
		swap(x, y);
		System.out.println(x);

		System.out.println(fib(4));
		System.out.println(fib(5));

		System.out.println(changePi("xyzzz"));
	
		
	}
	public static void swap(int x, int y) {
		int temp = x;
		x = y;
		y = temp;

	}
	public static int add (int x) {
		return 0;
	}

	public static int fib (int x) {
		if (x == 0) {return 0;} 
		else if (x == 1) {return 1;}
		else {return fib(x-1) + fib(x-2);}
	}

	

	public static String changePi(String str) {
  if (str.length() == 0) {
    return "";
  } else if (str.length() == 1) {
    return ""+ str.charAt(str.length()-1);
  } else if (str.substring(str.length()-2, str.length()).equals("pi")) {
    return changePi(str.substring(0, str.length()-2)) + 3.14;
  } else {
  	    System.out.println(str.charAt(str.length()-1));

    return changePi(str.substring(0, str.length()-1) + str.charAt(str.length()-1));
  }
}

}