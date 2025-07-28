import java.sql.*;

public class AccountManager {
    public static double getBalance(int userId) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE user_id=?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void credit(int userId, double amount) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE user_id=?");
            stmt.setDouble(1, amount);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean debit(int userId, double amount) {
        double current = getBalance(userId);
        if (current >= amount) {
            try (Connection conn = Database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE user_id=?");
                stmt.setDouble(1, amount);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean transfer(int fromId, int toId, double amount) {
        if (debit(fromId, amount)) {
            credit(toId, amount);
            return true;
        }
        return false;
    }
}
