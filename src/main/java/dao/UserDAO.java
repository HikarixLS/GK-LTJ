package dao;

import model.User;
import model.User.UserRole;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho User - xử lý các thao tác CRUD với người dùng
 */
public class UserDAO {
    private Connection connection;
    
    public UserDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Xác thực đăng nhập
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND is_active=1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Trong thực tế nên hash password
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                
                // Cập nhật thời gian đăng nhập cuối
                updateLastLogin(user.getUserId());
                user.setLastLogin(LocalDateTime.now());
                
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xác thực người dùng: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Thêm người dùng mới
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, full_name, email, phone, role, is_active, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getRole().name());
            stmt.setBoolean(7, user.isActive());
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật thông tin người dùng
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username=?, password=?, full_name=?, email=?, phone=?, role=?, is_active=? WHERE user_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getRole().name());
            stmt.setBoolean(7, user.isActive());
            stmt.setInt(8, user.getUserId());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xóa người dùng (vô hiệu hóa)
     */
    public boolean deleteUser(int userId) {
        String sql = "UPDATE users SET is_active=0 WHERE user_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lấy tất cả người dùng
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY full_name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
            e.printStackTrace();
        }
        
        return users;
    }
    
    /**
     * Tìm người dùng theo ID
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm người dùng: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Kiểm tra username đã tồn tại
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra username: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Cập nhật thời gian đăng nhập cuối
     */
    private void updateLastLogin(int userId) {
        String sql = "UPDATE users SET last_login=? WHERE user_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thời gian đăng nhập: " + e.getMessage());
        }
    }
    
    /**
     * Chuyển đổi ResultSet thành User object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(UserRole.valueOf(rs.getString("role")));
        user.setActive(rs.getBoolean("is_active"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) {
            user.setLastLogin(lastLogin.toLocalDateTime());
        }
        
        return user;
    }
}
