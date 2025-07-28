import javax.swing.*;

public class MainUI {
    public static void main(String[] args) {
        String[] options = {"Login", "Register"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome!", "Banking System",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            new LoginUI();
        } else if (choice == 1) {
            new RegisterUI();
        }
    }
}
