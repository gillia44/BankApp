public class SavingsAccount extends BankAccount {

    private double interestRate;

    /*
    * Constructor for the SavingsAccount class.
    */
    public SavingsAccount(String accountNumber,
                          double balance,
                          double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    /*
    * Applies interest to the total balance of the account.
    */
    public void applyInterest() {
        balance += balance * interestRate;
    }

    /*
    * Displays the account type.
    */
    @Override
    public void displayAccountType() {
        System.out.println("Savings Account");
    }
}