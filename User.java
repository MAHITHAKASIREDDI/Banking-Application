import java.sql.*;

public class User {
    public static int register(String username, String password, String pin) {
        try (Connection conn = Database.getConnection()) {
            // Insert user
            String query = "INSERT INTO bank_users (username, password, pin) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, pin);
            stmt.executeUpdate();

            // Fetch ID
            PreparedStatement fetchStmt = conn.prepareStatement("SELECT id FROM bank_users WHERE username = ?");
            fetchStmt.setString(1, username);
            ResultSet rs = fetchStmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");

                // Create account
                PreparedStatement accStmt = conn.prepareStatement("INSERT INTO accounts (user_id, balance) VALUES (?, 0)");
                accStmt.setInt(1, userId);
                accStmt.executeUpdate();
                return userId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int login(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT id FROM bank_users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean validatePin(int userId, String pin) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM bank_users WHERE id=? AND pin=?");
            stmt.setInt(1, userId);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
