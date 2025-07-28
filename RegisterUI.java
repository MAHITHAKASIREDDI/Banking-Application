import javax.swing.*;
import java.awt.*;

public class RegisterUI {
    public RegisterUI() {
        // Create panel and form elements
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JPasswordField pinField = new JPasswordField(6);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("6-digit PIN:"));
        panel.add(pinField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register New User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String pin = new String(pinField.getPassword());

            if (username.isEmpty() || password.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            if (!pin.matches("\\d{6}")) {
                JOptionPane.showMessageDialog(null, "PIN must be exactly 6 digits.");
                return;
            }

            int userId = User.register(username, password, pin);
            if (userId > 0) {
                JOptionPane.showMessageDialog(null, "Registration successful! User ID: " + userId);
                new LoginUI(); // proceed to login
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Try another username.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Registration cancelled.");
        }
    }
}
