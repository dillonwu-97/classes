public class Types {

  public static void main(String[] args) {
    // Values
    // Every value has a type

    // int
    System.out.println(90); // 90 is a value of type int
    System.out.println(0); // 0 is a value of type int
    System.out.println(-5); // -5 is a value of type int
    
    // double
    System.out.println(5.2); // 5.2 is a value of type double
    System.out.println(1.0); // 1.0 is a value of type double
    System.out.println(-2.5); // -2.5 is a value of type double
    System.out.println(Math.PI); // Math.PI is a value of type double
   
    // String
    System.out.println("hello"); // "hello" is a value of type String
    System.out.println("hey you");
    System.out.println(""); // empty String
    System.out.println("5"); // String representation of 5
    
    // Operators
    // Arithmetic operators: + - * / %

    // Addition
    System.out.println(1 + 2); // int + int = int
    System.out.println(1.2 + 2.3); // double + double = double
    System.out.println(1 + 2.3); // int + double = double
    System.out.println(1.0 + 2); // double + int = double
    
    // Subtraction
    System.out.println(1 - 2); // int - int = int
    System.out.println(2.5 - 1); // double - int = double

    // Multiplication
    System.out.println(2 * 3); // int * int = int
    System.out.println(2 * 3.5); // int * double = double

    // Division
    System.out.println(3 / 2); // int / int = int (integer division)
    System.out.println(5 / 2);
    System.out.println(5.0 / 2.0); // double / double = double (floating point division)
    System.out.println(5.0 / 2); // double / int = double
    System.out.println(5 / 2.0); // int / double = double
    System.out.println(360 / 7);
    System.out.println(360.0 / 7);

    // Modulus
    System.out.println(7 % 2); // 1 (remainder of integer division)
    System.out.println(7 % 3); // 1
    // look at this pattern
    System.out.println(0 % 4); // 0
    System.out.println(1 % 4); // 1
    System.out.println(2 % 4); // 2
    System.out.println(3 % 4); // 3
    System.out.println(4 % 4); // 0
    System.out.println(5 % 4); // 1
    System.out.println(6 % 4); // 2
    System.out.println(7 % 4); // 3
    System.out.println(8 % 4); // 0
    System.out.println(9 % 4); // 1
    System.out.println(10 % 4); // 2
    // 1) if x % n is 0, then x is a multiple of n
    // 2) x % n is always between 0 and n-1 (always smaller than n)

    // String concatenation
    System.out.println("hello" + "everyone"); // "helloeveryone"
    System.out.println("hey" + " you"); // "hey you"
    System.out.println("hello" + "5"); // "hello5"
    System.out.println("hello" + 5); // "hello5" (5 is converted to "5")
    System.out.println(1 + "2"); // "12"
    System.out.println(1 + 2 + "3"); // "33"
    System.out.println("1" + 2 + 3); // "123"

    // Length of a String (method)
    System.out.println("hello".length()); // 5
    System.out.println("".length()); // 0
    System.out.println("hey you".length()); // 7

    // Type conversions
    // int -> double
    System.out.println(1.0 * 5); // 5.0
    System.out.println(0.0 + 5); // 5.0
    System.out.println((double)5); // 5.0 (type cast)

    // double -> int
    System.out.println((int)5.0); // 5
    System.out.println((int)5.7); // 5 
    System.out.println((int)Math.PI); // 3

    // int or double -> String
    System.out.println("" + 5); // "5"
    //System.out.println((String)5); // ERROR
    System.out.println("" + Math.PI); // "3.141592653589793"
    System.out.println("" + "Math.PI"); // "Math.PI"

    // String to int
    System.out.println(Integer.parseInt("5")); // 5
    System.out.println(Integer.parseInt("5") + 2); // 7
    System.out.println(Integer.parseInt("5" + 2)); // 52
    //System.out.println(Integer.parseInt("hello5")); // ERROR

    // String to double
    System.out.println(Double.parseDouble("5.7")); // 5.7
    System.out.println(Double.parseDouble("5")); // 5.0

    // Operator precedence
    // See https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
    // You can modify the precedence using parentheses
    System.out.println(1 + 2 * 3); // 7
    System.out.println((1 + 2) * 3); // 9
    System.out.println(1 + 2 + "3"); // "33"
    System.out.println(1 + (2 + "3")); // "123"
  }

}
