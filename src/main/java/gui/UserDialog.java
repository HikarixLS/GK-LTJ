package gui;

import model.User;
import model.User.UserRole;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

/**
 * Dialog để thêm/sửa thông tin người dùng
 */
public class UserDialog extends JDialog {
    private User user;
    private boolean confirmed = false;
    private boolean isEditMode = false;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JComboBox<UserRole> cmbRole;
    private JCheckBox chkActive;
    private JButton btnSave;
    private JButton btnCancel;
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10,11}$");
    
    public UserDialog(Window parent, String title, User user) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.user = user;
        this.isEditMode = (user != null);
        
        initComponents();
        setupLayout();
        setupEvents();
        
        if (user != null) {
            populateFields();
        }
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo components
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtConfirmPassword = new JPasswordField(20);
        txtFullName = new JTextField(25);
        txtEmail = new JTextField(25);
        txtPhone = new JTextField(15);
        
        cmbRole = new JComboBox<>(UserRole.values());
        chkActive = new JCheckBox("Tài khoản hoạt động", true);
        
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        
        // Thiết lập font
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        txtUsername.setFont(font);
        txtPassword.setFont(font);
        txtConfirmPassword.setFont(font);
        txtFullName.setFont(font);
        txtEmail.setFont(font);
        txtPhone.setFont(font);
        cmbRole.setFont(font);
        chkActive.setFont(font);
        btnSave.setFont(font);
        btnCancel.setFont(font);
        
        // Thiết lập màu buttons
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        
        // Thiết lập tooltip
        txtEmail.setToolTipText("VD: example@email.com");
        txtPhone.setToolTipText("10-11 chữ số, VD: 0123456789");
        
        // Nếu đang sửa, không cho phép thay đổi username
        if (isEditMode) {
            txtUsername.setEditable(false);
            txtUsername.setBackground(new Color(248, 249, 250));
        }
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Tên đăng nhập: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtUsername, gbc);
        
        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        String passwordLabel = isEditMode ? "Mật khẩu mới:" : "Mật khẩu: *";
        mainPanel.add(new JLabel(passwordLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtPassword, gbc);
        
        // Xác nhận mật khẩu
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        String confirmLabel = isEditMode ? "Xác nhận mật khẩu mới:" : "Xác nhận mật khẩu: *";
        mainPanel.add(new JLabel(confirmLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtConfirmPassword, gbc);
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Họ tên: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtFullName, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtEmail, gbc);
        
        // Điện thoại
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtPhone, gbc);
        
        // Vai trò
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Vai trò: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(cmbRole, gbc);
        
        // Trạng thái hoạt động
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(chkActive, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Ghi chú
        String noteText = isEditMode ? 
            "(*) Trường bắt buộc - Để trống mật khẩu nếu không muốn thay đổi" :
            "(*) Trường bắt buộc";
        JLabel lblNote = new JLabel(noteText);
        lblNote.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNote.setForeground(Color.RED);
        lblNote.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        add(lblNote, BorderLayout.NORTH);
    }
    
    private void setupEvents() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter để lưu, Escape để hủy
        getRootPane().setDefaultButton(btnSave);
        
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void populateFields() {
        if (user != null) {
            txtUsername.setText(user.getUsername());
            txtFullName.setText(user.getFullName());
            txtEmail.setText(user.getEmail() != null ? user.getEmail() : "");
            txtPhone.setText(user.getPhone() != null ? user.getPhone() : "");
            cmbRole.setSelectedItem(user.getRole());
            chkActive.setSelected(user.isActive());
        }
    }
    
    private void saveUser() {
        // Validate dữ liệu
        if (!validateInput()) {
            return;
        }
        
        try {
            // Tạo hoặc cập nhật user object
            if (user == null) {
                user = new User();
            }
            
            user.setUsername(txtUsername.getText().trim());
            
            // Xử lý mật khẩu
            String password = new String(txtPassword.getPassword());
            if (!isEditMode || !password.isEmpty()) {
                user.setPassword(password);
            }
            
            user.setFullName(txtFullName.getText().trim());
            
            String email = txtEmail.getText().trim();
            user.setEmail(email.isEmpty() ? null : email);
            
            String phone = txtPhone.getText().trim();
            user.setPhone(phone.isEmpty() ? null : phone);
            
            user.setRole((UserRole) cmbRole.getSelectedItem());
            user.setActive(chkActive.isSelected());
            
            confirmed = true;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Có lỗi xảy ra: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        // Kiểm tra tên đăng nhập
        String username = txtUsername.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập!");
            txtUsername.requestFocus();
            return false;
        }
        
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập phải có ít nhất 3 ký tự!");
            txtUsername.requestFocus();
            return false;
        }
        
        // Kiểm tra mật khẩu
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        if (!isEditMode) {
            // Thêm mới: mật khẩu bắt buộc
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
                txtPassword.requestFocus();
                return false;
            }
        } else {
            // Sửa: chỉ kiểm tra nếu có nhập mật khẩu mới
            if (!password.isEmpty() || !confirmPassword.isEmpty()) {
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!");
                    txtPassword.requestFocus();
                    return false;
                }
            }
        }
        
        if (!password.isEmpty()) {
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!");
                txtPassword.requestFocus();
                return false;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
                txtConfirmPassword.requestFocus();
                return false;
            }
        }
        
        // Kiểm tra họ tên
        if (txtFullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
            txtFullName.requestFocus();
            return false;
        }
        
        // Kiểm tra email nếu có nhập
        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
            txtEmail.requestFocus();
            return false;
        }
        
        // Kiểm tra số điện thoại nếu có nhập
        String phone = txtPhone.getText().trim();
        if (!phone.isEmpty() && !PHONE_PATTERN.matcher(phone).matches()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (10-11 chữ số)!");
            txtPhone.requestFocus();
            return false;
        }
        
        // Kiểm tra vai trò
        if (cmbRole.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            cmbRole.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public User getUser() {
        return user;
    }
}
