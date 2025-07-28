package gui;

import model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Dialog để thêm/sửa thông tin sách
 */
public class BookDialog extends JDialog {
    private Book book;
    private boolean confirmed = false;
    
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtIsbn;
    private JComboBox<String> cmbCategory;
    private JTextField txtPrice;
    private JSpinner spnQuantity;
    private JTextField txtPublisher;
    private JTextField txtPublishDate;
    private JTextArea txtDescription;
    private JButton btnSave;
    private JButton btnCancel;
    
    private String[] categories = {
        "Văn học", "Khoa học", "Công nghệ", "Kinh tế", 
        "Lịch sử", "Tâm lý", "Giáo dục", "Thiếu nhi", "Khác"
    };
    
    public BookDialog(Window parent, String title, Book book) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.book = book;
        
        initComponents();
        setupLayout();
        setupEvents();
        
        if (book != null) {
            populateFields();
        }
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo components
        txtTitle = new JTextField(30);
        txtAuthor = new JTextField(30);
        txtIsbn = new JTextField(20);
        cmbCategory = new JComboBox<>(categories);
        txtPrice = new JTextField(15);
        spnQuantity = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        txtPublisher = new JTextField(30);
        txtPublishDate = new JTextField(15);
        txtDescription = new JTextArea(4, 30);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        
        // Thiết lập font
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        txtTitle.setFont(font);
        txtAuthor.setFont(font);
        txtIsbn.setFont(font);
        cmbCategory.setFont(font);
        txtPrice.setFont(font);
        spnQuantity.setFont(font);
        txtPublisher.setFont(font);
        txtPublishDate.setFont(font);
        txtDescription.setFont(font);
        btnSave.setFont(font);
        btnCancel.setFont(font);
        
        // Thiết lập màu buttons
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        
        // Thiết lập placeholder cho ngày
        txtPublishDate.setToolTipText("Định dạng: dd/MM/yyyy (VD: 15/03/2023)");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tên sách
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Tên sách: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtTitle, gbc);
        
        // Tác giả
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Tác giả: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtAuthor, gbc);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("ISBN: *"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtIsbn, gbc);
        
        // Danh mục
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(cmbCategory, gbc);
        
        // Giá và số lượng trên cùng một hàng
        JPanel priceQuantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        priceQuantityPanel.add(new JLabel("Giá (VND): "));
        priceQuantityPanel.add(txtPrice);
        priceQuantityPanel.add(Box.createHorizontalStrut(20));
        priceQuantityPanel.add(new JLabel("Số lượng: "));
        priceQuantityPanel.add(spnQuantity);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(priceQuantityPanel, gbc);
        
        // Nhà xuất bản
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Nhà xuất bản:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtPublisher, gbc);
        
        // Ngày xuất bản
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Ngày xuất bản:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtPublishDate, gbc);
        
        // Mô tả
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(new JScrollPane(txtDescription), gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Ghi chú
        JLabel lblNote = new JLabel("(*) Trường bắt buộc");
        lblNote.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNote.setForeground(Color.RED);
        lblNote.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        add(lblNote, BorderLayout.NORTH);
    }
    
    private void setupEvents() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBook();
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
        if (book != null) {
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtIsbn.setText(book.getIsbn());
            
            if (book.getCategory() != null) {
                cmbCategory.setSelectedItem(book.getCategory());
            }
            
            if (book.getPrice() != null) {
                txtPrice.setText(String.format("%.0f", book.getPrice().doubleValue()));
            }
            
            spnQuantity.setValue(book.getQuantity());
            txtPublisher.setText(book.getPublisher());
            
            if (book.getPublishDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtPublishDate.setText(book.getPublishDate().format(formatter));
            }
            
            txtDescription.setText(book.getDescription());
        }
    }
    
    private void saveBook() {
        // Validate dữ liệu
        if (!validateInput()) {
            return;
        }
        
        try {
            // Tạo hoặc cập nhật book object
            if (book == null) {
                book = new Book();
            }
            
            book.setTitle(txtTitle.getText().trim());
            book.setAuthor(txtAuthor.getText().trim());
            book.setIsbn(txtIsbn.getText().trim());
            book.setCategory((String) cmbCategory.getSelectedItem());
            book.setPrice(new BigDecimal(txtPrice.getText().trim()));
            book.setQuantity((Integer) spnQuantity.getValue());
            book.setPublisher(txtPublisher.getText().trim());
            
            // Parse ngày xuất bản
            String dateStr = txtPublishDate.getText().trim();
            if (!dateStr.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    book.setPublishDate(LocalDate.parse(dateStr, formatter));
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, 
                            "Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    txtPublishDate.requestFocus();
                    return;
                }
            }
            
            book.setDescription(txtDescription.getText().trim());
            
            confirmed = true;
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Giá sách phải là số hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPrice.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Có lỗi xảy ra: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        // Kiểm tra tên sách
        if (txtTitle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!");
            txtTitle.requestFocus();
            return false;
        }
        
        // Kiểm tra tác giả
        if (txtAuthor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tác giả!");
            txtAuthor.requestFocus();
            return false;
        }
        
        // Kiểm tra ISBN
        if (txtIsbn.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ISBN!");
            txtIsbn.requestFocus();
            return false;
        }
        
        // Kiểm tra giá
        String priceText = txtPrice.getText().trim();
        if (!priceText.isEmpty()) {
            try {
                double price = Double.parseDouble(priceText);
                if (price < 0) {
                    JOptionPane.showMessageDialog(this, "Giá sách không thể âm!");
                    txtPrice.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá sách phải là số hợp lệ!");
                txtPrice.requestFocus();
                return false;
            }
        }
        
        // Kiểm tra định dạng ngày nếu có nhập
        String dateStr = txtPublishDate.getText().trim();
        if (!dateStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, 
                        "Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtPublishDate.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Book getBook() {
        return book;
    }
}
