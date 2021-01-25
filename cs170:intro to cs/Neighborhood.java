/*
	Author: 		Dillon Wu
	Written: 		September 6, 2017

	Compilation: 	javac Neighborhood.java
	Execution: 		java Neighborhood

	Makes the turtle draw a house and a number of subsequent houses
	each smaller than the last.
*/

public class Neighborhood {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(0);
		// houseline(minion, 20);			//  Test case 1
		houseline(minion, 4); 			//  Test case 2
	}

	//  I had to add decimals because java reads 1/2 as 0.
	//	Originally, the formula was 1 / (1*i).
	
	public static void houseline (Turtle t, int numHouses) {
		for (int i = 1; i < numHouses + 1; i++) {
			house(t, 1.0 / (1.0*i)); 
			t.penup();
			t.forward(100.0 * (1.0 / (1.0*i)));
			t.pendown();
		}
		reverse(t, numHouses);
	}

	//	This returns the turtle to starting positions.

	public static void reverse (Turtle t, int numHouses) {
		for (int i = numHouses; i > 0; i--) {
			t.penup();
			t.backward(100 * (1.0 / (1.0*i)));
		}
		t.pendown();
	}

/*
	The code below is used to create a house.
	It was taken from the lecture on August. 
	I have modified so that it now includes a way to scale the dimensions. 
	The biggest house must have a scale of 1, and then it will subsequently scale 
	smaller through the formula 1/(1+n).
*/	

	public static void house(Turtle t, double scale) {
    	front(t, scale);
    	t.left(90);
    	t.forward(scale * 80);
    	t.right(90);
    	top(t, scale);
    	t.left(90);
    	t.backward(scale * 80);
    	t.right(90);
  	}

  	public static void front(Turtle t, double scale) {
    	walls(t, scale);
    	t.forward(scale * 30);
    	door(t, scale);
    	t.penup();
	    t.left(90);
	    t.forward(scale * 50);
	    t.right(90);
	    t.backward(scale * 20);
	    t.pendown();
	    windows(t, scale);
	    t.penup();
	    t.backward(scale * 10);
	    t.left(90);
	    t.backward(scale * 50);
	    t.right(90);
	    t.pendown();
  	}

  public static void top(Turtle t, double scale) {
	    roof(t, scale);
	    t.penup();
	    t.forward(scale * 10);
	    t.left(90);
	    t.forward(scale * 10);
	    t.right(90);
	    t.pendown();
	    chimney(t, scale);
	    t.penup();
	    t.backward(scale * 10);
	    t.right(90);
	    t.forward(scale * 10);
	    t.left(90);
	    t.pendown();
  	}

  public static void walls(Turtle t, double scale) {
    	square(t, scale * 80);
  	}

  public static void windows(Turtle t, double scale) {
	    square(t, scale * 20);
	    t.penup();
	    t.forward(scale * 40);
	    t.pendown();
	    square(t, scale * 20);
	    t.penup();
	    t.backward(scale * 40);
	    t.pendown();        
 	}

  public static void door(Turtle t, double scale) {
	    for (int i = 0; i < 2; i++) {
	      t.forward(scale * 20);
	      t.left(90);
	      t.forward(scale * 30);
	      t.left(90);
    	}
  	}

  public static void roof(Turtle t, double scale) {
	    t.forward(scale * 80);
	    t.left(135);
	    t.forward(scale * 40 * Math.sqrt(2));
	    t.left(90);
	    t.forward(scale * 40 * Math.sqrt(2));
	    t.left(135);
  	}

  public static void chimney(Turtle t, double scale) {
	    t.left(90);
	    t.forward(scale * 20);
	    t.right(90);
	    t.forward(scale * 10);
	    t.right(90);
	    t.forward(scale * 10);
	    t.backward(scale * 10);
	    t.left(90);
	    t.backward(scale * 10);
	    t.left(90);
	    t.backward(scale * 20);
	    t.right(90);
  	}

  public static void square(Turtle t, double x) {
	    for (int i = 0; i < 4; i++) {
	      t.forward(x);
	      t.left(90);
    	}
  }

}