package service;

import dao.UserDAO;
import model.User;
import model.User.UserRole;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Lớp Service cho User - xử lý logic nghiệp vụ người dùng
 */
public class UserService {
    private UserDAO userDAO;
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10,11}$");
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Xác thực đăng nhập
     */
    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Tên đăng nhập không được để trống");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.err.println("Mật khẩu không được để trống");
            return null;
        }
        
        // Hash password trước khi so sánh (trong thực tế)
        String hashedPassword = hashPassword(password);
        return userDAO.authenticate(username, hashedPassword);
    }
    
    /**
     * Đăng ký người dùng mới
     */
    public boolean registerUser(User user) {
        if (!validateUser(user)) {
            return false;
        }
        
        // Kiểm tra username đã tồn tại
        if (userDAO.isUsernameExists(user.getUsername())) {
            System.err.println("Tên đăng nhập đã tồn tại");
            return false;
        }
        
        // Hash password
        user.setPassword(hashPassword(user.getPassword()));
        
        return userDAO.addUser(user);
    }
    
    /**
     * Cập nhật thông tin người dùng
     */
    public boolean updateUser(User user) {
        if (!validateUser(user)) {
            return false;
        }
        
        // Hash password nếu được thay đổi
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        
        return userDAO.updateUser(user);
    }
    
    /**
     * Xóa người dùng
     */
    public boolean deleteUser(int userId) {
        if (userId <= 0) {
            System.err.println("ID người dùng không hợp lệ");
            return false;
        }
        
        // Không cho phép xóa admin cuối cùng
        List<User> admins = getUsersByRole(UserRole.ADMIN);
        if (admins.size() <= 1) {
            User user = getUserById(userId);
            if (user != null && user.getRole() == UserRole.ADMIN) {
                System.err.println("Không thể xóa admin cuối cùng");
                return false;
            }
        }
        
        return userDAO.deleteUser(userId);
    }
    
    /**
     * Lấy tất cả người dùng
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    /**
     * Tìm người dùng theo ID
     */
    public User getUserById(int userId) {
        if (userId <= 0) {
            System.err.println("ID người dùng không hợp lệ");
            return null;
        }
        return userDAO.getUserById(userId);
    }
    
    /**
     * Lấy người dùng theo vai trò
     */
    public List<User> getUsersByRole(UserRole role) {
        return getAllUsers().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }
    
    /**
     * Thay đổi mật khẩu
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (user == null) {
            System.err.println("Không tìm thấy người dùng");
            return false;
        }
        
        // Kiểm tra mật khẩu cũ
        if (!user.getPassword().equals(hashPassword(oldPassword))) {
            System.err.println("Mật khẩu cũ không đúng");
            return false;
        }
        
        // Validate mật khẩu mới
        if (!isValidPassword(newPassword)) {
            return false;
        }
        
        user.setPassword(hashPassword(newPassword));
        return userDAO.updateUser(user);
    }
    
    /**
     * Kiểm tra tính hợp lệ của thông tin người dùng
     */
    private boolean validateUser(User user) {
        if (user == null) {
            System.err.println("Thông tin người dùng không được để trống");
            return false;
        }
        
        // Kiểm tra username
        if (user.getUsername() == null || user.getUsername().trim().length() < 3) {
            System.err.println("Tên đăng nhập phải có ít nhất 3 ký tự");
            return false;
        }
        
        // Kiểm tra password
        if (!isValidPassword(user.getPassword())) {
            return false;
        }
        
        // Kiểm tra họ tên
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            System.err.println("Họ tên không được để trống");
            return false;
        }
        
        // Kiểm tra email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
                System.err.println("Email không hợp lệ");
                return false;
            }
        }
        
        // Kiểm tra số điện thoại
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            if (!PHONE_PATTERN.matcher(user.getPhone()).matches()) {
                System.err.println("Số điện thoại không hợp lệ (10-11 chữ số)");
                return false;
            }
        }
        
        // Kiểm tra vai trò
        if (user.getRole() == null) {
            System.err.println("Vai trò không được để trống");
            return false;
        }
        
        return true;
    }
    
    /**
     * Kiểm tra mật khẩu hợp lệ
     */
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            System.err.println("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        return true;
    }
    
    /**
     * Hash password bằng SHA-256
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Lỗi khi hash password: " + e.getMessage());
            return password; // Fallback - không nên dùng trong production
        }
    }
    
    /**
     * Kiểm tra quyền admin
     */
    public boolean isAdmin(User user) {
        return user != null && user.getRole() == UserRole.ADMIN;
    }
    
    /**
     * Kiểm tra người dùng có hoạt động không
     */
    public boolean isUserActive(int userId) {
        User user = getUserById(userId);
        return user != null && user.isActive();
    }
}
