/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac Nihon.java
	Execution: 		java Nihon

	This code makes a sun (period)
*/

public class Nihon {

	public static void main (String[] args) {
		Turtle x = new Turtle();
		x.delay(0);
		//sun(x, 100);
		//ray8(x, 100);
		// fractalSun(x, 50, 1);
		// fractalSun(x, 50, 2);
		// fractalSun(x, 50, 3);
		// fractalSun(x, 50, 4);

		fractalSun(x, 25, 5); // using this code so that it runs quicker
		
		// but you can also try it with different dimensions.
		//fractalSun(x, 50, 5);
		
		darkNight(x, 50); // for the dark background <-------
		// YOU NEED TO GIVE THIS A SECOND TO COVER THE SCREEN

		x.hideturtle();
	}

	// using penup is to not make extra lines, making filling the entire circle easier
	public static void ray (Turtle t, double r) {
		t.penup();
		t.forward(3* r);
		t.pendown();
	}

	// This creates one of the suns.
	public static void sun(Turtle t, double r) {
		t.left(90);
		t.penup();
		t.forward(r);
		t.right(90);
		t.pendown();
		for (int i = 0; i < 10000; i++) {
			t.forward(2*Math.PI*r/10000);
			t.right(360.0/10000);
		}
		t.left(90);
		t.penup();
		t.backward(r);
		t.right(90);
		t.pendown();
	}

	// for the dark background
	public static void darkNight (Turtle t, double r) {
		t.color("black");
		t.penup();
		t.left(45);
		t.forward(6* r + 1);
		t.pendown();
		t.fill();
		t.penup();
		t.backward(6* r+1);
		t.right(45);
	}

	// this makes the fractal
	public static void fractalSun(Turtle t, double r, int level) {
 		String[] colors = new String[6];
 		colors[0] = "green";
 		colors[1] = "pink";
 		colors[2] = "blue";
 		colors[3] = "orange";
 		colors[4] = "yellow";
 		colors[5] = "red";

		if (level == 0) {
			sun(t,r);
			// fill sequence
			t.color(colors[0]);
			t.penup();
			t.forward(r/2);
			t.pendown();
			t.fill();
			t.penup();
			t.backward(r/2);
			t.pendown();
			//ending fill sequence
		} else {
			t.color(colors[level]);
			sun(t, r);
			// to fill the circle with a color
			t.penup();
			t.forward(r/2);
			t.pendown();
			t.fill();
			t.penup();
			t.backward(r/2);
			t.pendown();
			// Ending fill sequence
			for (int i = 0; i < 8; i++) {
				ray(t, r);
				fractalSun(t, r/4, level - 1);
				t.backward(r);
				t.color(colors[level]);
				t.backward(r * 2);
				t.left(360.0 / 8.0);
			}
		}

	}
}