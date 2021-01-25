public class FractalCircles {
    
    public static void f(Turtle t, double r, String color, int level) {
        if (level == 0) {
            return;
        } else {
            t.color(color);
           
            circle(t, r);
            f(t, 0.5 * r, "black", level - 1);
            t.penup();
            t.backward(r);
            t.pendown();
            f(t, 0.5 * r, "orange", level - 1);
            t.penup();
            t.forward(2 * r);
            t.pendown();
            f(t, 0.5 * r, "orange", level - 1);
            t.penup();
            t.backward(r);
            t.left(90);
            t.forward(r);
            t.pendown();
            f(t, 0.5 * r, "red", level - 1);
            t.penup();
            t.backward(2 * r);
            t.pendown();
            f(t, 0.5 * r, "red", level - 1);
            t.penup();
            t.forward(r);
            t.right(90);
            t.pendown();
        }
    }

    // draws a circle of radius r centered on the turtle's location
    public static void circle(Turtle t, double r) {
        int n = (int)(0.9 * Math.abs(r) + 3);
        double side = 2 * r * Math.sin(Math.PI / n);
        t.penup();
        t.backward(r);
        t.right(90);
        t.backward(side / 2);
        t.pendown();
        for (int i = 0; i < n; i++) {
            t.forward(side);
            t.left(360.0 / n);
        }
        t.penup();
        t.forward(side / 2);
        t.left(90);
        t.forward(r);
        t.pendown();
    }

    public static void main(String[] args) {
        Turtle t = new Turtle();
        t.delay(1);
        f(t, 100, "black", 4);
    }
}