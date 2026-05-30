public class Main {

    public static void main(String[] args) {
        // Create bank
        Bank bank = new Bank();

        // Create new user
        User user = new User(
                1001,
                "John Smith",
                "123 Main Street",
                "555-1234"
        );

        // Create checking and savings accounts
        CheckingAccount checking = new CheckingAccount("CHK001", 1000);
        SavingsAccount savings = new SavingsAccount("SAV001", 5000, 0.03);

        // Set the checking and savings account for the user
        user.setCheckingAccount(checking);
        user.setSavingsAccount(savings);

        // Add the customer to the bank
        bank.addCustomer(user);

        // Display information
        System.out.println("Before Transfer:");
        System.out.println("Checking: $" + String.format("%.2f", checking.getBalance()));
        System.out.println("Savings: $" + String.format("%.2f", savings.getBalance()));

        // Make a transfer
        bank.transferCheckingToSavings(user, 250);

        // Display information
        System.out.println("\nAfter Transfer:");
        System.out.println("Checking: $" + String.format("%.2f", checking.getBalance()));
        System.out.println("Savings: $" + String.format("%.2f", savings.getBalance()));

        // Apply interest to the savings account
        savings.applyInterest();

        // Display information
        System.out.println("\nAfter Interest:");
        System.out.println("Savings: $" + String.format("%.2f", savings.getBalance()));
    }
}