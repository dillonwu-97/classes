public class HW8Sandbox {

	private class Node {
		Entry entry;
		Node next;

		Node(Entry entry) {this.entry = entry; }
	}

	Node[] hashTable;
	int arraySize;
	int tableSize;

	public HW8Sandbox(){
		hashTable = new Node[10];
		arraySize = 10;
	}

	/** TODO: Write a resizing method for the hash table.*/
	private void resize() {
		// if bigger than a certain size, then resize; initial array size is 10, so if the
		// array is maxed out, then resize it
		// if array is full, then double the size of the array
		// on average, the table will be full when you hit 5*arraysize elements. 
		// not sure when I should resize
		// then you hash each object to the new array
		arraySize = arraySize * 2;
		Node[] hashCopy = hashTable;
		hashTable = new Node[arraySize];

		// create a pointer node

		for (int i = 0; i < hashCopy.length; i++) {
			if (hashCopy[i] != null) {
				System.out.println("reached");
				// this returns some number which is the index of the array
				// inside the index of the array is the key 
				//hashTable [hash (hashCopy[i]) ] = hashCopy[i];

				// if there are connected Nodes, then you have to get those as well
				// if node.next is not null, keep on moving the pointer and then backtrack 
				Node track = hashCopy[i];
				put(track.entry.getKey(), track.entry.getValue());


			 	while (track.next != null) {
			 		System.out.println(track.entry.getKey());
			 		track = track.next;
			 		put(track.entry.getKey(), track.entry.getValue());
			 	}
			 	// once you hit the base, you rehash each of the things, and those preceding it
				
			}

		}
	}

	// this creates a new array that you put the elements in

	/** Computes the index in the hash table from a given key */
	private int hash(String key) {
		int hashCode = key.hashCode();
		return (hashCode & 0x7fffffff) % arraySize;
	}

	/** Returns the number of entries in the hash table. */
	public int size() { return tableSize; }

	/** Checks whether the hash table is empty */
	public boolean isEmpty() { return tableSize == 0; }

	/** Returns the node containing the given key value if it exists in the table.
	    Otherwise, it returns a null value. */
	private Node findEntry(String key) {
		int index = hash(key);
		//System.out.println(index);

		Node currentNode = hashTable[index];
		while (currentNode != null && !currentNode.entry.getKey().equals(key))
			currentNode = currentNode.next;

		return currentNode;

	}

	/** Returns the integer value paired with the given key, if the key is in the table.
		Otherwise, it returns null. */
	public Integer get(String key) {
		Node searchResult = findEntry(key);

		if (searchResult != null)
			return searchResult.entry.getValue();
		else
			return null;

	}

	/** If the given key is not in the table, creates a new entry and adds it to the table.
		Otherwise, it updates the value associated with the given key. */
	public void put(String key, Integer value) {
		tableSize++;
		Node searchResult = findEntry(key);

		if (searchResult != null){
		//	searchResult.entry.setValue(value);
			searchResult.entry.setValue(searchResult.entry.getValue() + 1);
			return;
		}

		Entry newEntry = new Entry(key, value);
		Node newNode = new Node(newEntry);

		int index = hash(key);
		if (hashTable[index] != null)
			newNode.next = hashTable[index];

		hashTable[index] = newNode;

		// if the array is full, then resize and put things in again
		if ((tableSize / arraySize) >= 2) {
			System.out.println("reached 0");
			tableSize = 0;
			resize();
		}

	}

	/** Removes the entry containing the given key from the table, if the key exists in the table. */
	public void delete(String key) {
		Node searchResult = findEntry(key);
		if (searchResult == null)
			return;

		int index = hash(key);
		if (hashTable[index] == searchResult)
			hashTable[index] = searchResult.next;
		else{
			Node currentNode = hashTable[index];
			while (currentNode.next != searchResult)
				currentNode = currentNode.next;
			currentNode.next = searchResult.next;
		}
	}

	/** Produces a string representation of the table. */
	@Override
	public String toString(){
		String output = "";
		for (int i = 0; i < arraySize; i++){
			output += "(" + i + "): ";
			Node currentNode = hashTable[i];
			if (currentNode == null)
				output += currentNode + "\n";
			else{
				while (currentNode != null){
					output += " -> " + currentNode.entry;
					currentNode = currentNode.next;
				}
				output += "\n";
			}
		}

		return output;

	}

	public static void main (String[] args) {
		HW8Sandbox test2 = new HW8Sandbox();
		test2.put("hello", 5);
		test2.put("hello", 5);
		test2.put("helm", 99);
		test2.put("hell", 100);
		test2.put("ape", 100);
		test2.put("hell0 world", 100);
		test2.put("china", 100);
		test2.put("acdasdfsda", 100);
		test2.put("kpo9213", 100);
		test2.put("jk;kljdsfa", 100);


		System.out.println(test2.toString());







	}















}