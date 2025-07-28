import java.sql.*;

public class Accounts {
    public static void createAccount(String email) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts(email,balance) VALUES (?,?)");
        stmt.setString(1, email);
        stmt.setDouble(2, 0.0);
        stmt.executeUpdate();
        conn.close();
    }

    public static double getBalance(String email) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE email=?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        double balance = rs.getDouble("balance");
        conn.close();
        return balance;
    }

    public static void updateBalance(String email, double newBal) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance=? WHERE email=?");
        stmt.setDouble(1, newBal);
        stmt.setString(2, email);
        stmt.executeUpdate();
        conn.close();
    }
}

