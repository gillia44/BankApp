import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<Integer, Customer> customers;

    /*
    * Constructor for the Bank class. This class holds the customers, and provides the
    * functionaity to transfer between a checking and savings account
    */
    public Bank() {
        customers = new HashMap<>();
    }

    /*
    * Add a customer to the bank.
    * @param The user to add to the bank.
    */
    public void addCustomer(Customer user) {
        customers.put(user.getCustomerId(), user);
    }

    /*
    * Looks up a customer in the bank.
    * @param The user to search for.
    * @return The user 
    */
    public Customer findCustomer(int customerId) {
        return customers.get(customerId);
    }

    /*
    * Displays a list of all customers.
    */
    public void displayAllCustomers() {
        for (Customer user : customers.values()) {
            System.out.println(user);
            System.out.println("----------------");
        }
    }

    /*
    * Transfer money from a checking account to a savings account.
    * @param  The user performing the transfer.
    * @param The amount to transfer.
    * @return If the transfer was successful.
    */
    public boolean transferCheckingToSavings(Customer user, double amount) {

        CheckingAccount checking = user.getCheckingAccount();
        SavingsAccount savings = user.getSavingsAccount();

        if (checking.withdraw(amount)) {
            savings.deposit(amount);
            return true;
        }

        return false;
    }

    /*
    * Transfer money from a savings account to a checking account.
    * @param  The user performing the transfer.
    * @param The amount to transfer.
    * @return If the transfer was successful.
    */
    public boolean transferSavingsToChecking(Customer user, double amount) {

        SavingsAccount savings = user.getSavingsAccount();
        CheckingAccount checking = user.getCheckingAccount();

        if (savings.withdraw(amount)) {
            checking.deposit(amount);
            return true;
        }

        return false;
    }
}