public class FractalTree5 {

  public static void main(String[] args) {
    Turtle t = new Turtle();
    t.delay(0);
    t.left(90);
    //tree(t, 150, 10);
    tree2(t, 150, 10);
  }

  public static void tree(Turtle t, double size, int level) {
    int red = (int)(Math.random() * 256);;
    int green = (int)(Math.random() * 256);
    int blue = (int)(Math.random() * 256);
    t.color(red, green, blue);
    double lengthRandom = Math.max(200, (Math.random() * size));
    if (level == 0) {
        t.forward(lengthRandom);
        t.penup();
        t.backward(lengthRandom);
        t.pendown();
    } else {
        // stem
        t.forward(lengthRandom);
        // first branch
        double tilt1 = Math.random() * 30 - 15;
        t.left(30 + tilt1);
        tree(t, lengthRandom * 0.8, level - 1);
        // second branch
        double tilt2 = Math.random() * 30 - 15;
        t.right(30 + tilt1 + 45 + tilt2);
        tree(t, lengthRandom * 0.6, level - 1);
        t.left(45 + tilt2);
        t.penup();
        t.backward(lengthRandom);
        t.pendown();        
    }
  }


  public static void tree2(Turtle t, double size, int level) {
    int red = (int)(Math.random() * 256);;
    int green = (int)(Math.random() * 256);
    int blue = (int)(Math.random() * 256);
    t.color(red, green, blue);
    if (level == 0) {
        t.forward(size);
        t.penup();
        t.backward(size);
        t.pendown();
    } else {
        // stem
        t.forward(size);
        // first branch
        double tilt1 = Math.random() * 30 - 15;
        t.left(30 + tilt1);
        tree(t, size * 0.8, level - 1);
        // second branch
        double tilt2 = Math.random() * 30 - 15;
        t.right(30 + tilt1 + 45 + tilt2);
        tree(t, size * 0.6, level - 1);
        t.left(45 + tilt2);
        t.penup();
        t.backward(size);
        t.pendown();        
    }
  }



}

