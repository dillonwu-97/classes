public class RecursionTrace {

  public static void main(String[] args) {
    // What will this program print?
    f1(3, 2);
    System.out.println("---------------");
    System.out.println(f2(20));
    System.out.println("---------------");
    System.out.println(f3(1));
  }

  public static void f1(int x, int y) {
    if (x == y) {
      System.out.println("done " + x + " " + y);
    } else if (x > y) {
      System.out.println("swap " + x + " " + y);
      f1(y, x);
    } else {
      System.out.println("cut " + x + " " + y);
      f1(x, y / 2);
    }
  }

  public static int f2(int n) {
    if (n < 3) {
      System.out.println("end");
      return n;
    } else {
      System.out.println("go " + n);
      return 1 + f2(n / 2);
    }
  }

  public static int f3(int n) {
    if (n > 5) {
      return n - 5;
    } else {
      System.out.println(n);
      return f3(n + 2) + f3(n + 3);
    }
  }

}

