import dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateDatabase {
    public static void main(String[] args) {
        System.out.println("=== UPDATING DATABASE ===");
        
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection OK");
                
                // Update user passwords to plain text
                String updateSQL = "UPDATE users SET password = ? WHERE username = ?";
                
                PreparedStatement stmt = conn.prepareStatement(updateSQL);
                
                // Update admin password
                stmt.setString(1, "admin123");
                stmt.setString(2, "admin");
                int result1 = stmt.executeUpdate();
                System.out.println("Admin password updated: " + result1 + " rows affected");
                
                // Update user password
                stmt.setString(1, "user123");
                stmt.setString(2, "user");
                int result2 = stmt.executeUpdate();
                System.out.println("User password updated: " + result2 + " rows affected");
                
                // Update manager password
                stmt.setString(1, "manager123");
                stmt.setString(2, "manager");
                int result3 = stmt.executeUpdate();
                System.out.println("Manager password updated: " + result3 + " rows affected");
                
                System.out.println("✅ Database updated successfully!");
                
            } else {
                System.out.println("❌ Database connection failed");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
