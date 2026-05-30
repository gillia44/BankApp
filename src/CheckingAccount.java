public class CheckingAccount extends BankAccount {

    /*
    * Constructor for the CheckingAccount class.
    */
    public CheckingAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }
    
    /*
    * Displays the account type.
    */
    @Override
    public void displayAccountType() {
        System.out.println("Checking Account");
    }
}