import dao.UserDAO;
import dao.DatabaseConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("=== TEST LOGIN ===");
        
        // Test database connection
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection OK");
                
                // Check if users table exists and has data
                PreparedStatement stmt = conn.prepareStatement("SELECT username, password FROM users LIMIT 5");
                ResultSet rs = stmt.executeQuery();
                
                System.out.println("📋 Users in database:");
                while (rs.next()) {
                    System.out.println("   " + rs.getString("username") + " / " + rs.getString("password"));
                }
                
                // Test login with UserDAO
                UserDAO userDAO = new UserDAO();
                
                System.out.println("\n🔐 Testing login...");
                User user1 = userDAO.authenticate("admin", "admin123");
                if (user1 != null) {
                    System.out.println("✅ admin/admin123 - SUCCESS: " + user1.getFullName());
                } else {
                    System.out.println("❌ admin/admin123 - FAILED");
                }
                
                User user2 = userDAO.authenticate("user", "user123");
                if (user2 != null) {
                    System.out.println("✅ user/user123 - SUCCESS: " + user2.getFullName());
                } else {
                    System.out.println("❌ user/user123 - FAILED");
                }
                
                User user3 = userDAO.authenticate("manager", "manager123");
                if (user3 != null) {
                    System.out.println("✅ manager/manager123 - SUCCESS: " + user3.getFullName());
                } else {
                    System.out.println("❌ manager/manager123 - FAILED");
                }
                
            } else {
                System.out.println("❌ Database connection failed");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
