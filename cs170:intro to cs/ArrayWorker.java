public class ArrayWorker {

  public static void main(String[] args) {
    int[] t = new int[10];
    t[0] = 5;
    t[1] = 3;
    t[2] = 7;
    t[3] = 9;
    System.out.println(arrayToString(t, 4));
    // test insert
    insert(t, 4, 10, 2);
    System.out.println(arrayToString(t, 5));
    // test remove
    int q = remove(t, 5, 1);
    System.out.println("q: " + q + ", a: " + arrayToString(t, 4));
  }

  /**
   * Returns a string representation of an array of integers in the following
   * format: [a0, a1, a2, ..., an-1]
   * 
   * @param a an array of integers
   * @param n the number of elements in a (n <= a.length)
   */
  public static String arrayToString(int[] a, int n) {
    String result = "[";
    for (int i = 0; i < n - 1; i++) {
      result = result + a[i] + ", ";
    }
    if (n > 0) {
      result = result + a[n - 1];
    }
    result += "]";
    return result;
  }
  
  /**
   * Inserts an integer x into position pos in a given array.
   * All the original elements after pos are shifted forward.
   * Assume that there is enough free space in the array to
   * insert the new element.
   * 
   * @param a an array of integers
   * @param n number of elements in a
   * @param x element to be inserted
   * @param pos index at which x should be inserted
   */
  public static void insert(int[] a, int n, int x, int pos) {
    for (int i = n - 1; i >= pos; i--) {
      a[i + 1] = a[i]; 
    }
    a[pos] = x;
  }

  /**
   * Removes the element at index pos from a given array.
   * All the elements after pos are shifted backward.
   * 
   * @param a an array of integers
   * @param n number of elements in a
   * @param pos index of the element to be removed
   * @return the element that was removed
   */
  public static int remove(int[] a, int n, int pos) {
    int result = a[pos];
    for (int i = pos; i < n; i++ ) {
      a[i] = a[i + 1];
    }
    return result; 

  }

}

