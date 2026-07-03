import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    protected String accountNumber;
    protected double balance;
    protected List<Transaction> transactions;

    /*
    * Constructor for the BankAccount abstract class.
    */
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    /*
    * Deposits money into the account.
    * @param The amount to deposit.
    */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
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
            transactions.add(new Transaction("Withdraw", amount));
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
    * Get the transaction history for this account.
    * @return The list of transactions.
    */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /*
    * Abstract method to display the type of account.
    * Currently, it's either checking or savings.
    */
    public abstract void displayAccountType();
}
