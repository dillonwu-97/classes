public class FractalCross {

    public static void f(Turtle t, double x, int n) {
        if (n == 0) {
            return;
        } else {
            for (int i = 0; i < 4; i++) {
                t.forward(x);
                t.left(45);

                f(t, x * 0.4, n -1);
                t.right(45);
                t.backward(x);
                t.left(90);
            }
            t.left(45);
            f(t, x * 0.4, n-1);
            t.right(45);
        }
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        t.delay(1);
        f(t, 100, 4);
    }
}