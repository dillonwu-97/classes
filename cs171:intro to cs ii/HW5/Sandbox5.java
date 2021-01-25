import java.util.Stack;
import java.util.ArrayDeque;
import java.util.Iterator;

public class Sandbox5 {
	
	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		for (int i = 0; i < 10; i++) {
			test.push(i);
		} 

		for (double x: test) {
			System.out.println(x);
		}

		System.out.println("Queue Test");

		ArrayDeque<Integer> test2 = new ArrayDeque<Integer>();
		for (int i = 0; i < 10; i++) {
			test2.add(i);
		}

		Iterator<Integer> j = test2.iterator();
		while (j.hasNext()) {
			Integer temp = j.next();
			System.out.println(temp);

		}

		System.out.println ("++i");
		int m = 0;
		while (m < 10) {
			System.out.println("m is " + ++m); // starts with 1
		}

		System.out.println("i++");

		int m2 = 0;
		while (m2 < 10) {
			System.out.println("m2 is " + m2++); // starts with 0
		}

	}
}