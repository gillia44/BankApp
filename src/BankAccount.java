public abstract class BankAccount {
    protected String accountNumber;
    protected double balance;

    /*
    * Constructor for the BankAccount abstract class.
    */
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    /*
    * Deposits money into the account.
    * @param The amount to deposit.
    */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /*
    * Withdraws money from the account.
    * @param The amount to withdraw.
    * @return Whether or not the withdraw was successful.
    */
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /*
    * Get the balance of the account.
    * @return The amount in the account.
    */
    public double getBalance() {
        return balance;
    }

    /*
    * Get the account number.
    * @return The account number.
    */
    public String getAccountNumber() {
        return accountNumber;
    }

    /*
    * Abstract method to display the type of account.
    * Currently, it's either checking or savings.
    */
    public abstract void displayAccountType();
}