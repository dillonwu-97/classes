import java.util.Arrays;

public class Sorting {

  public static void main(String[] args) {
    int[] a = {3, 2, 5, 1, 7, 8, 9, 2, 7};
    System.out.println("a: " + Arrays.toString(a));
    selectionSort(a);
    System.out.println("a: " + Arrays.toString(a));

    int[] b = {3, 2, 5, 1, 7, 8, 9, 2, 7};
    System.out.println("b: " + Arrays.toString(b));
    bubbleSort(b);
    System.out.println("b: " + Arrays.toString(b));
  }

  // Sorts array x in place (modifies the original array)
  // using the selection sort algorithm
  public static void selectionSort(int[] x) {
    for (int i = 0; i < x.length - 1; i++) {
      // find the index of the minimum element
      int minIndex = i;
      for (int j = i + 1; j < x.length; j++) {
        if (x[j] < x[minIndex]) {
          minIndex = j;
          System.out.println("minIndex: " + minIndex);
        }
        System.out.println("j value: " + j);
      }
      // swap the current element with the minimum
      int temp = x[i];
      System.out.println("temp: " + temp);
      x[i] = x[minIndex];
      x[minIndex] = temp;
    }
  }

  // Sorts array x in place (modifies the original array)
  // using the bubble sort algorithm
  public static void bubbleSort(int[] x) {
    boolean swap = true;
    // repeat this process until there are no more swaps
    while (swap) {
      swap = false;
      for (int i = 1; i < x.length; i++) {
        if (x[i] < x[i - 1]) {
          // swap x[i] and x[i - 1]
          int temp = x[i];
          x[i] = x[i - 1];
          x[i - 1] = temp;
          swap = true;
          System.out.println("what happens: " + Arrays.toString(x));

        }
      }
    }
  }

}

