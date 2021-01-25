/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       February 14, 2018

   Compilation:   javac Circle.java
   Execution:     java Circle

   This creates a circle and gives it some dimensions. 
*/

//A class for a circle. 
public class Circle {  // Save as "Circle.java"
   protected double radius;//the radius of the circle.
   protected double x; //the x coordinate of the circle.
   protected double y;// the y coordinate of the circle. 
   // The default constructor with no argument.
   // It sets the radius.
   public Circle() {
      radius = 1.0;
      x = 0.0;
      y = 0.0;
   }
   
   // 2nd constructor with given radius, but origin default
   public Circle(double r) {
      radius = r;
      x = 0.0;
      y = 0.0;
      
   }
   
   //You should complete this next constructor. "r" is the radius name, "ex" should be the x coordinate, and "why" should be the y coordinate. 

   public Circle(double r, double ex, double why){
      radius = r;
      x = ex;
      y = why;
   }

   // A public method for retrieving the radius
   public double getRadius() {
     return radius; 
   }
   
   // A public method for computing the area of circle
   public double getArea() {
      return radius*radius*Math.PI;
   }

   //A public method to compute the circumference of the circle.
   public double getCircumference(){
      return Math.PI*2*radius; 
   }

   //A public method that compares the sizes of two circles. CircleA.isBiggerThan(CircleB) should return true if 
   //circleA has a larger area than circle B, false otherwise. 
   //The method is named, but you should fill in the signature. 
   public boolean isBiggerThan(Circle circleA, Circle circleB){
      if (circleA.getArea() > circleB.getArea()) {
         return true;
      } else {
         return false;
      }
   }

   //A public method that takes as input an x coordinate (as a double) and a y coordinate (a double), and 
   //returns true if the (x,y) coordinate 
   //is inside the circle, and false otherwise.  
   public boolean containsPoint(double x, double y){
      double distance = Math.sqrt((this.x - x)*(this.x - x) + (this.y - y)*(this.y - y));
      if (distance < radius) {
         return true;
      } else {
         return false;
      }
   }

   // helper method to get the coordinates of the circle
   public double getX() {
      return x;
   }
      
   public double getY() {
      return y;
   }

   //Sets the radius
   public void setRadius(double r) {
      radius = r;
   }

   public void setCenter(double ex, double why) {
      x = ex;
      y = why;
   }

   public String toString() {
      return "This circle is centered at point (" + x + "," + y + ") with radius " + radius + ".";
   }
}