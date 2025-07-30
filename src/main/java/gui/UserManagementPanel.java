package gui;

import model.User;
import model.User.UserRole;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel quản lý người dùng (chỉ dành cho Admin)
 */
public class UserManagementPanel extends JPanel {
    private User currentUser;
    private UserService userService;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField txtSearch;
    private JComboBox<UserRole> cmbRole;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    
    private String[] columnNames = {
        "ID", "Tên đăng nhập", "Họ tên", "Email", "Điện thoại", 
        "Vai trò", "Trạng thái", "Ngày tạo", "Đăng nhập cuối"
    };
    
    public UserManagementPanel(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        
        initComponents();
        setupLayout();
        setupEvents();
        loadUsers();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Tạo table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.setRowHeight(25);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Thiết lập độ rộng cột
        userTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        userTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Username
        userTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Họ tên
        userTable.getColumnModel().getColumn(3).setPreferredWidth(180); // Email
        userTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Phone
        userTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Role
        userTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Status
        userTable.getColumnModel().getColumn(7).setPreferredWidth(120); // Created
        userTable.getColumnModel().getColumn(8).setPreferredWidth(120); // Last login
        
        // Tạo row sorter
        rowSorter = new TableRowSorter<>(tableModel);
        userTable.setRowSorter(rowSorter);
        
        // Tạo components tìm kiếm
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbRole = new JComboBox<>();
        cmbRole.addItem(null); // Tất cả
        for (UserRole role : UserRole.values()) {
            cmbRole.addItem(role);
        }
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Tạo buttons
        btnAdd = new JButton("➕ Thêm");
        btnEdit = new JButton("✏️ Sửa");
        btnDelete = new JButton("🗑️ Xóa");
        btnRefresh = new JButton("🔄 Làm mới");
        
        // Thiết lập font và màu cho buttons
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);
        btnAdd.setFont(buttonFont);
        btnEdit.setFont(buttonFont);
        btnDelete.setFont(buttonFont);
        btnRefresh.setFont(buttonFont);
        
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(255, 193, 7));
        btnEdit.setForeground(Color.BLACK);
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnRefresh.setBackground(new Color(23, 162, 184));
        btnRefresh.setForeground(Color.WHITE);
        
        updateButtonStates();
    }
    
    private void setupLayout() {
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm và Lọc"));
        
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Vai trò:"));
        searchPanel.add(cmbRole);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        
        // Panel phía trên
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel table
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách người dùng"));
        
        // Panel thống kê
        JPanel statsPanel = createStatsPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Thống kê"));
        panel.setPreferredSize(new Dimension(0, 60));
        
        JLabel lblTotalUsers = new JLabel("Tổng số người dùng: 0");
        JLabel lblActiveUsers = new JLabel("Đang hoạt động: 0");
        JLabel lblAdmins = new JLabel("Quản trị viên: 0");
        
        lblTotalUsers.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblActiveUsers.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAdmins.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        panel.add(lblTotalUsers);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(lblActiveUsers);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(lblAdmins);
        
        return panel;
    }
    
    private void setupEvents() {
        // Tìm kiếm
        txtSearch.addActionListener(e -> filterUsers());
        
        // Lọc theo vai trò
        cmbRole.addActionListener(e -> filterUsers());
        
        // Double click để sửa
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editUser();
                }
            }
        });
        
        // Selection change
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        // Button events
        btnAdd.addActionListener(e -> addUser());
        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> loadUsers());
    }
    
    private void loadUsers() {
        SwingWorker<List<User>, Void> worker = new SwingWorker<List<User>, Void>() {
            @Override
            protected List<User> doInBackground() throws Exception {
                return userService.getAllUsers();
            }
            
            @Override
            protected void done() {
                try {
                    List<User> users = get();
                    updateTable(users);
                    updateStats(users);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(UserManagementPanel.this,
                            "Lỗi khi tải dữ liệu: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
    
    private void updateTable(List<User> users) {
        tableModel.setRowCount(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (User user : users) {
            Object[] row = {
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail() != null ? user.getEmail() : "",
                user.getPhone() != null ? user.getPhone() : "",
                user.getRole().getDisplayName(),
                user.isActive() ? "Hoạt động" : "Vô hiệu",
                user.getCreatedAt() != null ? user.getCreatedAt().format(formatter) : "",
                user.getLastLogin() != null ? user.getLastLogin().format(formatter) : "Chưa đăng nhập"
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateStats(List<User> users) {
        int totalUsers = users.size();
        long activeUsers = users.stream().filter(User::isActive).count();
        long admins = users.stream().filter(u -> u.getRole() == UserRole.ADMIN).count();
        
        Component[] components = ((JPanel) getComponent(2)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Tổng số người dùng:")) {
                    label.setText("Tổng số người dùng: " + totalUsers);
                } else if (label.getText().startsWith("Đang hoạt động:")) {
                    label.setText("Đang hoạt động: " + activeUsers);
                } else if (label.getText().startsWith("Quản trị viên:")) {
                    label.setText("Quản trị viên: " + admins);
                }
            }
        }
    }
    
    private void filterUsers() {
        String searchText = txtSearch.getText().trim();
        UserRole selectedRole = (UserRole) cmbRole.getSelectedItem();
        
        if (searchText.isEmpty() && selectedRole == null) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    // Kiểm tra từ khóa tìm kiếm
                    boolean matchSearch = searchText.isEmpty();
                    if (!matchSearch) {
                        for (int i = 1; i < entry.getValueCount() - 1; i++) { // Bỏ qua ID và cột cuối
                            String value = entry.getStringValue(i).toLowerCase();
                            if (value.contains(searchText.toLowerCase())) {
                                matchSearch = true;
                                break;
                            }
                        }
                    }
                    
                    // Kiểm tra vai trò
                    boolean matchRole = selectedRole == null ||
                            entry.getStringValue(5).equals(selectedRole.getDisplayName());
                    
                    return matchSearch && matchRole;
                }
            };
            
            rowSorter.setRowFilter(filter);
        }
    }
    
    private void addUser() {
        UserDialog dialog = new UserDialog(SwingUtilities.getWindowAncestor(this), "Thêm người dùng mới", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            User user = dialog.getUser();
            if (userService.registerUser(user)) {
                JOptionPane.showMessageDialog(this, "Thêm người dùng thành công!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần sửa!");
            return;
        }
        
        int modelRow = userTable.convertRowIndexToModel(selectedRow);
        int userId = (Integer) tableModel.getValueAt(modelRow, 0);
        
        User user = userService.getUserById(userId);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UserDialog dialog = new UserDialog(SwingUtilities.getWindowAncestor(this), "Sửa thông tin người dùng", user);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            User updatedUser = dialog.getUser();
            updatedUser.setUserId(userId);
            
            if (userService.updateUser(updatedUser)) {
                JOptionPane.showMessageDialog(this, "Cập nhật người dùng thành công!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng cần xóa!");
            return;
        }
        
        int modelRow = userTable.convertRowIndexToModel(selectedRow);
        int userId = (Integer) tableModel.getValueAt(modelRow, 0);
        String username = (String) tableModel.getValueAt(modelRow, 1);
        
        // Không cho phép xóa chính mình
        if (userId == currentUser.getUserId()) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản của chính mình!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn vô hiệu hóa tài khoản \"" + username + "\"?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            if (userService.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "Vô hiệu hóa tài khoản thành công!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Vô hiệu hóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateButtonStates() {
        boolean hasSelection = userTable.getSelectedRow() != -1;
        btnEdit.setEnabled(hasSelection);
        btnDelete.setEnabled(hasSelection);
    }
}
