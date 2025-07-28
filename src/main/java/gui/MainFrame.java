package gui;

import model.User;
import model.User.UserRole;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Cửa sổ chính của ứng dụng
 */
public class MainFrame extends JFrame {
    private User currentUser;
    private UserService userService;
    private JTabbedPane tabbedPane;
    private BookManagementPanel bookPanel;
    private UserManagementPanel userPanel;
    
    public MainFrame(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        
        initComponents();
        setupLayout();
        setupMenuBar();
        setupEvents();
        
        showWelcomeMessage();
    }
    
    private void initComponents() {
        setTitle("Quản lý Cửa hàng Sách - " + currentUser.getFullName() + 
                " (" + currentUser.getRole().getDisplayName() + ")");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Tạo tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Panel quản lý sách
        bookPanel = new BookManagementPanel(currentUser);
        tabbedPane.addTab("📚 Quản lý Sách", bookPanel);
        
        // Panel quản lý người dùng (chỉ admin)
        if (currentUser.getRole() == UserRole.ADMIN) {
            userPanel = new UserManagementPanel(currentUser);
            tabbedPane.addTab("👥 Quản lý Người dùng", userPanel);
        }
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Panel chính
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 123, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Logo và tiêu đề
        JLabel lblTitle = new JLabel("📚 HỆ THỐNG QUẢN LÝ CỬA HÀNG SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        
        // Thông tin người dùng
        JLabel lblUser = new JLabel("Xin chào, " + currentUser.getFullName() + 
                " | " + currentUser.getRole().getDisplayName());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(Color.WHITE);
        
        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(lblUser, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel lblFooter = new JLabel("© 2025 Hệ thống Quản lý Cửa hàng Sách - Phiên bản 1.0");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(108, 117, 125));
        
        panel.add(lblFooter);
        return panel;
    }
    
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Hệ thống
        JMenu systemMenu = new JMenu("Hệ thống");
        systemMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Đổi mật khẩu
        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        changePasswordItem.addActionListener(e -> showChangePasswordDialog());
        systemMenu.add(changePasswordItem);
        
        systemMenu.addSeparator();
        
        // Đăng xuất
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutItem.addActionListener(e -> logout());
        systemMenu.add(logoutItem);
        
        // Thoát
        JMenuItem exitItem = new JMenuItem("Thoát");
        exitItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitItem.addActionListener(e -> exitApplication());
        systemMenu.add(exitItem);
        
        // Menu Trợ giúp
        JMenu helpMenu = new JMenu("Trợ giúp");
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JMenuItem aboutItem = new JMenuItem("Về chương trình");
        aboutItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(systemMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void setupEvents() {
        // Xử lý khi đóng cửa sổ
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }
    
    private void showWelcomeMessage() {
        SwingUtilities.invokeLater(() -> {
            String message = "Chào mừng " + currentUser.getFullName() + 
                    " đến với hệ thống quản lý cửa hàng sách!";
            
            JOptionPane.showMessageDialog(this, message, "Chào mừng", 
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog(this, "Đổi mật khẩu", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mật khẩu cũ
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Mật khẩu cũ:"), gbc);
        
        JPasswordField txtOldPassword = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtOldPassword, gbc);
        
        // Mật khẩu mới
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Mật khẩu mới:"), gbc);
        
        JPasswordField txtNewPassword = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNewPassword, gbc);
        
        // Xác nhận mật khẩu
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Xác nhận:"), gbc);
        
        JPasswordField txtConfirmPassword = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtConfirmPassword, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        
        btnSave.addActionListener(e -> {
            String oldPassword = new String(txtOldPassword.getPassword());
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            
            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu xác nhận không khớp!");
                return;
            }
            
            if (userService.changePassword(currentUser.getUserId(), oldPassword, newPassword)) {
                JOptionPane.showMessageDialog(dialog, "Đổi mật khẩu thành công!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Đổi mật khẩu thất bại!");
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void logout() {
        int option = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    private void exitApplication() {
        int option = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn thoát ứng dụng?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void showAboutDialog() {
        String message = "<html><center>" +
                "<h2>Hệ thống Quản lý Cửa hàng Sách</h2>" +
                "<p>Phiên bản: 1.0</p>" +
                "<p>Phát triển bằng Java Swing</p>" +
                "<p>Cơ sở dữ liệu: MySQL</p>" +
                "<p>© 2025 - Đồ án Lập trình Java</p>" +
                "</center></html>";
        
        JOptionPane.showMessageDialog(this, message, "Về chương trình", 
                JOptionPane.INFORMATION_MESSAGE);
    }
}
