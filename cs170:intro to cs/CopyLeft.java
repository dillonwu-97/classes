/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac CopyLeft.java
	Execution: 		java CopyLeft

	This code creates recursive Mickey Mice. 
*/

public class CopyLeft {
	
	// This makes one of Micky's faces using circles and semicircles. 
	public static void mickeyFace(Turtle t, double r) {
		circle(t, r);
		circle(t, r/5);
		semiCircle(t, 4*r/5);
		t.penup();
		t.left(45);
		t.forward(3*r/5);
		t.right(45);
		t.pendown();
		circle(t, r/6);
		t.left(45);
		t.penup();
		t.backward(3*r/5);
		t.left(90);
		t.forward(3*r/5);
		t.right(135);
		t.pendown();
		circle(t, r/6);
		t.left(135);
		t.penup();
		t.backward(3*r/5);
		t.right(135);
		t.pendown();
		
	}

	// This makes a circle 
	// Pretty self explanatory 
	public static void circle(Turtle t, double r) {
		t.left(90);
		t.penup();
		t.forward(r);
		t.right(90);
		t.pendown();
		for(int i=0; i<10000; i++) { // To approximate a circle
			t.forward(2*Math.PI*r/10000);
			t.right(360.0/10000);
		}
		t.left(90);
		t.penup();
		t.backward(r);
		t.right(90);
		t.pendown();
	}

	// Wow, I wonder what this makes
	public static void semiCircle(Turtle t, double r) {
		t.penup();
		t.backward(r);
		t.pendown();
		t.right(90);
		for(int i=0; i<5000; i++) {
			t.forward(Math.PI*r/5000);
			t.left(180.0/5000);
		}
		t.right(90);
		t.penup();
		t.backward(r);
		t.pendown();
	}
	
	public static void fractalMickeyMouse(Turtle t, double r, int level) {
		if (level == 0) {
			mickeyFace(t, r);
		} else {
			mickeyFace(t, r);
			t.penup();
			t.left(45);
			t.forward(r);
			t.forward(r/2);
			t.right(45);
			t.pendown();
			fractalMickeyMouse(t, r/2, level - 1);
			

			t.penup();
			t.left(45.0);
			t.backward(r/2);
			t.backward(r);
			t.left(90);
			t.forward(r);
			t.forward(r/2);
			t.right(135);
			t.pendown();
			fractalMickeyMouse(t, r/2, level - 1);

			t.penup();
			t.left(135);
			t.backward(r/2);
			t.backward(r);
			t.right(135);
			t.pendown();


		}
	}


	public static void main (String[] args) {
		Turtle x = new Turtle();
		x.delay(0);
		//mickeyFace(x, 100);
		fractalMickeyMouse(x,100,6);
		//semiCircle(x, 100);
	}































}