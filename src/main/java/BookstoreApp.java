import gui.LoginFrame;
import dao.DatabaseConnection;
import javax.swing.*;

/**
 * Lớp chính để khởi chạy ứng dụng Quản lý Cửa hàng Sách
 */
public class BookstoreApp {
    
    public static void main(String[] args) {
        // Thiết lập Look and Feel
        try {
            // Sử dụng Nimbus LAF cho giao diện đẹp hơn
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                // Fallback về System LAF
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Thiết lập font mặc định cho ứng dụng
        setupDefaultFont();
        
        // Kiểm tra kết nối database
        checkDatabaseConnection();
        
        // Khởi chạy ứng dụng
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
    
    /**
     * Thiết lập font mặc định cho toàn ứng dụng
     */
    private static void setupDefaultFont() {
        java.awt.Font font = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
    
    /**
     * Kiểm tra kết nối cơ sở dữ liệu
     */
    private static void checkDatabaseConnection() {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            if (!dbConnection.isConnected()) {
                showDatabaseError();
                System.exit(1);
            }
        } catch (Exception e) {
            showDatabaseError();
            System.exit(1);
        }
    }
    
    /**
     * Hiển thị thông báo lỗi database
     */
    private static void showDatabaseError() {
        String message = "<html><body style='width: 300px;'>" +
                "<h3>Lỗi kết nối cơ sở dữ liệu</h3>" +
                "<p>Không thể kết nối đến cơ sở dữ liệu MySQL.</p>" +
                "<p><b>Hướng dẫn khắc phục:</b></p>" +
                "<ol>" +
                "<li>Đảm bảo MySQL Server đang chạy</li>" +
                "<li>Kiểm tra thông tin kết nối trong database.properties</li>" +
                "<li>Tạo database 'bookstore' và import schema.sql</li>" +
                "<li>Đảm bảo MySQL JDBC Driver có trong classpath</li>" +
                "</ol>" +
                "</body></html>";
        
        JOptionPane.showMessageDialog(null, message, "Lỗi Database", JOptionPane.ERROR_MESSAGE);
    }
}
