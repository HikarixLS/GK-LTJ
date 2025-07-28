import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreAppUltra {
    private static JFrame frame;
    private static String currentUser;
    private static String userRole;
    private static DefaultTableModel bookTableModel;
    private static DefaultTableModel userTableModel;
    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    
    // Ultra modern color scheme
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);        // Material Blue
    private static final Color PRIMARY_DARK = new Color(25, 118, 210);         
    private static final Color ACCENT_COLOR = new Color(255, 193, 7);          
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);         
    private static final Color WARNING_COLOR = new Color(255, 152, 0);         
    private static final Color DANGER_COLOR = new Color(244, 67, 54);          
    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);    
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
            initializeData();
            createUltraLoginFrame();
        });
    }
    
    private static void initializeData() {
        // Initialize sample books
        books.add(new Book("1", "Lập trình Java Advanced", "Nguyễn Văn A", "Công nghệ", "250,000 VNĐ", "25"));
        books.add(new Book("2", "Machine Learning với Python", "Trần Thị B", "AI/ML", "350,000 VNĐ", "15"));
        books.add(new Book("3", "Triết học đời sống hiện đại", "Lê Văn C", "Triết học", "180,000 VNĐ", "30"));
        books.add(new Book("4", "Digital Marketing 4.0", "Phạm Thị D", "Kinh doanh", "280,000 VNĐ", "20"));
        books.add(new Book("5", "Blockchain & Cryptocurrency", "Hoàng Văn E", "Fintech", "450,000 VNĐ", "12"));
        
        // Initialize sample users
        users.add(new User("1", "admin", "Quản trị viên hệ thống", "Admin", "Hoạt động"));
        users.add(new User("2", "manager", "Nguyễn Quản Lý", "Manager", "Hoạt động"));
        users.add(new User("3", "user", "Trần Nhân Viên", "User", "Hoạt động"));
    }
    
    private static void createUltraLoginFrame() {
        JFrame loginFrame = new JFrame("🌟 BOOKSTORE ULTRA PROFESSIONAL 🌟");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(800, 700);  // Tăng kích thước để đảm bảo hiển thị đầy đủ
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(true);  // Cho phép resize
        
        // Ultra modern gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Multi-color gradient
                Color[] colors = {
                    new Color(67, 56, 202),   // Indigo
                    new Color(147, 51, 234),  // Purple  
                    new Color(236, 72, 153),  // Pink
                    new Color(59, 130, 246)   // Blue
                };
                
                int h = getHeight();
                int w = getWidth();
                
                for (int i = 0; i < colors.length - 1; i++) {
                    GradientPaint gp = new GradientPaint(
                        0, i * h / (colors.length - 1), colors[i],
                        w, (i + 1) * h / (colors.length - 1), colors[i + 1]
                    );
                    g2d.setPaint(gp);
                    g2d.fillRect(0, i * h / (colors.length - 1), w, h / (colors.length - 1) + 1);
                }
                
                // Animated particles effect (static for now)
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < 50; i++) {
                    int x = (i * 123) % w;
                    int y = (i * 456) % h;
                    g2d.fillOval(x, y, 3, 3);
                }
            }
        };
        
        // Ultra header with 3D effect
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(30, 40, 20, 40));  // Giảm padding trên
        
        // Mega logo with shadow effect
        JLabel logoLabel = new JLabel("🌟", JLabel.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 60));  // Giảm size logo
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("BOOKSTORE ULTRA", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));  // Giảm size title
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("🚀 NEXT-GENERATION MANAGEMENT SYSTEM 🚀", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));  // Giảm size subtitle
        subtitleLabel.setForeground(new Color(255, 255, 255, 220));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(subtitleLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Ultra login card với scroll để đảm bảo hiển thị đầy đủ
        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setOpaque(false);
        cardContainer.setBorder(new EmptyBorder(10, 80, 40, 80));  // Điều chỉnh padding
        
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border with gradient
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                
                super.paintComponent(g);
            }
        };
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(30, 40, 30, 40));  // Giảm padding trong card
        
        // Ultra form fields
        JLabel loginTitle = new JLabel("✨ ĐĂNG NHẬP VÀO HỆ THỐNG ✨", JLabel.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginTitle.setForeground(Color.WHITE);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username field với spacing rõ ràng
        JLabel userLabel = new JLabel("👤 TÊN ĐĂNG NHẬP");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = createUltraTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Password field với spacing rõ ràng  
        JLabel passLabel = new JLabel("🔒 MẬT KHẨU");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(Color.WHITE);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passwordField = createUltraPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Ultra login button với kích thước hợp lý
        JButton loginButton = createUltraButton("🚀 ĐĂNG NHẬP NGAY", PRIMARY_COLOR, PRIMARY_DARK);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // Ultra demo info với kích thước nhỏ gọn
        JLabel infoLabel = new JLabel("<html><center>" +
            "<div style='color: white; font-size: 11px; margin-top: 10px;'>" +
            "<b>🎭 TÀI KHOẢN DEMO:</b><br/>" +
            "👨‍💼 <b>admin/admin123</b> (Siêu Quản Trị)<br/>" +
            "👨‍💻 <b>manager/manager123</b> (Quản Lý Pro)<br/>" +
            "👤 <b>user/user123</b> (Nhân Viên)<br/>" +
            "<span style='color: #FFD700; font-size: 12px;'><b>✨ ULTRA MODERN! ✨</b></span>" +
            "</div></center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add all components với spacing chính xác
        cardPanel.add(loginTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(userLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(usernameField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        cardPanel.add(passLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(passwordField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(infoLabel);
        
        // Đảm bảo card hiển thị đầy đủ
        JScrollPane cardScrollPane = new JScrollPane(cardPanel);
        cardScrollPane.setOpaque(false);
        cardScrollPane.getViewport().setOpaque(false);
        cardScrollPane.setBorder(null);
        cardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        cardContainer.add(cardScrollPane, BorderLayout.CENTER);
        mainPanel.add(cardContainer, BorderLayout.CENTER);
        
        // Login action with animation
        ActionListener loginAction = e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (authenticateUser(username, password)) {
                currentUser = username;
                loginFrame.dispose();
                createUltraMainFrame();
            } else {
                showUltraErrorDialog(loginFrame, "❌ ĐĂNG NHẬP THẤT BẠI", 
                    "🚫 Tài khoản hoặc mật khẩu không chính xác!\n💡 Vui lòng kiểm tra lại thông tin đăng nhập.");
            }
        };
        
        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }
    
    private static boolean authenticateUser(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            userRole = "Siêu Quản Trị";
            return true;
        } else if ("manager".equals(username) && "manager123".equals(password)) {
            userRole = "Quản Lý Pro";
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            userRole = "Nhân Viên";
            return true;
        }
        return false;
    }
    
    private static JTextField createUltraTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
            new EmptyBorder(15, 20, 15, 20)
        ));
        textField.setForeground(TEXT_PRIMARY);
        textField.setBackground(new Color(255, 255, 255, 90));
        textField.setOpaque(true);
        
        // Focus glow effect
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
                    new EmptyBorder(14, 19, 14, 19)
                ));
                textField.setBackground(new Color(255, 255, 255, 95));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                    new EmptyBorder(15, 20, 15, 20)
                ));
                textField.setBackground(new Color(255, 255, 255, 90));
            }
        });
        
        return textField;
    }
    
    private static JPasswordField createUltraPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
            new EmptyBorder(15, 20, 15, 20)
        ));
        passwordField.setForeground(TEXT_PRIMARY);
        passwordField.setBackground(new Color(255, 255, 255, 90));
        passwordField.setOpaque(true);
        
        // Focus glow effect
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
                    new EmptyBorder(14, 19, 14, 19)
                ));
                passwordField.setBackground(new Color(255, 255, 255, 95));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                    new EmptyBorder(15, 20, 15, 20)
                ));
                passwordField.setBackground(new Color(255, 255, 255, 90));
            }
        });
        
        return passwordField;
    }
    
    private static JButton createUltraButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ultra gradient with glow
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Glow effect
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(18, 30, 18, 30));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Ultra hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 17));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            }
        });
        
        return button;
    }
    
    private static void showUltraErrorDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private static void showUltraSuccessDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void createUltraMainFrame() {
        frame = new JFrame("🌟 BOOKSTORE ULTRA PROFESSIONAL SYSTEM 🌟");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBackground(BACKGROUND_COLOR);
        
        // Create ultra main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Ultra header
        JPanel headerPanel = createUltraHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Ultra content with tabs
        JTabbedPane tabbedPane = createUltraTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Ultra status bar
        JPanel statusBar = createUltraStatusBar();
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        
        showUltraSuccessDialog(frame, "🎉 ĐĂNG NHẬP THÀNH CÔNG!", 
            "🌟 Chào mừng " + currentUser + " đến với hệ thống Ultra Professional!\n🎭 Vai trò: " + userRole + 
            "\n🚀 Trải nghiệm giao diện cực đẹp!");
        
        frame.setVisible(true);
    }
    
    private static JPanel createUltraHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ultra gradient header
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(67, 56, 202),
                    getWidth(), 0, new Color(147, 51, 234)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 90));
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        // Left side
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("🌟");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        
        JLabel titleLabel = new JLabel("BOOKSTORE ULTRA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        
        // Right side
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        
        JLabel userInfo = new JLabel("👤 " + currentUser + " | " + userRole);
        userInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userInfo.setForeground(Color.WHITE);
        
        JButton logoutButton = createUltraButton("🚪 Đăng xuất", DANGER_COLOR, new Color(211, 47, 47));
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, 
                "🤔 Bạn có chắc chắn muốn đăng xuất khỏi hệ thống Ultra?", 
                "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
                createUltraLoginFrame();
            }
        });
        
        rightPanel.add(userInfo);
        rightPanel.add(logoutButton);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private static JTabbedPane createUltraTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        
        // Ultra Dashboard
        JPanel dashboardPanel = createUltraDashboard();
        tabbedPane.addTab("📊 DASHBOARD ULTRA", dashboardPanel);
        
        // Ultra Books
        JPanel booksPanel = createUltraBooksPanel();
        tabbedPane.addTab("📚 QUẢN LÝ SÁCH", booksPanel);
        
        // Ultra Users (if admin)
        if ("admin".equals(currentUser)) {
            JPanel usersPanel = createUltraUsersPanel();
            tabbedPane.addTab("👥 QUẢN LÝ NGƯỜI DÙNG", usersPanel);
        }
        
        return tabbedPane;
    }
    
    private static JPanel createUltraDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("🌟 WELCOME TO ULTRA DASHBOARD 🌟", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(new EmptyBorder(20, 0, 40, 0));
        
        // Ultra stats cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        statsPanel.setBackground(BACKGROUND_COLOR);
        
        statsPanel.add(createUltraStatsCard("📚", "SÁCH ULTRA", "1,247", SUCCESS_COLOR));
        statsPanel.add(createUltraStatsCard("👥", "NGƯỜI DÙNG PRO", "156", PRIMARY_COLOR));
        statsPanel.add(createUltraStatsCard("🛒", "ĐơN HÀNG VIP", "89", WARNING_COLOR));
        statsPanel.add(createUltraStatsCard("💎", "DOANH THU KHỦNG", "45.2M VNĐ", ACCENT_COLOR));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createUltraStatsCard(String icon, String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ultra card background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, CARD_COLOR,
                    0, getHeight(), new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 20)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Ultra border
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(accentColor);
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 18, 18);
                
                super.paintComponent(g);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(40, 30, 40, 30));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(titleLabel);
        
        return card;
    }
    
    private static JPanel createUltraBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Ultra header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
        
        JLabel titleLabel = new JLabel("📚 QUẢN LÝ SÁCH ULTRA MODERN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        
        JButton addButton = createUltraButton("➕ THÊM SÁCH", SUCCESS_COLOR, new Color(67, 160, 71));
        JButton editButton = createUltraButton("✏️ SỬA", PRIMARY_COLOR, PRIMARY_DARK);
        JButton deleteButton = createUltraButton("🗑️ XÓA", DANGER_COLOR, new Color(211, 47, 47));
        
        actionsPanel.add(addButton);
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(actionsPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Ultra table
        JPanel tablePanel = createUltraTablePanel("books");
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createUltraUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("👥 QUẢN LÝ NGƯỜI DÙNG ULTRA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        // Ultra table for users
        JPanel tablePanel = createUltraTablePanel("users");
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 25)), BorderLayout.CENTER);
        panel.add(tablePanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private static JPanel createUltraTablePanel(String type) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ultra table panel background
                g2d.setColor(CARD_COLOR);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Ultra border
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
                
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Create ultra table
        JTable table;
        if ("books".equals(type)) {
            String[] columns = {"ID", "📚 TÊN SÁCH", "✍️ TÁC GIẢ", "📖 THỂ LOẠI", "💰 GIÁ", "📦 SỐ LƯỢNG"};
            bookTableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(bookTableModel);
            refreshBookTable();
        } else {
            String[] columns = {"ID", "👤 TÊN ĐĂNG NHẬP", "👨‍💼 HỌ TÊN", "🎭 VAI TRÒ", "🟢 TRẠNG THÁI"};
            userTableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(userTableModel);
            refreshUserTable();
        }
        
        // Ultra table styling
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(50);
        table.setSelectionBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 40));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 30));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        
        // Ultra table header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 60));
        
        // Ultra cell renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(240, 248, 255));
                    }
                }
                setBorder(new EmptyBorder(12, 18, 12, 18));
                setFont(new Font("Segoe UI", Font.PLAIN, 14));
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createUltraStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Ultra gradient status bar
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(67, 56, 202),
                    getWidth(), 0, new Color(147, 51, 234)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                super.paintComponent(g);
            }
        };
        statusBar.setOpaque(false);
        statusBar.setPreferredSize(new Dimension(0, 40));
        statusBar.setBorder(new EmptyBorder(10, 25, 10, 25));
        
        JLabel statusLabel = new JLabel("🌟 HỆ THỐNG ULTRA HOẠT ĐỘNG HOÀN HẢO");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(Color.WHITE);
        
        JLabel timeLabel = new JLabel("⏰ " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        timeLabel.setForeground(Color.WHITE);
        
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
