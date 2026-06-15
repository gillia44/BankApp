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
    * @param The customer to add to the bank.
    */
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    /*
    * Looks up a customer in the bank.
    * @param The customer to search for.
    * @return The customer 
    */
    public Customer findCustomer(int customerId) {
        return customers.get(customerId);
    }

    /*
    * Displays a list of all customers.
    */
    public void displayAllCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println(customer);
            System.out.println("----------------");
        }
    }

    /*
    * Transfer money from a checking account to a savings account.
    * @param  The customer performing the transfer.
    * @param The amount to transfer.
    * @return If the transfer was successful.
    */
    public boolean transferCheckingToSavings(Customer customer, double amount) {

        CheckingAccount checking = customer.getCheckingAccount();
        SavingsAccount savings = customer.getSavingsAccount();

        if (checking.withdraw(amount)) {
            savings.deposit(amount);
            return true;
        }

        return false;
    }

    /*
    * Transfer money from a savings account to a checking account.
    * @param  The customer performing the transfer.
    * @param The amount to transfer.
    * @return If the transfer was successful.
    */
    public boolean transferSavingsToChecking(Customer customer, double amount) {

        SavingsAccount savings = customer.getSavingsAccount();
        CheckingAccount checking = customer.getCheckingAccount();

        if (savings.withdraw(amount)) {
            checking.deposit(amount);
            return true;
        }

        return false;
    }
}