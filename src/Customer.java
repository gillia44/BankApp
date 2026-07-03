public class Customer {
    private String name;
    private String address;
    private String phoneNumber;
    private int customerId;

    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;

    /*
    * Constructor for the Customer class.
    * Holds information about a particular customer in the Bank.
    */
    public Customer(int customerId, String name, String address, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /*
    * Gets the customer's ID.
    * @return The customer's ID.
    */
    public int getCustomerId() {
        return customerId;
    }

    /*
    * Gets the customer's name.
    * @return The customer's name.
    */
    public String getName() {
        return name;
    }

    /*
    * Gets the customer's address.
    * @return The customer's address.
    */
    public String getAddress() {
        return address;
    }

    /*
    * Gets the customer's phone number.
    * @return The customer's phone number.
    */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /*
    * Updates the customer's name.
    * @param The new name.
    */
    public void setName(String name) {
        this.name = name;
    }

    /*
    * Updates the customer's address.
    * @param The new address.
    */
    public void setAddress(String address) {
        this.address = address;
    }

    /*
    * Updates the customer's phone number.
    * @param The new phone number.
    */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*
    * Gets the customer's checking account.
    * @return The customer's checking account.
    */
    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    /*
    * Gets the customer's savings account.
    * @return The customer's savings account.
    */
    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    /*
    * Set the checking account for a customer.
    * @param The checking account for the customer.
    */
    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    /*
    * Set the savings account for a customer.
    * @param The savings account for the customer.
    */
    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    /*
    * Handy method to display output.
    */
    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                "\nName: " + name +
                "\nAddress: " + address +
                "\nPhone: " + phoneNumber;
    }
}
