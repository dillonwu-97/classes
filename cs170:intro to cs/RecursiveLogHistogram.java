/*

Write a recursive method logHistogram(int n) that takes an integer n >= 0, and
returns a string representing a bar composed with equal ("=") characters. The
number of characters corresponds to the floor of the base 2 logarithm of n.

Examples:
logHistogram(0) returns ""
logHistogram(1) returns ""
logHistogram(2) returns "="
logHistogram(8) returns "==="
logHistogram(7) returns "=="
logHistogram(16) returns "===="

*/

public class RecursiveLogHistogram {

    // write your method here
    
    public static void main(String[] args) {
        // test your method here
        System.out.println(logHistogram(0));
        System.out.println(logHistogram(1));
        System.out.println(logHistogram(2));
        System.out.println(logHistogram(8));
        System.out.println(logHistogram(7));
        System.out.println(logHistogram(16));
    }

    public static String logHistogram(int n) {
    	if (n == 0 || n == 1) {
    		return "";
    	} else {
    		return logHistogram(n/2) + "=";
    	}
    }

}