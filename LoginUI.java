import javax.swing.*;
import java.awt.*;

public class LoginUI {
    public LoginUI() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "User Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Both fields are required.");
                return;
            }

            int userId = User.login(username, password);

            if (userId > 0) {
                JOptionPane.showMessageDialog(null, "Login successful.");
                showOptions(userId);
            } else {
                JOptionPane.showMessageDialog(null, "Login failed.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login cancelled.");
        }
    }

    private void showOptions(int userId) {
        while (true) {
            String[] opts = {"Balance", "Credit", "Debit", "Transfer", "Logout"};
            int ch = JOptionPane.showOptionDialog(null, "Choose action:", "Dashboard",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opts, opts[0]);

            if (ch == 0) {
                double balance = AccountManager.getBalance(userId);
                JOptionPane.showMessageDialog(null, "Balance: ₹" + balance);
            } else if (ch == 1) {
                if (validatePin(userId)) {
                    try {
                        double amt = Double.parseDouble(JOptionPane.showInputDialog("Amount to credit:"));
                        AccountManager.credit(userId, amt);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid amount.");
                    }
                }
            } else if (ch == 2) {
                if (validatePin(userId)) {
                    try {
                        double amt = Double.parseDouble(JOptionPane.showInputDialog("Amount to debit:"));
                        if (!AccountManager.debit(userId, amt)) {
                            JOptionPane.showMessageDialog(null, "Insufficient balance.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid amount.");
                    }
                }
            } else if (ch == 3) {
                if (validatePin(userId)) {
                    try {
                        int toId = Integer.parseInt(JOptionPane.showInputDialog("Receiver user ID:"));
                        double amt = Double.parseDouble(JOptionPane.showInputDialog("Amount to transfer:"));
                        if (!AccountManager.transfer(userId, toId, amt)) {
                            JOptionPane.showMessageDialog(null, "Transfer failed.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid input.");
                    }
                }
            } else break;
        }
    }

    private boolean validatePin(int userId) {
        String pin = JOptionPane.showInputDialog("Enter security PIN:");
        if (pin == null || !User.validatePin(userId, pin)) {
            JOptionPane.showMessageDialog(null, "Invalid PIN.");
            return false;
        }
        return true;
    }
}
