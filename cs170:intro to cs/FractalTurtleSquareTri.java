// 1 + 4^4

public class FractalTurtleSquareTri {

    public static void f(Turtle t, double x, int n) {
        if (n == 0) {
            t.forward(x);
        } else {
            t.forward(x * 0.25);
            t.left(90);
            for (int i = 0; i < 3; i++) {
                f(t, x * 0.5, n - 1);
                t.right(120);
            }
            t.right(90);
            t.forward(x * 0.75);
        }
    }
    
    public static void main(String[] args) {
        Turtle t = new Turtle();
        t.delay(1);
        f(t, 400, 4);
    }
}