import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreAppPro {
    private static JFrame frame;
    private static String currentUser;
    private static String userRole;
    private static DefaultTableModel bookTableModel;
    private static DefaultTableModel userTableModel;
    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    
    // Professional color scheme with gradients
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);        // Material Blue
    private static final Color PRIMARY_DARK = new Color(25, 118, 210);         // Darker Blue
    private static final Color ACCENT_COLOR = new Color(255, 193, 7);          // Amber
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);         // Green
    private static final Color WARNING_COLOR = new Color(255, 152, 0);         // Orange
    private static final Color DANGER_COLOR = new Color(244, 67, 54);          // Red
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);    // Very Light Gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);
    
    // Book class
    static class Book {
        String id, title, author, category, price, quantity;
        
        Book(String id, String title, String author, String category, String price, String quantity) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.category = category;
            this.price = price;
            this.quantity = quantity;
        }
        
        Object[] toArray() {
            return new Object[]{id, title, author, category, price, quantity};
        }
    }
    
    // User class
    static class User {
        String id, username, fullName, role, status;
        
        User(String id, String username, String fullName, String role, String status) {
            this.id = id;
            this.username = username;
            this.fullName = fullName;
            this.role = role;
            this.status = status;
        }
        
        Object[] toArray() {
            return new Object[]{id, username, fullName, role, status};
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                
                // Customize UI defaults
                UIManager.put("Table.selectionBackground", new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 30));
                UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
                
            } catch (Exception e) {
                // Use default look and feel
            }
            
            initializeData();
            createLoginFrame();
        });
    }
    
    private static void initializeData() {
        // Initialize sample books
        books.add(new Book("1", "Lập trình Java", "Nguyễn Văn A", "Công nghệ", "150,000 VNĐ", "25"));
        books.add(new Book("2", "Cấu trúc dữ liệu", "Trần Thị B", "Công nghệ", "200,000 VNĐ", "15"));
        books.add(new Book("3", "Triết học đời sống", "Lê Văn C", "Triết học", "120,000 VNĐ", "30"));
        books.add(new Book("4", "Marketing hiện đại", "Phạm Thị D", "Kinh doanh", "180,000 VNĐ", "20"));
        books.add(new Book("5", "Khoa học máy tính", "Hoàng Văn E", "Công nghệ", "250,000 VNĐ", "12"));
        
        // Initialize sample users
        users.add(new User("1", "admin", "Quản trị viên hệ thống", "Admin", "Hoạt động"));
        users.add(new User("2", "manager", "Nguyễn Quản Lý", "Manager", "Hoạt động"));
        users.add(new User("3", "user", "Trần Nhân Viên", "User", "Hoạt động"));
    }
    
    private static void createLoginFrame() {
        JFrame loginFrame = new JFrame("Bookstore Management System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(600, 500);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        
        // Main panel with professional gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR, 
                    getWidth(), getHeight(), PRIMARY_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Header section with logo
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(40, 40, 30, 40));
        
        JLabel logoLabel = new JLabel("📚", JLabel.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("BOOKSTORE", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Management System Professional", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitleLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Login card with modern design
        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setOpaque(false);
        cardContainer.setBorder(new EmptyBorder(0, 50, 50, 50));
        
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            createShadowBorder(),
            new EmptyBorder(35, 35, 35, 35)
        ));
        
        // Form fields with modern styling
        JLabel loginTitle = new JLabel("Đăng nhập vào hệ thống", JLabel.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(TEXT_PRIMARY);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username field
        JLabel userLabel = new JLabel("👤 Tên đăng nhập");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(TEXT_PRIMARY);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = createModernTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Password field
        JLabel passLabel = new JLabel("🔒 Mật khẩu");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(TEXT_PRIMARY);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passwordField = createModernPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Login button with gradient
        JButton loginButton = createGradientButton("🚀 ĐĂNG NHẬP", PRIMARY_COLOR, PRIMARY_DARK);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // Demo accounts info
        JLabel infoLabel = new JLabel("<html><center>" +
            "<div style='color: #757575; font-size: 11px; margin-top: 10px;'>" +
            "<b>🎭 Tài khoản demo:</b><br/>" +
            "👨‍💼 admin/admin123 (Quản trị viên)<br/>" +
            "👨‍💻 manager/manager123 (Quản lý)<br/>" +
            "👤 user/user123 (Nhân viên)<br/><br/>" +
            "<span style='color: #4CAF50;'><b>✨ GIAO DIỆN PROFESSIONAL!</b></span>" +
            "</div></center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add all components with proper spacing
        cardPanel.add(loginTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(userLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(usernameField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        cardPanel.add(passLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(passwordField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(infoLabel);
        
        cardContainer.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(cardContainer, BorderLayout.CENTER);
        
        // Login action
        ActionListener loginAction = e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (authenticateUser(username, password)) {
                currentUser = username;
                loginFrame.dispose();
                createMainFrame();
            } else {
                showModernErrorDialog(loginFrame, "❌ Đăng nhập thất bại", 
                    "Tài khoản hoặc mật khẩu không đúng.\nVui lòng kiểm tra lại thông tin.");
            }
        };
        
        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }
    
    private static boolean authenticateUser(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            userRole = "Quản trị viên";
            return true;
        } else if ("manager".equals(username) && "manager123".equals(password)) {
            userRole = "Quản lý";
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            userRole = "Nhân viên";
            return true;
        }
        return false;
    }
    
    // Helper methods for modern UI components
    private static JTextField createModernTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 16, 12, 16)
        ));
        textField.setForeground(TEXT_PRIMARY);
        textField.setBackground(Color.WHITE);
        
        // Add focus effects
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    new EmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return textField;
    }
    
    private static JPasswordField createModernPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 16, 12, 16)
        ));
        passwordField.setForeground(TEXT_PRIMARY);
        passwordField.setBackground(Color.WHITE);
        
        // Add focus effects
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    new EmptyBorder(11, 15, 11, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });
        
        return passwordField;
    }
    
    private static JButton createGradientButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private static JButton createModernButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(new EmptyBorder(12, 24, 12, 24));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect with animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private static javax.swing.border.Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 4, 4, new Color(0, 0, 0, 20)),
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1)
        );
    }
    
    private static void showModernErrorDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private static void showModernSuccessDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void createMainFrame() {
        frame = new JFrame("📚 Bookstore Management System Professional");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 950);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BACKGROUND_COLOR);
        
        // Set modern frame properties
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Create main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Create stunning header
        JPanel headerPanel = createStunningHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create navigation sidebar
        JPanel sidebarPanel = createModernSidebar();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        
        // Create content area with tabs
        JTabbedPane tabbedPane = createModernTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusBar = createStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        
        showModernSuccessDialog(frame, "🎉 Đăng nhập thành công!", 
            "Chào mừng " + currentUser + " đến với hệ thống!\nVai trò: " + userRole);
        
        frame.setVisible(true);
    }
    
    private static JPanel createStunningHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create stunning gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), 0, new Color(138, 43, 226) // Purple accent
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 10));
                for (int i = 0; i < getWidth(); i += 30) {
                    g2d.drawLine(i, 0, i + 15, getHeight());
                }
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setBorder(new EmptyBorder(15, 25, 15, 25));
        
        // Left side - Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("📚");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        
        JLabel titleLabel = new JLabel("BOOKSTORE PRO");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 15, 0, 0));
        
        JLabel subtitleLabel = new JLabel("Professional Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        subtitleLabel.setBorder(new EmptyBorder(0, 15, 0, 0));
        
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setOpaque(false);
        titleContainer.add(titleLabel);
        titleContainer.add(subtitleLabel);
        
        leftPanel.add(logoLabel);
        leftPanel.add(titleContainer);
        
        // Right side - User info and controls
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        
        // User info card
        JPanel userCard = new JPanel();
        userCard.setBackground(new Color(255, 255, 255, 20));
        userCard.setBorder(new EmptyBorder(10, 20, 10, 20));
        userCard.setLayout(new BoxLayout(userCard, BoxLayout.Y_AXIS));
        
        JLabel userLabel = new JLabel("👤 " + currentUser);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel roleLabel = new JLabel(userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleLabel.setForeground(new Color(255, 255, 255, 180));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userCard.add(userLabel);
        userCard.add(roleLabel);
        
        // Logout button
        JButton logoutButton = createModernButton("🚪 Đăng xuất", DANGER_COLOR, new Color(211, 47, 47));
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
                createLoginFrame();
            }
        });
        
        rightPanel.add(userCard);
        rightPanel.add(logoutButton);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private static JPanel createModernSidebar() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBackground(CARD_COLOR);
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));
        
        // Sidebar header
        JPanel sidebarHeader = new JPanel();
        sidebarHeader.setLayout(new BoxLayout(sidebarHeader, BoxLayout.Y_AXIS));
        sidebarHeader.setBackground(CARD_COLOR);
        sidebarHeader.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        JLabel navTitle = new JLabel("📊 DASHBOARD");
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        navTitle.setForeground(TEXT_PRIMARY);
        navTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel navSubtitle = new JLabel("Quản lý toàn diện");
        navSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        navSubtitle.setForeground(TEXT_SECONDARY);
        navSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        sidebarHeader.add(navTitle);
        sidebarHeader.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarHeader.add(navSubtitle);
        
        sidebarPanel.add(sidebarHeader);
        
        // Navigation items
        String[] navItems = {
            "📈 Thống kê tổng quan",
            "📚 Quản lý sách", 
            "👥 Quản lý người dùng",
            "🛒 Đơn hàng",
            "📊 Báo cáo",
            "⚙️ Cài đặt"
        };
        
        for (String item : navItems) {
            JButton navButton = createSidebarButton(item);
            sidebarPanel.add(navButton);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        sidebarPanel.add(Box.createVerticalGlue());
        
        return sidebarPanel;
    }
    
    private static JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(CARD_COLOR);
        button.setBorder(new EmptyBorder(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 20));
                button.setForeground(PRIMARY_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_COLOR);
                button.setForeground(TEXT_PRIMARY);
            }
        });
        
        return button;
    }
    
    private static JTabbedPane createModernTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        // Dashboard tab
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("📊 Dashboard", dashboardPanel);
        
        // Books management tab
        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("📚 Quản lý Sách", booksPanel);
        
        // Users management tab (if admin)
        if ("admin".equals(currentUser)) {
            JPanel usersPanel = createUsersPanel();
            tabbedPane.addTab("👥 Quản lý Người dùng", usersPanel);
        }
        
        return tabbedPane;
    }
    
    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);
        
        statsPanel.add(createStatsCard("📚", "Tổng số sách", "1,247", "books", SUCCESS_COLOR));
        statsPanel.add(createStatsCard("👥", "Người dùng", "156", "users", PRIMARY_COLOR));
        statsPanel.add(createStatsCard("🛒", "Đơn hàng", "89", "orders", WARNING_COLOR));
        statsPanel.add(createStatsCard("💰", "Doanh thu", "45.2M VNĐ", "revenue", ACCENT_COLOR));
        
        panel.add(statsPanel, BorderLayout.NORTH);
        
        // Charts and activity area
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(25, 0, 0, 0));
        
        // Activity feed
        JPanel activityPanel = createActivityPanel();
        contentPanel.add(activityPanel);
        
        // Quick actions
        JPanel quickActionsPanel = createQuickActionsPanel();
        contentPanel.add(quickActionsPanel);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createStatsCard(String icon, String title, String value, String subtitle, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            createShadowBorder(),
            new EmptyBorder(25, 20, 25, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);
        
        return card;
    }
    
    private static JPanel createActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            createShadowBorder(),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("🔥 Hoạt động gần đây");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel activityList = new JPanel();
        activityList.setLayout(new BoxLayout(activityList, BoxLayout.Y_AXIS));
        activityList.setBackground(CARD_COLOR);
        
        String[] activities = {
            "📚 Đã thêm sách 'Java Programming' vào hệ thống",
            "👤 Người dùng mới 'nguyenvana' đã đăng ký",
            "🛒 Đơn hàng #1234 đã được xử lý thành công",
            "📊 Báo cáo tháng đã được tạo",
            "⚙️ Hệ thống đã được cập nhật lên phiên bản 2.1"
        };
        
        for (String activity : activities) {
            JPanel activityItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
            activityItem.setBackground(CARD_COLOR);
            activityItem.setBorder(new EmptyBorder(8, 0, 8, 0));
            
            JLabel activityLabel = new JLabel(activity);
            activityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            activityLabel.setForeground(TEXT_SECONDARY);
            
            activityItem.add(activityLabel);
            activityList.add(activityItem);
        }
        
        JScrollPane scrollPane = new JScrollPane(activityList);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 15)), BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private static JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            createShadowBorder(),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("⚡ Thao tác nhanh");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel actionsGrid = new JPanel(new GridLayout(3, 1, 0, 15));
        actionsGrid.setBackground(CARD_COLOR);
        actionsGrid.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JButton addBookBtn = createModernButton("📚 Thêm sách mới", PRIMARY_COLOR, PRIMARY_DARK);
        JButton addUserBtn = createModernButton("👥 Thêm người dùng", SUCCESS_COLOR, new Color(67, 160, 71));
        JButton generateReportBtn = createModernButton("📊 Tạo báo cáo", WARNING_COLOR, new Color(245, 124, 0));
        
        actionsGrid.add(addBookBtn);
        actionsGrid.add(addUserBtn);
        actionsGrid.add(generateReportBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(actionsGrid, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header with search and actions
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("📚 Quản lý Sách");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        
        JTextField searchField = createModernTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        
        JButton addButton = createModernButton("➕ Thêm sách", SUCCESS_COLOR, new Color(67, 160, 71));
        JButton editButton = createModernButton("✏️ Sửa", PRIMARY_COLOR, PRIMARY_DARK);
        JButton deleteButton = createModernButton("🗑️ Xóa", DANGER_COLOR, new Color(211, 47, 47));
        
        actionsPanel.add(new JLabel("🔍"));
        actionsPanel.add(searchField);
        actionsPanel.add(addButton);
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionsPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Books table
        JPanel tablePanel = createModernTablePanel("books");
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("👥 Quản lý Người dùng");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        
        JButton addButton = createModernButton("➕ Thêm người dùng", SUCCESS_COLOR, new Color(67, 160, 71));
        JButton editButton = createModernButton("✏️ Sửa", PRIMARY_COLOR, PRIMARY_DARK);
        JButton deleteButton = createModernButton("🗑️ Xóa", DANGER_COLOR, new Color(211, 47, 47));
        
        actionsPanel.add(addButton);
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionsPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Users table
        JPanel tablePanel = createModernTablePanel("users");
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createModernTablePanel(String type) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            createShadowBorder(),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Create table based on type
        JTable table;
        if ("books".equals(type)) {
            String[] columns = {"ID", "Tên sách", "Tác giả", "Thể loại", "Giá", "Số lượng"};
            bookTableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(bookTableModel);
            refreshBookTable();
        } else {
            String[] columns = {"ID", "Tên đăng nhập", "Họ tên", "Vai trò", "Trạng thái"};
            userTableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(userTableModel);
            refreshUserTable();
        }
        
        // Style the table
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(45);
        table.setSelectionBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 30));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER_COLOR);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        
        // Style table header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                setBorder(new EmptyBorder(10, 15, 10, 15));
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(CARD_COLOR);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            new EmptyBorder(10, 20, 10, 20)
        ));
        
        JLabel statusLabel = new JLabel("🟢 Hệ thống hoạt động bình thường");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(SUCCESS_COLOR);
        
        JLabel timeLabel = new JLabel("📅 " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);
        
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(timeLabel, BorderLayout.EAST);
        
        return statusBar;
    }
    
    private static void refreshBookTable() {
        if (bookTableModel != null) {
            bookTableModel.setRowCount(0);
            for (Book book : books) {
                bookTableModel.addRow(book.toArray());
            }
        }
    }
    
    private static void refreshUserTable() {
        if (userTableModel != null) {
            userTableModel.setRowCount(0);
            for (User user : users) {
                userTableModel.addRow(user.toArray());
            }
        }
    }
}
