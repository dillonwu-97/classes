public class BankAccount {
 
  // Attributes (or fields)
  // Describe what an object HAS
  String owner;

  double balance;

  // Constructor
  // Initializes the values of the attributes of a newly created object
  public BankAccount(String owner) {
    owner = this.owner;
    balance = 0;
  }

  // Methods
  // Describe what an object can DO
  public void deposit(double money) {
    balance += money;
  }

  // If there is enough money in the account,
  // withdraws that amount and returns it.
  // Otherwise withdraws only the amount available
  // and returns it 
  public double withdraw(double money) {
    if (balance >= money) {
      balance -= money;
      return money;
    } else {
      money = balance;
      balance = 0;
      return money;
    }
  }

  // The method toString is automatically called when it is
  // necessary to represent an object as a String
  // (e.g., to print it, or to concatenate it with another String)
  public String toString() {
    return "Account owner: " + owner + ", balance: " + balance;
  }

  // The method equals must be implemented if we need to compare
  // two objects for equality (same values of their attributes)
  public boolean equals(BankAccount other) {
    return this.owner.equals(other.owner) && this.balance == other.balance;
  }

}
