/*
	Author: 		Dillon Wu
	Written: 		November 6, 2017

	Compilation: 	javac Nest.java
	Execution: 		java Nest

	This code makes a bee's nest (kind of). 
	It actually makes a recursive hexagon, with hexagonal sides. 
*/

public class Nest {
	
	public static void main (String[] args) {
		Turtle minion = new Turtle();
		minion.delay(0);
		//honeycomb(minion, 50, 2);
		//honeycomb(minion, 10, 5); // I needed to use 10, or else it wouldn't fit in the screen; 
		// it actually still doesn't fit, but is a bit better 

		complex(minion,100,4);
		
	}

	// To make six sides of the nest, I need to run the method six times. 
 	public static void honeycomb(Turtle t, double length, int level) {
    	for (int i = 0; i < 6; i++) {
        	honey(t, length, level);
        	t.right(360.0/6);
    	}
  	}


	// This is one side of the comb
	public static void honey (Turtle t, double length, int level) {
    	int c1 = (int)(Math.random() * 256); // To randomize the colors
    	int c2 = (int)(Math.random() * 256); 
    	int c3 = (int)(Math.random() * 256);
    	t.color(c1, c2, c3);

    	if (level == 0) {
        	t.forward(length);
    	} else {
        	honey(t, length / 2, level - 1);
        	t.left(60.0);
        	honey(t, length / 2, level - 1);
        	t.right(60.0);
        	honey(t, length / 2, level - 1);
        	t.right(60.0);
        	honey(t, length / 2, level - 1);
        	t.left(60.0);
        	honey(t, length / 2, level - 1);
    	}
 	}


 	// adds a layer of randomness by changing the length of the side
 	public static void complex (Turtle t, double length, int level) {
    	int c1 = (int)(Math.random() * 256); // To randomize the colors
    	int c2 = (int)(Math.random() * 256); 
    	int c3 = (int)(Math.random() * 256);
    	t.color(c1, c2, c3);

    	double rLength = length*Math.random();
    	if (level == 0) {
        	t.forward(rLength);
    	} else {
        	honey(t, rLength / 2, level - 1);
        	t.left(60.0);
        	honey(t, rLength / 2, level - 1);
        	t.right(60.0);
        	honey(t, rLength / 2, level - 1);
        	t.right(60.0);
        	honey(t, rLength / 2, level - 1);
        	t.left(60.0);
        	honey(t, rLength / 2, level - 1);
    	}
 	}

 	

 
}