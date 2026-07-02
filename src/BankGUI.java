import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Swing front end for the Bank / Customer / BankAccount model classes.
 * Lets the user create customers, open checking and savings accounts,
 * make deposits and withdrawals, and transfer funds between accounts.
 */
public class BankGUI extends JFrame {

    private final Bank bank = new Bank();
    private int nextCustomerId = 1001;

    private final DefaultListModel<Customer> customerListModel = new DefaultListModel<>();
    private final JList<Customer> customerList = new JList<>(customerListModel);

    // Profile tab
    private final JTextField nameField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JLabel idValueLabel = new JLabel("-");

    // Checking tab
    private final JLabel checkingBalanceLabel = new JLabel("No account open");
    private final JTextField checkingOpenField = new JTextField();
    private final JButton checkingOpenButton = new JButton("Open Checking Account");
    private final JTextField checkingAmountField = new JTextField();
    private final JButton checkingDepositButton = new JButton("Deposit");
    private final JButton checkingWithdrawButton = new JButton("Withdraw");
    private final JTextArea checkingHistoryArea = new JTextArea();

    // Savings tab
    private final JLabel savingsBalanceLabel = new JLabel("No account open");
    private final JTextField savingsOpenField = new JTextField();
    private final JTextField savingsRateField = new JTextField("0.03");
    private final JButton savingsOpenButton = new JButton("Open Savings Account");
    private final JTextField savingsAmountField = new JTextField();
    private final JButton savingsDepositButton = new JButton("Deposit");
    private final JButton savingsWithdrawButton = new JButton("Withdraw");
    private final JButton savingsInterestButton = new JButton("Apply Interest");
    private final JTextArea savingsHistoryArea = new JTextArea();

    // Transfer tab
    private final JTextField transferAmountField = new JTextField();
    private final JButton toSavingsButton = new JButton("Checking -> Savings");
    private final JButton toCheckingButton = new JButton("Savings -> Checking");
    private final JLabel transferStatusLabel = new JLabel(" ");

    private final JTabbedPane detailTabs = new JTabbedPane();

