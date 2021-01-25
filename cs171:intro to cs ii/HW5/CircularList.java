/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       March 23, 2018

   Compilation:   javac CircularList.java
   Execution:     java CircularList

   This program creates a circular list and gives it some functions it can execute.
*/

public class CircularList {
	
	private ListElem head;		// pointer to the first element in list
	private int size;		// size of the list
	
	public CircularList(){
		/*
		 * build an empty CircularList
		 */
		head = null;
		size = 0;
	}

	// if the list is empty, return true
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	// returns the size of the circular list 
	public int getSize() {
		return size;
	}

	
	// removes an element from the head of the list and returns the value removed
	// if the removed value is null, return null
	public Integer remove() {
		// store the value in a temporary location
		int temp = 0;
		if (size == 0) {
			return null;
		} else {
			// remove the element by cutting off its link to the next element
			// return the value
			// to change the size of the list once an element is removed 
			size = size - 1;
			temp = head.value;
			head = head.next;
			return temp;
		}
	}
	
	// inserts a new element at the head of the circular list
	public void insert (ListElem newElem) {
		// move the head pointer to the new element and make the pointer from the new element
		// the old head
		size++;
		ListElem oldhead = new ListElem();
		oldhead = head;
		head = newElem;
		newElem.next = oldhead;
	}

	// rotates the circular list meaning the first element in the list is moved to the end of 
	// the list, and all other elements in the list are moved forward one position.
	// Note: Might need to come up with something in case size = 1 or 0
	public void rotate () {
		// tail gives the location of where the end of the list is
		ListElem tail = new ListElem();
		tail = head;
		for (int i = 0; i < size - 1; i++) {
			tail = tail.next;
		}
		tail.next = head; // make the next element after the tail the head
		head = head.next; // make the new head the element after the head
	}

	public void printList() {
		// creating a temporary variable so that the position of head doesn't need to change
		ListElem oldhead = new ListElem();
		oldhead = head;
		oldhead.next = head.next;
		for (int i = 0; i < size; i++) {
			System.out.println(oldhead.value);
			oldhead = oldhead.next;
		}
	}

}
