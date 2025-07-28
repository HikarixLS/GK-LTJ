import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Ứng dụng Bookstore Management chính thức với kết nối MySQL XAMPP
 */
public class BookstoreApp {
    
    private static Connection connection;
    private static String currentUser;
    private static String currentUserRole;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Thiết lập Look and Feel
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName()) || "Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                
                // Thiết lập font
                setupBetterFonts();
                
                // Kết nối database
                if (connectToDatabase()) {
                    createLoginFrame();
                } else {
                    showDatabaseError();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "❌ Lỗi khởi động ứng dụng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private static void setupBetterFonts() {
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);
        
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("Button.font", boldFont);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("PasswordField.font", defaultFont);
        UIManager.put("PasswordField.foreground", Color.BLACK);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("Table.foreground", Color.BLACK);
        UIManager.put("TableHeader.font", boldFont);
        UIManager.put("TabbedPane.font", boldFont);
        UIManager.put("TabbedPane.foreground", Color.BLACK);
        UIManager.put("TitledBorder.font", boldFont);
        UIManager.put("TitledBorder.titleColor", Color.BLACK);
        
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }
    
    private static boolean connectToDatabase() {
        try {
            // Load database properties
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("src/main/resources/database.properties")) {
                props.load(fis);
            } catch (IOException e) {
                // Fallback to default XAMPP settings
                props.setProperty("db.url", "jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
                props.setProperty("db.username", "root");
                props.setProperty("db.password", "");
            }
            
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Kết nối database thành công!");
            return true;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy MySQL JDBC Driver!");
            return false;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối database: " + e.getMessage());
            return false;
        }
    }
    
    private static void showDatabaseError() {
        JFrame errorFrame = new JFrame("❌ Lỗi Database");
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorFrame.setSize(500, 300);
        errorFrame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel errorLabel = new JLabel("<html><center>" +
            "❌ <b>KHÔNG THỂ KẾT NỐI DATABASE</b><br/><br/>" +
            "Vui lòng kiểm tra:<br/>" +
            "✅ XAMPP đã được khởi động<br/>" +
            "✅ MySQL Service đang chạy<br/>" +
            "✅ Database 'bookstore' đã tồn tại<br/>" +
            "✅ MySQL JDBC Driver trong thư mục lib/<br/><br/>" +
            "Sau khi sửa, khởi động lại ứng dụng." +
            "</center></html>", JLabel.CENTER);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorLabel.setForeground(Color.BLACK);
        
        JButton exitButton = new JButton("Thoát");
        exitButton.addActionListener(e -> System.exit(0));
        
        panel.add(errorLabel, BorderLayout.CENTER);
        panel.add(exitButton, BorderLayout.SOUTH);
        
        errorFrame.add(panel);
        errorFrame.setVisible(true);
    }
    
    private static void createLoginFrame() {
        JFrame frame = new JFrame("🏪 Bookstore Management - Đăng nhập");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JLabel headerLabel = new JLabel("📚 BOOKSTORE MANAGEMENT SYSTEM", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Login panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30),
            BorderFactory.createTitledBorder("Đăng nhập hệ thống")
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(Color.BLACK);
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setForeground(Color.BLACK);
        usernameField.setPreferredSize(new Dimension(200, 35));
        loginPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel passLabel = new JLabel("🔒 Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passLabel.setForeground(Color.BLACK);
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setForeground(Color.BLACK);
        passwordField.setPreferredSize(new Dimension(200, 35));
        loginPanel.add(passwordField, gbc);
        
        // Login button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton loginButton = new JButton("🚀 ĐĂNG NHẬP");
        loginButton.setPreferredSize(new Dimension(250, 45));
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (authenticateUser(username, password)) {
                frame.dispose();
                createMainFrame();
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "❌ Sai username hoặc password!", 
                    "Lỗi đăng nhập", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Enter key support
        passwordField.addActionListener(e -> loginButton.doClick());
        
        loginPanel.add(loginButton, gbc);
        
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        // Footer
        JLabel footerLabel = new JLabel("<html><center>🏪 BOOKSTORE MANAGEMENT SYSTEM<br/>Kết nối với MySQL XAMPP</center></html>", JLabel.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(Color.DARK_GRAY);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private static boolean authenticateUser(String username, String password) {
        try {
            String hashedPassword = hashPassword(password);
            String sql = "SELECT user_id, full_name, role FROM users WHERE username = ? AND password = ? AND is_active = 1";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    currentUser = rs.getString("full_name");
                    currentUserRole = rs.getString("role");
                    
                    // Update last login
                    updateLastLogin(username);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password; // Fallback
        }
    }
    
    private static void updateLastLogin(String username) {
        try {
            String sql = "UPDATE users SET last_login = NOW() WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void createMainFrame() {
        JFrame frame = new JFrame("🏪 Bookstore Management - " + currentUser + " (" + currentUserRole + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu systemMenu = new JMenu("Hệ thống");
        
        JMenuItem logoutItem = new JMenuItem("🚪 Đăng xuất");
        logoutItem.addActionListener(e -> {
            frame.dispose();
            currentUser = null;
            currentUserRole = null;
            createLoginFrame();
        });
        
        JMenuItem exitItem = new JMenuItem("❌ Thoát");
        exitItem.addActionListener(e -> System.exit(0));
        
        systemMenu.add(logoutItem);
        systemMenu.addSeparator();
        systemMenu.add(exitItem);
        menuBar.add(systemMenu);
        
        frame.setJMenuBar(menuBar);
        
        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab quản lý sách (tất cả roles)
        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("📚 Quản lý Sách", booksPanel);
        
        // Tab quản lý users (chỉ admin)
        if ("ADMIN".equals(currentUserRole)) {
            JPanel usersPanel = createUsersPanel();
            tabbedPane.addTab("👥 Quản lý Người dùng", usersPanel);
        }
        
        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    
    private static JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Header
        JLabel header = new JLabel("📚 QUẢN LÝ SÁCH", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(header, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(Color.WHITE);
        
        JLabel searchLabel = new JLabel("🔍 Tìm kiếm:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLabel.setForeground(Color.BLACK);
        
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.BLACK);
        
        JButton searchButton = new JButton("Tìm");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Table
        String[] columns = {"ID", "Tên sách", "Tác giả", "ISBN", "Danh mục", "Giá", "Số lượng"};
        Object[][] data = loadBooksFromDatabase();
        
        JTable table = new JTable(data, columns);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Center panel với search và table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Buttons panel (chỉ admin và employee mới có quyền thêm/sửa/xóa)
        if ("ADMIN".equals(currentUserRole) || "EMPLOYEE".equals(currentUserRole)) {
            JPanel buttonsPanel = new JPanel(new FlowLayout());
            buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            buttonsPanel.setBackground(Color.WHITE);
            
            JButton addBtn = new JButton("➕ THÊM SÁCH");
            JButton editBtn = new JButton("✏️ SỬA");
            JButton deleteBtn = new JButton("🗑️ XÓA");
            JButton refreshBtn = new JButton("🔄 LÀM MỚI");
            
            Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
            Dimension buttonSize = new Dimension(150, 40);
            
            addBtn.setFont(buttonFont);
            addBtn.setPreferredSize(buttonSize);
            addBtn.setBackground(new Color(34, 139, 34));
            addBtn.setForeground(Color.WHITE);
            
            editBtn.setFont(buttonFont);
            editBtn.setPreferredSize(buttonSize);
            editBtn.setBackground(new Color(30, 144, 255));
            editBtn.setForeground(Color.WHITE);
            
            deleteBtn.setFont(buttonFont);
            deleteBtn.setPreferredSize(buttonSize);
            deleteBtn.setBackground(new Color(220, 20, 60));
            deleteBtn.setForeground(Color.WHITE);
            
            refreshBtn.setFont(buttonFont);
            refreshBtn.setPreferredSize(buttonSize);
            refreshBtn.setBackground(new Color(128, 128, 128));
            refreshBtn.setForeground(Color.WHITE);
            
            // Event handlers
            addBtn.addActionListener(e -> showAddBookDialog(table));
            editBtn.addActionListener(e -> showEditBookDialog(table));
            deleteBtn.addActionListener(e -> deleteSelectedBook(table));
            refreshBtn.addActionListener(e -> refreshBooksTable(table));
            
            buttonsPanel.add(addBtn);
            buttonsPanel.add(editBtn);
            buttonsPanel.add(deleteBtn);
            buttonsPanel.add(refreshBtn);
            
            panel.add(buttonsPanel, BorderLayout.SOUTH);
        }
        
        return panel;
    }
    
    private static Object[][] loadBooksFromDatabase() {
        try {
            String sql = "SELECT book_id, title, author, isbn, category, price, quantity FROM books ORDER BY book_id";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                
                // Count rows first
                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();
                
                Object[][] data = new Object[rowCount][7];
                int i = 0;
                
                while (rs.next()) {
                    data[i][0] = rs.getInt("book_id");
                    data[i][1] = rs.getString("title");
                    data[i][2] = rs.getString("author");
                    data[i][3] = rs.getString("isbn");
                    data[i][4] = rs.getString("category");
                    data[i][5] = String.format("%,.0f VND", rs.getBigDecimal("price"));
                    data[i][6] = rs.getInt("quantity");
                    i++;
                }
                
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][7]; // Empty array on error
        }
    }
    
    private static void showAddBookDialog(JTable table) {
        JOptionPane.showMessageDialog(null, "🔧 Chức năng thêm sách đang được phát triển...");
    }
    
    private static void showEditBookDialog(JTable table) {
        JOptionPane.showMessageDialog(null, "🔧 Chức năng sửa sách đang được phát triển...");
    }
    
    private static void deleteSelectedBook(JTable table) {
        JOptionPane.showMessageDialog(null, "🔧 Chức năng xóa sách đang được phát triển...");
    }
    
    private static void refreshBooksTable(JTable table) {
        Object[][] newData = loadBooksFromDatabase();
        // Update table model here
        JOptionPane.showMessageDialog(null, "✅ Đã làm mới dữ liệu!");
    }
    
    private static JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel header = new JLabel("👥 QUẢN LÝ NGƯỜI DÙNG", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(header, BorderLayout.NORTH);
        
        // Load users from database
        Object[][] userData = loadUsersFromDatabase();
        String[] userColumns = {"ID", "Username", "Họ tên", "Email", "Vai trò", "Trạng thái"};
        
        JTable userTable = new JTable(userData, userColumns);
        userTable.setRowHeight(35);
        userTable.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userTable.setForeground(Color.BLACK);
        userTable.setBackground(Color.WHITE);
        userTable.setSelectionForeground(Color.BLACK);
        userTable.setSelectionBackground(new Color(173, 216, 230));
        userTable.getTableHeader().setBackground(Color.WHITE);
        userTable.getTableHeader().setForeground(Color.BLACK);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        userTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        userTable.setGridColor(Color.BLACK);
        userTable.setShowGrid(true);
        
        JScrollPane userScrollPane = new JScrollPane(userTable);
        panel.add(userScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static Object[][] loadUsersFromDatabase() {
        try {
            String sql = "SELECT user_id, username, full_name, email, role, is_active FROM users ORDER BY user_id";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                
                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();
                
                Object[][] data = new Object[rowCount][6];
                int i = 0;
                
                while (rs.next()) {
                    data[i][0] = rs.getInt("user_id");
                    data[i][1] = rs.getString("username");
                    data[i][2] = rs.getString("full_name");
                    data[i][3] = rs.getString("email");
                    data[i][4] = rs.getString("role");
                    data[i][5] = rs.getBoolean("is_active") ? "Hoạt động" : "Vô hiệu hóa";
                    i++;
                }
                
                return data;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][6];
        }
    }
}
