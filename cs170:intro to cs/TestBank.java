public class TestBank {
    
  public static void main(String[] args) {
    BankAccount a1 = new BankAccount("Amy");
    BankAccount a2 = new BankAccount("Bob");
    System.out.println("a1 owner is " + a1.owner);
    System.out.println("a1 balance is " + a1.balance);
    System.out.println("a2 owner is " + a2.owner);
    System.out.println("a2 balance is " + a2.balance);
    System.out.println("----------");

    a1.deposit(1000);
    System.out.println("a1 owner is " + a1.owner);
    System.out.println("a1 balance is " + a1.balance);
    System.out.println("a2 owner is " + a2.owner);
    System.out.println("a2 balance is " + a2.balance);
    System.out.println("----------");

    System.out.println(a1.withdraw(300));
    System.out.println("a1 owner is " + a1.owner);
    System.out.println("a1 balance is " + a1.balance);
    System.out.println("----------");

    System.out.println(a1.withdraw(1500));
    System.out.println("a1 owner is " + a1.owner);
    System.out.println("a1 balance is " + a1.balance);
    System.out.println("----------");

    // printing an object
    System.out.println(a1);
    System.out.println(a2);
    System.out.println("----------");

    // comparing two objects
    BankAccount a3 = new BankAccount("Bob");
    System.out.println("a1: " + a1);
    System.out.println("a2: " + a2);
    System.out.println("a3: " + a3);

    // == tests for IDENTITY, not EQUALITY
    System.out.println("a1 == a2 is " + (a1 == a2)); // false
    System.out.println("a1 == a3 is " + (a1 == a3)); // false
    System.out.println("a2 == a3 is " + (a2 == a3)); // false !!!
    System.out.println("a1 == a1 is " + (a1 == a1)); // true

    System.out.println("a1.equals(a2) is " + a1.equals(a2)); // false
    System.out.println("a1.equals(a3) is " + a1.equals(a3)); // false
    System.out.println("a2.equals(a3) is " + a2.equals(a3)); // true
    System.out.println("a1.equals(a1) is " + a1.equals(a1)); // true
    
  }

}
