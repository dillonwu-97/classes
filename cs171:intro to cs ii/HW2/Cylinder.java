/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       February 14, 2018

   Compilation:   javac Cylinder.java
   Execution:     java Cylinder

   This creates a cylinder and gives it some dimensions. 
*/


public class Cylinder extends Circle {
	
	//private double x; // this gives the x coordinate of the circle
	//private double y; // y coordinate of the circle
	//private double radius;
	// The above dimensions are not needed since they already exist in circle. 
	private double z; // z coordinate of the circle 
	private double height; // the height of the cylinder
	
	//Two constructors: one that takes no input and has the center of the base at (0.0,0.0,0.0), 
	//Radius of base is 1, height is 1 and the height is 1 
	public Cylinder () {
		x = 0.0;
		y = 0.0;
		z = 0.0;
		height = 1.0;
		radius = 1.0;
	}

	//This takes as input the x and y coordinates along with the radius and the height. 
	public Cylinder (double x, double y, double r, double h) {
		this.x = x;
		this.y = y;
		z = 0.0;
		radius = r;
		height = h;
	}

	// returns volume and overrides the getArea method in Circle
	public double getArea() {
      return radius*radius*Math.PI*height;
    }

    // gets height 
    public double getHeight() {
    	return height;
    }

    // sets height
    public void setHeight(double h) {
    	height = h;
    }


}