import java.util.*;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Classwork10 {
	
		public static void main (String[] args) {
			Deque<String> deque = new ArrayDeque<String>();
			deque.add("E1");
			deque.addFirst("E2");
			deque.addLast("E3");
			deque.push("E4");
			deque.offer("E5");
			deque.offerFirst("E6");
			deque.offerLast("E7");
			
			deque.pop();
			deque.removeLast();
			deque.removeFirst();

			
			for (String s: deque) {
				System.out.println(s);
			}

			Queue<String> Q = new Queue<String>();

			for (int k = 1; k <=7; k++) {
				Q.enqueue(k);
			}

			for (int k = 1; k <=7; k++) {
				Q.enqueue(Q.deque());
				Q.deque();
			}

			for(String s : Q) { 
  					System.out.println(Q.toString()); 
			}	
		}
	
}