    public BankGUI() {
        super("Banking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildCustomerListPanel(), BorderLayout.WEST);
        add(buildDetailPanel(), BorderLayout.CENTER);

        setDetailEnabled(false);
        customerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshDetailPanel();
            }
        });
    }

    // ---------- Left panel: customer list ----------

    private JPanel buildCustomerListPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 5));
        panel.setPreferredSize(new Dimension(260, 0));

        JLabel header = new JLabel("Customers");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(header, BorderLayout.NORTH);

        customerList.setCellRenderer((list, customer, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(customer.getCustomerId() + " - " + customer.getName());
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(4, 6, 4, 6));
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            return label;
        });
        panel.add(new JScrollPane(customerList), BorderLayout.CENTER);

        JButton newCustomerButton = new JButton("New Customer");
        newCustomerButton.addActionListener(this::onNewCustomer);
        panel.add(newCustomerButton, BorderLayout.SOUTH);

        return panel;
    }

    private void onNewCustomer(ActionEvent e) {
        JTextField nameF = new JTextField();
        JTextField addressF = new JTextField();
        JTextField phoneF = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 1, 4, 4));
        form.add(new JLabel("Name:"));
        form.add(nameF);
        form.add(new JLabel("Address:"));
        form.add(addressF);
        form.add(new JLabel("Phone Number:"));
        form.add(phoneF);

        int result = JOptionPane.showConfirmDialog(this, form, "New Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameF.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required.", "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = nextCustomerId++;
            Customer customer = new Customer(id, name, addressF.getText().trim(), phoneF.getText().trim());
            bank.addCustomer(customer);
            customerListModel.addElement(customer);
            customerList.setSelectedValue(customer, true);
        }
    }

    // ---------- Right panel: detail tabs ----------

    private JPanel buildDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 5, 10, 10));

        detailTabs.addTab("Profile", buildProfileTab());
        detailTabs.addTab("Checking Account", buildCheckingTab());
        detailTabs.addTab("Savings Account", buildSavingsTab());
        detailTabs.addTab("Transfer Funds", buildTransferTab());

        panel.add(detailTabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildProfileTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, c, 0, "Customer ID:", idValueLabel);
        addRow(panel, c, 1, "Name:", nameField);
        addRow(panel, c, 2, "Address:", addressField);
        addRow(panel, c, 3, "Phone Number:", phoneField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            Customer customer = getSelectedCustomer();
            if (customer == null) return;
            customer.setName(nameField.getText().trim());
            customer.setAddress(addressField.getText().trim());
            customer.setPhoneNumber(phoneField.getText().trim());
            customerList.repaint();
            JOptionPane.showMessageDialog(this, "Customer profile updated.");
        });
        c.gridx = 1;
        c.gridy = 4;
        panel.add(saveButton, c);

        return panel;
    }

    private JPanel buildCheckingTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, c, 0, "Balance:", checkingBalanceLabel);

        addRow(panel, c, 1, "Opening Balance:", checkingOpenField);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(checkingOpenButton, c);
        checkingOpenButton.addActionListener(e -> openAccount(false));

        addRow(panel, c, 3, "Amount:", checkingAmountField);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(checkingDepositButton);
        buttons.add(checkingWithdrawButton);
        c.gridx = 1;
        c.gridy = 4;
        panel.add(buttons, c);
        checkingDepositButton.addActionListener(e -> depositOrWithdraw(false, true));
        checkingWithdrawButton.addActionListener(e -> depositOrWithdraw(false, false));

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(new JLabel("Transaction History:"), c);

        checkingHistoryArea.setEditable(false);
        checkingHistoryArea.setRows(10);
        JScrollPane scroll = new JScrollPane(checkingHistoryArea);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scroll, c);

        return panel;
    }

    private JPanel buildSavingsTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, c, 0, "Balance:", savingsBalanceLabel);

        addRow(panel, c, 1, "Opening Balance:", savingsOpenField);
        addRow(panel, c, 2, "Interest Rate (e.g. 0.03):", savingsRateField);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(savingsOpenButton, c);
        savingsOpenButton.addActionListener(e -> openAccount(true));

        addRow(panel, c, 4, "Amount:", savingsAmountField);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(savingsDepositButton);
        buttons.add(savingsWithdrawButton);
        buttons.add(savingsInterestButton);
        c.gridx = 1;
        c.gridy = 5;
        panel.add(buttons, c);
        savingsDepositButton.addActionListener(e -> depositOrWithdraw(true, true));
        savingsWithdrawButton.addActionListener(e -> depositOrWithdraw(true, false));
        savingsInterestButton.addActionListener(e -> applyInterest());

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        panel.add(new JLabel("Transaction History:"), c);

        savingsHistoryArea.setEditable(false);
        savingsHistoryArea.setRows(10);
        JScrollPane scroll = new JScrollPane(savingsHistoryArea);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(scroll, c);

        return panel;
    }

    private JPanel buildTransferTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, c, 0, "Amount:", transferAmountField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(toSavingsButton);
        buttons.add(toCheckingButton);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(buttons, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        transferStatusLabel.setForeground(new Color(0, 110, 0));
        panel.add(transferStatusLabel, c);

        toSavingsButton.addActionListener(e -> doTransfer(true));
        toCheckingButton.addActionListener(e -> doTransfer(false));

        return panel;
    }

    private void addRow(JPanel panel, GridBagConstraints c, int row, String labelText, JComponent field) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        c.weightx = 0;
        panel.add(new JLabel(labelText), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(field, c);
    }

    // ---------- Actions ----------

    private Customer getSelectedCustomer() {
        Customer customer = customerList.getSelectedValue();
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Select a customer first.", "No Customer Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
        return customer;
    }

    private void openAccount(boolean savings) {
        Customer customer = getSelectedCustomer();
        if (customer == null) return;

        try {
            if (savings) {
                double balance = parseAmount(savingsOpenField.getText());
                double rate = Double.parseDouble(savingsRateField.getText().trim());
                String accountNumber = "SAV" + customer.getCustomerId();
                customer.setSavingsAccount(new SavingsAccount(accountNumber, balance, rate));
                savingsOpenField.setText("");
            } else {
                double balance = parseAmount(checkingOpenField.getText());
                String accountNumber = "CHK" + customer.getCustomerId();
                customer.setCheckingAccount(new CheckingAccount(accountNumber, balance));
                checkingOpenField.setText("");
            }
            refreshDetailPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void depositOrWithdraw(boolean savings, boolean deposit) {
        Customer customer = getSelectedCustomer();
        if (customer == null) return;

        BankAccount account = savings ? customer.getSavingsAccount() : customer.getCheckingAccount();
        if (account == null) {
            JOptionPane.showMessageDialog(this, "That account has not been opened yet.", "No Account",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField amountField = savings ? savingsAmountField : checkingAmountField;
        try {
            double amount = parseAmount(amountField.getText());
            if (deposit) {
                account.deposit(amount);
            } else {
                if (!account.withdraw(amount)) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds.", "Withdraw Failed",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            amountField.setText("");
            refreshDetailPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyInterest() {
        Customer customer = getSelectedCustomer();
        if (customer == null) return;

        SavingsAccount savings = customer.getSavingsAccount();
        if (savings == null) {
            JOptionPane.showMessageDialog(this, "That account has not been opened yet.", "No Account",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        savings.applyInterest();
        refreshDetailPanel();
    }

    private void doTransfer(boolean toSavings) {
        Customer customer = getSelectedCustomer();
        if (customer == null) return;

        if (customer.getCheckingAccount() == null || customer.getSavingsAccount() == null) {
            transferStatusLabel.setForeground(Color.RED);
            transferStatusLabel.setText("Both a checking and savings account must be open to transfer.");
            return;
        }

        try {
            double amount = parseAmount(transferAmountField.getText());
            boolean success = toSavings
                    ? bank.transferCheckingToSavings(customer, amount)
                    : bank.transferSavingsToChecking(customer, amount);

            if (success) {
                transferStatusLabel.setForeground(new Color(0, 110, 0));
                transferStatusLabel.setText("Transfer successful.");
                transferAmountField.setText("");
            } else {
                transferStatusLabel.setForeground(Color.RED);
                transferStatusLabel.setText("Transfer failed: insufficient funds.");
            }
            refreshDetailPanel();
        } catch (NumberFormatException ex) {
            transferStatusLabel.setForeground(Color.RED);
            transferStatusLabel.setText("Please enter a valid amount.");
        }
    }

    private double parseAmount(String text) {
        double amount = Double.parseDouble(text.trim());
        if (amount <= 0) {
            throw new NumberFormatException("Amount must be positive.");
        }
        return amount;
    }

    // ---------- Refreshing ----------

    private void setDetailEnabled(boolean enabled) {
        detailTabs.setEnabled(enabled);
        for (Component comp : detailTabs.getComponents()) {
            comp.setEnabled(enabled);
        }
    }

    private void refreshDetailPanel() {
        Customer customer = customerList.getSelectedValue();
        if (customer == null) {
            setDetailEnabled(false);
            return;
        }
        setDetailEnabled(true);

        idValueLabel.setText(String.valueOf(customer.getCustomerId()));
        nameField.setText(customer.getName());
        addressField.setText(customer.getAddress());
        phoneField.setText(customer.getPhoneNumber());

        CheckingAccount checking = customer.getCheckingAccount();
        if (checking == null) {
            checkingBalanceLabel.setText("No account open");
            checkingHistoryArea.setText("");
            setCheckingAccountControlsEnabled(false);
        } else {
            checkingBalanceLabel.setText(String.format("$%.2f  (Acct #%s)", checking.getBalance(), checking.getAccountNumber()));
            checkingHistoryArea.setText(formatHistory(checking.getTransactions()));
            setCheckingAccountControlsEnabled(true);
        }

        SavingsAccount savings = customer.getSavingsAccount();
        if (savings == null) {
            savingsBalanceLabel.setText("No account open");
            savingsHistoryArea.setText("");
            setSavingsAccountControlsEnabled(false);
        } else {
            savingsBalanceLabel.setText(String.format("$%.2f  (Acct #%s)", savings.getBalance(), savings.getAccountNumber()));
            savingsHistoryArea.setText(formatHistory(savings.getTransactions()));
            setSavingsAccountControlsEnabled(true);
        }

        transferStatusLabel.setText(" ");
    }

    private void setCheckingAccountControlsEnabled(boolean hasAccount) {
        checkingOpenField.setEnabled(!hasAccount);
        checkingOpenButton.setEnabled(!hasAccount);
        checkingAmountField.setEnabled(hasAccount);
        checkingDepositButton.setEnabled(hasAccount);
        checkingWithdrawButton.setEnabled(hasAccount);
    }

    private void setSavingsAccountControlsEnabled(boolean hasAccount) {
        savingsOpenField.setEnabled(!hasAccount);
        savingsRateField.setEnabled(!hasAccount);
        savingsOpenButton.setEnabled(!hasAccount);
        savingsAmountField.setEnabled(hasAccount);
        savingsDepositButton.setEnabled(hasAccount);
        savingsWithdrawButton.setEnabled(hasAccount);
        savingsInterestButton.setEnabled(hasAccount);
    }

    private String formatHistory(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return "No transactions yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Transaction t : transactions) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fall back to default look and feel.
            }
            new BankGUI().setVisible(true);
        });
    }
}
