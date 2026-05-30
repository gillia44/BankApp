import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private double amount;
    private LocalDateTime timestamp;

    /*
    * Constructor for the Transaction class.
    * This is not currently used, but will later provide account
    * transaction information.
    */
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return timestamp + " | " + type + " | $" + amount;
    }
}