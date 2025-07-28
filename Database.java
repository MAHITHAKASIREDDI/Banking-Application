import java.sql.*;

public class Database {
    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "mahi");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
