/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac Nest.java
	Execution: 		java Nest

	This code makes a sun (period)
*/

public class TheFractal {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(0);
		honeycomb(minion, 50, 3);
	}

	public static void honey (Turtle t, double length, int level) {
    if (level == 0) {
        t.forward(length);
    } else {
        honey(t, length / 3, level - 1);
        t.left(60);
        honey(t, length / 3, level - 1);
        t.right(60);
        honey(t, length / 3, level - 1);
        t.right(60);
        honey(t, length / 3, level - 1);
        t.left(60);
        honey(t, length / 3, level - 1);
    }
  }

  public static void honeycomb(Turtle t, double length, int level) {
    for (int i = 0; i < 6; i++) {
        honey(t, length, level);
        t.right(360/6);
    }
  }
 
}