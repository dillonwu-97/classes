public class ShapeQuiz {

    public static void main (String[] args){

        // Test your classes and methods here
        // Example of object creation:
        Operation o1 = new Addition("plus", 2, 3);
        System.out.println(o1.toString());
        o1.printResult();

    }
}


// Operation abstract class: add what is missing based on the diagram given
abstract class Operation {
    // attributes
    protected String name; // add visibilty modifier here

    /**
     * 
     * @return the name of the operation
     */
    public String toString(){
        return name;
    }

    /**
     * 
     * prints the result of any operation
     */
    public abstract void printResult(); 

}

// Addition class: add what is missing based on the diagram given
class Addition extends Operation{

    private int operand1;
    private int operand2;

    // Constructor
    /**
     * creates a new addition operation. 
     * @param n - the name of the operation
     * @param n1 - first operand
     * @param n2 - second operand
     */
    public Addition(String n, int n1, int n2){
        super.name = n;
        operand1 = n1;
        operand2 = n2;
    }

    /**
     * 
     * @return addition information in the following format:
     * operand1 operationName operand2
     */
    public String toString(){
        // to be implemented
        return operand1 +" " +  super.toString() +" "+ operand2;
    }

    /**
     * 
     * prints the result of an addition operation
     * 
     */
    public void printResult() {
        int result = operand1 + operand2;
        System.out.println(result);

    }


}

// Division class: add what is missing based on the diagram given
class Division {

    private double operand1;
    private double operand2;

    /**
     * creates a new division operation. 
     * @param n - the name of the operation
     * @param n1 - first operand
     * @param n2 - second operand
     */
    public Division(String n, double n1, double n2){
        // to be implemented
    }

    /**
     * 
     * @return addition information in the following format:
     * operand1 operationName operand2
     */
    public String toString(){
        // to be implemented
        return null;
    }

    /**
     * 
     * prints the result of a division operation
     * 
     */
    public void printResult() {
        // to be implemented
    }

}

// Concatenation class: add what is missing based on the diagram given
class Concatenation{

    private String operand1;
    private String operand2;


    /**
     * creates a new String concatenation operation. 
     * @param n - the name of the operation
     * @param n1 - first operand
     * @param n2 - second operand
     */
    public Concatenation(String name, String op1, String op2){
        // to be implemented 
    }

    /**
     * 
     * @return addition information in the following format:
     * operand1 operationName operand2
     */
    public String toString(){
        // to be implemented
        return null;
    }

    /**
     * 
     * prints the result of a concatenation operation
     * 
     */
    public void printResult() {
        // to be implemented
    }

}
