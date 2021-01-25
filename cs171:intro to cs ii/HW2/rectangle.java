/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       February 14, 2018

   Compilation:   javac Rectangle.java
   Execution:     java Rectangle

   This creates a rectangle and gives it some dimensions. 
*/

//A class for a rectangle.

public class Rectangle{

	 // private instance variable, not accessible from outside this class

	private double L;//the length of the rectangle
   private double H; //the height of the rectangle
   private double x;// The x coordinate of the  bottom left corner of the rectangle
   private double y; // the y coordinate of the bottom left corner of the rectangle


   //You should write a basic constructor that creates a rectangle with side lengths 1 whose bottom left corner is at (0.0,0.0)
   public Rectangle () {
      L = 1.0;
      H = 1.0;
      x = 0.0;
      y = 0.0;
   }

   //A constructor that takes four inputs named Ell, Eich, Ex, and Why for L, H, x, and y respectively. 

   public Rectangle (double Ell, double Eich, double Ex, double Why) {
      L = Ell;
      H = Eich;
      x = Ex;
      y = Why;
   }

   //getLength returns the length.
   public double getLength() {
      return L;
   }
   
   //getHeight returns the height. 
   public double getHeight() {
      return H;
   }

   //setLength takes as input a double called Ell which sets the length of the rectangle.
   public void setLength(double Ell) {
      L = Ell;
   }

   //etHeight takes as input a double called Eich which sets the height of the rectangle.
   public void setHeight(double Eich) {
      H = Eich;
   }

   // computes the perimeter of the rectangle
   public double perimeter () {
      double result = 2*L + 2*H;
      return result;
   }

   //computes the area of the rectangle
   public double area() {
      return L*H;
   }

}