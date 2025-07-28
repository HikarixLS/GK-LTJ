import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BookstoreAppNew {
    private static JFrame frame;
    private static String currentUser;
    private static String userRole;
    private static DefaultTableModel bookTableModel;
    private static DefaultTableModel userTableModel;
    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);      // Indigo
    private static final Color SECONDARY_COLOR = new Color(92, 107, 192);   // Light Indigo
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);      // Green
    private static final Color WARNING_COLOR = new Color(255, 152, 0);      // Orange
    private static final Color DANGER_COLOR = new Color(244, 67, 54);       // Red
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Light Gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    
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
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // If unable to set Windows look and feel, use default
            }
            
            initializeData();
            createLoginFrame();
        });
    }
    
    private static void initializeData() {
        // Initialize sample books
        books.add(new Book("1", "Lap trinh Java", "Nguyen Van A", "Cong nghe", "150,000", "25"));
        books.add(new Book("2", "Cau truc du lieu", "Tran Thi B", "Cong nghe", "200,000", "15"));
        books.add(new Book("3", "Triet hoc doi song", "Le Van C", "Triet hoc", "120,000", "30"));
        books.add(new Book("4", "Marketing hien dai", "Pham Thi D", "Kinh doanh", "180,000", "20"));
        
        // Initialize sample users
        users.add(new User("1", "admin", "Quan tri vien", "Admin", "Hoat dong"));
        users.add(new User("2", "manager", "Nguoi quan ly", "Manager", "Hoat dong"));
        users.add(new User("3", "user", "Nhan vien", "User", "Hoat dong"));
    }
    
    private static void createLoginFrame() {
        JFrame loginFrame = new JFrame("Bookstore Management System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 450);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_COLOR, 0, getHeight(), SECONDARY_COLOR);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Header with modern styling
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(30, 30, 20, 30));
        
        JLabel titleLabel = new JLabel("📚 BOOKSTORE", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Management System v2.0", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Login form card
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(20, 40, 20, 40),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 20), 1),
                new EmptyBorder(30, 30, 30, 30)
            )
        ));
        
        // Add subtle shadow effect
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(0, 0, 0, 20)),
            cardPanel.getBorder()
        ));
        
        // Username field
        JLabel userLabel = new JLabel("Tên đăng nhập");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(TEXT_PRIMARY);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField usernameField = createStyledTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Password field
        JLabel passLabel = new JLabel("Mật khẩu");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(TEXT_PRIMARY);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passwordField = createStyledPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Login button
        JButton loginButton = createPrimaryButton("ĐĂNG NHẬP");
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Account info
        JLabel infoLabel = new JLabel("<html><center><b>Tài khoản demo:</b><br/>" +
            "admin/admin123 • manager/manager123 • user/user123<br/>" +
            "<br/><span style='color: #4CAF50;'><b>✨ PHIÊN BẢN CÓ CHỨC NĂNG THỰC SỰ!</b></span></center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel.setForeground(TEXT_SECONDARY);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components with spacing
        cardPanel.add(userLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(usernameField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(passLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(passwordField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(infoLabel);
        
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        // Login action
        ActionListener loginAction = e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (authenticateUser(username, password)) {
                currentUser = username;
                loginFrame.dispose();
                createMainFrame();
            } else {
                showErrorDialog(loginFrame, "Đăng nhập thất bại!", 
                    "Tài khoản hoặc mật khẩu không đúng.\nVui lòng thử lại.");
            }
        };
        
        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }
    
    private static boolean authenticateUser(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            userRole = "Quan tri vien";
            return true;
        } else if ("manager".equals(username) && "manager123".equals(password)) {
            userRole = "Nguoi quan ly";
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            userRole = "Nhan vien";
            return true;
        }
        return false;
    }
    
    // Helper methods for modern UI components
    private static JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        textField.setForeground(TEXT_PRIMARY);
        return textField;
    }
    
    private static JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        passwordField.setForeground(TEXT_PRIMARY);
        return passwordField;
    }
    
    private static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    private static JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
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
    
    private static void showSuccessDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void showErrorDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private static void showInfoDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static int showConfirmDialog(Component parent, String title, String message) {
        return JOptionPane.showConfirmDialog(parent, message, title, 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    private static void createMainFrame() {
        frame = new JFrame("📚 Bookstore Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BACKGROUND_COLOR);
        
        // Modern menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(CARD_COLOR);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 20)));
        
        JMenu fileMenu = new JMenu("📁 Tệp");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fileMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem exitItem = new JMenuItem("🚪 Thoát");
        exitItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        exitItem.addActionListener(e -> {
            int result = showConfirmDialog(frame, "Xác nhận thoát", "Bạn có chắc chắn muốn thoát khỏi ứng dụng?");
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        
        JMenu helpMenu = new JMenu("❓ Trợ giúp");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        helpMenu.setForeground(TEXT_PRIMARY);
        
        JMenuItem aboutItem = new JMenuItem("ℹ️ Giới thiệu");
        aboutItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        aboutItem.addActionListener(e -> showInfoDialog(frame, "Về ứng dụng",
            "📚 BOOKSTORE MANAGEMENT SYSTEM v2.0\n\n" +
            "🎯 Hệ thống quản lý cửa hàng sách chuyên nghiệp\n" +
            "✨ Với các chức năng CRUD đầy đủ\n" +
            "🚀 Giao diện hiện đại và thân thiện\n\n" +
            "💡 Tất cả các nút đã hoạt động thực sự!\n" +
            "🔧 Phát triển bởi: Team Java Developer"));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        frame.setJMenuBar(menuBar);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header panel with user info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel welcomeLabel = new JLabel("👋 Chào mừng, " + currentUser + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(TEXT_PRIMARY);
        
        JLabel roleLabel = new JLabel("🎭 Vai trò: " + userRole + " | 🟢 Đang hoạt động");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(TEXT_SECONDARY);
        
        JPanel userInfoPanel = new JPanel(new BorderLayout());
        userInfoPanel.setOpaque(false);
        userInfoPanel.add(welcomeLabel, BorderLayout.NORTH);
        userInfoPanel.add(roleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        
        // Add current time
        JLabel timeLabel = new JLabel("🕐 " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);
        headerPanel.add(timeLabel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Tabbed pane with modern styling
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        // Book management tab
        tabbedPane.addTab("📚 Quản lý sách", createBookPanel());
        
        // User management tab (only for admin and manager)
        if (!"Nhan vien".equals(userRole)) {
            tabbedPane.addTab("👥 Quản lý người dùng", createUserPanel());
        }
        
        // Statistics tab
        tabbedPane.addTab("📊 Thống kê", createStatsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private static JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel titleLabel = new JLabel("📚 Quản lý sách");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        JLabel countLabel = new JLabel("Tổng số sách: " + books.size());
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(TEXT_SECONDARY);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(countLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_COLOR);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Modern table
        String[] columns = {"ID", "📖 Tên sách", "✍️ Tác giả", "🏷️ Thể loại", "💰 Giá", "📦 Số lượng"};
        bookTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(bookTableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 30));
        table.setSelectionForeground(TEXT_PRIMARY);
        
        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(BACKGROUND_COLOR);
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }
                
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        });
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Load data
        refreshBookTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        
        // Button panel with modern design
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JButton addButton = createStyledButton("➕ THÊM SÁCH", SUCCESS_COLOR, new Color(67, 160, 71));
        addButton.setPreferredSize(new Dimension(140, 45));
        addButton.addActionListener(e -> addBook());
        
        JButton editButton = createStyledButton("✏️ SỬA SÁCH", PRIMARY_COLOR, SECONDARY_COLOR);
        editButton.setPreferredSize(new Dimension(140, 45));
        editButton.addActionListener(e -> editBook(table));
        
        JButton deleteButton = createStyledButton("🗑️ XÓA SÁCH", DANGER_COLOR, new Color(211, 47, 47));
        deleteButton.setPreferredSize(new Dimension(140, 45));
        deleteButton.addActionListener(e -> deleteBook(table));
        
        JButton refreshButton = createStyledButton("🔄 LÀM MỚI", WARNING_COLOR, new Color(245, 124, 0));
        refreshButton.setPreferredSize(new Dimension(140, 45));
        refreshButton.addActionListener(e -> {
            refreshBookTable();
            // Update count label - simplified approach
            frame.repaint();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private static void addBook() {
        JDialog dialog = new JDialog(frame, "Thêm sách mới", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(frame);
        dialog.setBackground(BACKGROUND_COLOR);
        
        // Main panel with modern design
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Header
        JLabel headerLabel = new JLabel("📖 Thêm sách mới", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(TEXT_PRIMARY);
        headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create form fields
        JTextField titleField = createStyledTextField();
        titleField.setPreferredSize(new Dimension(280, 35));
        
        JTextField authorField = createStyledTextField();
        authorField.setPreferredSize(new Dimension(280, 35));
        
        JTextField categoryField = createStyledTextField();
        categoryField.setPreferredSize(new Dimension(280, 35));
        
        JTextField priceField = createStyledTextField();
        priceField.setPreferredSize(new Dimension(280, 35));
        
        JTextField quantityField = createStyledTextField();
        quantityField.setPreferredSize(new Dimension(280, 35));
        
        // Add form components
        addFormRow(formPanel, gbc, 0, "📖 Tên sách:", titleField);
        addFormRow(formPanel, gbc, 1, "✍️ Tác giả:", authorField);
        addFormRow(formPanel, gbc, 2, "🏷️ Thể loại:", categoryField);
        addFormRow(formPanel, gbc, 3, "💰 Giá:", priceField);
        addFormRow(formPanel, gbc, 4, "📦 Số lượng:", quantityField);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton saveButton = createStyledButton("💾 LUU", SUCCESS_COLOR, new Color(67, 160, 71));
        saveButton.setPreferredSize(new Dimension(120, 40));
        
        JButton cancelButton = createStyledButton("❌ HUY", DANGER_COLOR, new Color(211, 47, 47));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        
        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            String price = priceField.getText().trim();
            String quantity = quantityField.getText().trim();
            
            if (title.isEmpty() || author.isEmpty()) {
                showErrorDialog(dialog, "Lỗi nhập liệu", "Tên sách và tác giả không được để trống!");
                return;
            }
            
            String newId = String.valueOf(books.size() + 1);
            Book newBook = new Book(newId, title, author, category, price, quantity);
            books.add(newBook);
            refreshBookTable();
            
            showSuccessDialog(dialog, "Thành công", 
                "✅ Đã thêm sách thành công!\n\n" +
                "📖 Tên sách: " + title + "\n" +
                "✍️ Tác giả: " + author + "\n" +
                "🏷️ Thể loại: " + category + "\n" +
                "💰 Giá: " + price + "\n" +
                "📦 Số lượng: " + quantity);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private static void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_PRIMARY);
        panel.add(label, gbc);
        
        gbc.gridx = 1; gbc.gridy = row;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private static void editBook(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon sach can sua!", "Thong bao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String id = (String) table.getValueAt(selectedRow, 0);
        Book book = books.stream().filter(b -> b.id.equals(id)).findFirst().orElse(null);
        
        if (book == null) return;
        
        JDialog dialog = new JDialog(frame, "Sua thong tin sach", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField titleField = new JTextField(book.title, 20);
        JTextField authorField = new JTextField(book.author, 20);
        JTextField categoryField = new JTextField(book.category, 20);
        JTextField priceField = new JTextField(book.price, 20);
        JTextField quantityField = new JTextField(book.quantity, 20);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Ten sach:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Tac gia:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("The loai:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(categoryField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Gia:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("So luong:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(quantityField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("CAP NHAT");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        
        JButton cancelButton = new JButton("HUY");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> {
            String oldTitle = book.title;
            book.title = titleField.getText().trim();
            book.author = authorField.getText().trim();
            book.category = categoryField.getText().trim();
            book.price = priceField.getText().trim();
            book.quantity = quantityField.getText().trim();
            
            refreshBookTable();
            JOptionPane.showMessageDialog(dialog, 
                "========== CAP NHAT SACH THANH CONG ==========\n\n" +
                "Sach cu: " + oldTitle + "\n" +
                "Sach moi: " + book.title + "\n\n" +
                "THONG TIN DA DUOC CAP NHAT!",
                "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void deleteBook(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon sach can xoa!", "Thong bao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String bookTitle = (String) table.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(frame,
            "======== XAC NHAN XOA SACH ========\n\n" +
            "Ban co chac chan muon xoa sach:\n" + 
            "\"" + bookTitle + "\"?\n\n" +
            "Hanh dong nay KHONG THE HOAN TAC!",
            "Xac nhan xoa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) table.getValueAt(selectedRow, 0);
            books.removeIf(b -> b.id.equals(id));
            refreshBookTable();
            JOptionPane.showMessageDialog(frame, 
                "========== XOA SACH THANH CONG ==========\n\n" +
                "Sach \"" + bookTitle + "\" da duoc xoa khoi he thong!\n\n" +
                "So sach con lai: " + books.size(),
                "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void refreshBookTable() {
        bookTableModel.setRowCount(0);
        for (Book book : books) {
            bookTableModel.addRow(book.toArray());
        }
        showSuccessDialog(frame,
            "✅ Làm mới thành công", 
            "📊 Danh sách sách đã được cập nhật!\n" +
            "📚 Tổng số sách: " + books.size() + " cuốn");
    }
    
    private static JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Stats cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(BACKGROUND_COLOR);
        
        // Book count card
        JPanel bookCard = createStatCard("📚", "Tổng số sách", String.valueOf(books.size()), PRIMARY_COLOR);
        
        // User count card  
        JPanel userCard = createStatCard("👥", "Người dùng", String.valueOf(users.size()), SUCCESS_COLOR);
        
        // Category count card
        long categoryCount = books.stream().map(book -> book.category).distinct().count();
        JPanel categoryCard = createStatCard("🏷️", "Thể loại", String.valueOf(categoryCount), WARNING_COLOR);
        
        // Active status card
        JPanel statusCard = createStatCard("🟢", "Trạng thái", "Hoạt động", new Color(67, 160, 71));
        
        statsPanel.add(bookCard);
        statsPanel.add(userCard);
        statsPanel.add(categoryCard);
        statsPanel.add(statusCard);
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private static JPanel createStatCard(String icon, String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            new EmptyBorder(30, 25, 30, 25)
        ));
        
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        
        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(valueLabel, BorderLayout.CENTER);
        textPanel.add(titleLabel, BorderLayout.SOUTH);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private static JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table
        String[] columns = {"ID", "Username", "Ho ten", "Vai tro", "Trang thai"};
        userTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(userTableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Load data
        refreshUserTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("THEM USER");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.addActionListener(e -> addUser());
        
        JButton editButton = new JButton("SUA USER");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setBackground(Color.BLUE);
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(120, 40));
        editButton.addActionListener(e -> editUser(table));
        
        JButton refreshButton = new JButton("LAM MOI");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setBackground(Color.GRAY);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> refreshUserTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private static void addUser() {
        JDialog dialog = new JDialog(frame, "Them nguoi dung moi", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField usernameField = new JTextField(20);
        JTextField fullNameField = new JTextField(20);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Admin", "Manager", "User"});
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Ho ten:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(fullNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Vai tro:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(roleCombo, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("THEM");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        saveButton.setBackground(Color.GREEN);
        saveButton.setForeground(Color.WHITE);
        
        JButton cancelButton = new JButton("HUY");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String role = (String) roleCombo.getSelectedItem();
            
            if (username.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username va ho ten khong duoc de trong!", "Loi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String newId = String.valueOf(users.size() + 1);
            User newUser = new User(newId, username, fullName, role, "Hoat dong");
            users.add(newUser);
            refreshUserTable();
            
            JOptionPane.showMessageDialog(dialog, 
                "========== THEM USER THANH CONG ==========\n\n" +
                "Username: " + username + "\n" +
                "Ho ten: " + fullName + "\n" +
                "Vai tro: " + role + "\n\n" +
                "USER DA DUOC THEM VAO HE THONG!",
                "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void editUser(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui long chon nguoi dung can sua!", "Thong bao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String id = (String) table.getValueAt(selectedRow, 0);
        User user = users.stream().filter(u -> u.id.equals(id)).findFirst().orElse(null);
        
        if (user == null) return;
        
        JDialog dialog = new JDialog(frame, "Sua thong tin nguoi dung", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField usernameField = new JTextField(user.username, 20);
        JTextField fullNameField = new JTextField(user.fullName, 20);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Admin", "Manager", "User"});
        roleCombo.setSelectedItem(user.role);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Hoat dong", "Tam khoa"});
        statusCombo.setSelectedItem(user.status);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Ho ten:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(fullNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Vai tro:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(roleCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Trang thai:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(statusCombo, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("CAP NHAT");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        
        JButton cancelButton = new JButton("HUY");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        
        saveButton.addActionListener(e -> {
            String oldUsername = user.username;
            user.username = usernameField.getText().trim();
            user.fullName = fullNameField.getText().trim();
            user.role = (String) roleCombo.getSelectedItem();
            user.status = (String) statusCombo.getSelectedItem();
            
            refreshUserTable();
            JOptionPane.showMessageDialog(dialog, 
                "========== CAP NHAT USER THANH CONG ==========\n\n" +
                "User cu: " + oldUsername + "\n" +
                "User moi: " + user.username + "\n\n" +
                "THONG TIN DA DUOC CAP NHAT!",
                "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private static void refreshUserTable() {
        userTableModel.setRowCount(0);
        for (User user : users) {
            userTableModel.addRow(user.toArray());
        }
        JOptionPane.showMessageDialog(frame,
            "========== LAM MOI USER ==========\n\n" +
            "Danh sach nguoi dung da duoc cap nhat!\n" +
            "Tong so user: " + users.size(),
            "Lam moi thanh cong", JOptionPane.INFORMATION_MESSAGE);
    }
}
