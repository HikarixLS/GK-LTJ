package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu MySQL
 */
public class DatabaseConnection {
    private static final String CONFIG_FILE = "src/main/resources/database.properties";
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Thông tin kết nối mặc định
    private String url = "jdbc:mysql://localhost:3306/bookstore";
    private String username = "root";
    private String password = "";
    
    private DatabaseConnection() {
        try {
            loadConfig();
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy MySQL JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Tải cấu hình từ file properties
     */
    private void loadConfig() {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            props.load(fis);
            
            url = props.getProperty("db.url", url);
            username = props.getProperty("db.username", username);
            password = props.getProperty("db.password", password);
            
            fis.close();
        } catch (IOException e) {
            System.out.println("Sử dụng cấu hình mặc định cho database");
        }
    }
    
    /**
     * Lấy instance của DatabaseConnection (Singleton pattern)
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Lấy kết nối database
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy kết nối: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Đóng kết nối database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối cơ sở dữ liệu");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Kiểm tra kết nối
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
