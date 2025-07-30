package gui;

import model.Book;
import model.User;
import model.User.UserRole;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel quản lý sách
 */
public class BookManagementPanel extends JPanel {
    private User currentUser;
    private BookService bookService;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField txtSearch;
    private JComboBox<String> cmbCategory;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    
    private String[] columnNames = {
        "ID", "Tên sách", "Tác giả", "ISBN", "Danh mục", 
        "Giá (VND)", "Số lượng", "Nhà xuất bản", "Ngày XB"
    };
    
    private String[] categories = {
        "Tất cả", "Văn học", "Khoa học", "Công nghệ", "Kinh tế", 
        "Lịch sử", "Tâm lý", "Giáo dục", "Thiếu nhi", "Khác"
    };
    
    public BookManagementPanel(User user) {
        this.currentUser = user;
        this.bookService = new BookService();
        
        initComponents();
        setupLayout();
        setupEvents();
        loadBooks();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Tạo table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp
            }
        };
        
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.getTableHeader().setReorderingAllowed(false);
        bookTable.setRowHeight(25);
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Thiết lập độ rộng cột
        bookTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên sách
        bookTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Tác giả
        bookTable.getColumnModel().getColumn(3).setPreferredWidth(120); // ISBN
        bookTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Danh mục
        bookTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Giá
        bookTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Số lượng
        bookTable.getColumnModel().getColumn(7).setPreferredWidth(150); // Nhà XB
        bookTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Ngày XB
        
        // Tạo row sorter cho tìm kiếm và sắp xếp
        rowSorter = new TableRowSorter<>(tableModel);
        bookTable.setRowSorter(rowSorter);
        
        // Tạo components tìm kiếm
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
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
        
        // Disable một số button nếu không phải admin
        if (currentUser.getRole() != UserRole.ADMIN) {
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }
        
        updateButtonStates();
    }
    
    private void setupLayout() {
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm và Lọc"));
        
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Danh mục:"));
        searchPanel.add(cmbCategory);
        
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
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sách"));
        
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
        
        JLabel lblTotalBooks = new JLabel("Tổng số sách: 0");
        JLabel lblTotalQuantity = new JLabel("Tổng số lượng: 0");
        
        lblTotalBooks.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotalQuantity.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        panel.add(lblTotalBooks);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(lblTotalQuantity);
        
        return panel;
    }
    
    private void setupEvents() {
        // Tìm kiếm
        txtSearch.addActionListener(e -> filterBooks());
        
        // Lọc theo danh mục
        cmbCategory.addActionListener(e -> filterBooks());
        
        // Double click để sửa
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && currentUser.getRole() == UserRole.ADMIN) {
                    editBook();
                }
            }
        });
        
        // Selection change
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
        
        // Button events
        btnAdd.addActionListener(e -> addBook());
        btnEdit.addActionListener(e -> editBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnRefresh.addActionListener(e -> loadBooks());
    }
    
    private void loadBooks() {
        SwingWorker<List<Book>, Void> worker = new SwingWorker<List<Book>, Void>() {
            @Override
            protected List<Book> doInBackground() throws Exception {
                return bookService.getAllBooks();
            }
            
            @Override
            protected void done() {
                try {
                    List<Book> books = get();
                    updateTable(books);
                    updateStats(books);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(BookManagementPanel.this,
                            "Lỗi khi tải dữ liệu: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
    
    private void updateTable(List<Book> books) {
        tableModel.setRowCount(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                String.format("%,.0f", book.getPrice().doubleValue()),
                book.getQuantity(),
                book.getPublisher(),
                book.getPublishDate() != null ? book.getPublishDate().format(formatter) : ""
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateStats(List<Book> books) {
        int totalBooks = books.size();
        int totalQuantity = books.stream().mapToInt(Book::getQuantity).sum();
        
        Component[] components = ((JPanel) getComponent(2)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Tổng số sách:")) {
                    label.setText("Tổng số sách: " + totalBooks);
                } else if (label.getText().startsWith("Tổng số lượng:")) {
                    label.setText("Tổng số lượng: " + totalQuantity);
                }
            }
        }
    }
    
    private void filterBooks() {
        String searchText = txtSearch.getText().trim();
        String selectedCategory = (String) cmbCategory.getSelectedItem();
        
        if (searchText.isEmpty() && "Tất cả".equals(selectedCategory)) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    // Kiểm tra từ khóa tìm kiếm
                    boolean matchSearch = searchText.isEmpty();
                    if (!matchSearch) {
                        for (int i = 1; i < entry.getValueCount(); i++) { // Bỏ qua cột ID
                            String value = entry.getStringValue(i).toLowerCase();
                            if (value.contains(searchText.toLowerCase())) {
                                matchSearch = true;
                                break;
                            }
                        }
                    }
                    
                    // Kiểm tra danh mục
                    boolean matchCategory = "Tất cả".equals(selectedCategory) ||
                            entry.getStringValue(4).equals(selectedCategory);
                    
                    return matchSearch && matchCategory;
                }
            };
            
            rowSorter.setRowFilter(filter);
        }
    }
    
    private void addBook() {
        BookDialog dialog = new BookDialog(SwingUtilities.getWindowAncestor(this), "Thêm sách mới", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Book book = dialog.getBook();
            if (bookService.addBook(book)) {
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần sửa!");
            return;
        }
        
        int modelRow = bookTable.convertRowIndexToModel(selectedRow);
        int bookId = (Integer) tableModel.getValueAt(modelRow, 0);
        
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BookDialog dialog = new BookDialog(SwingUtilities.getWindowAncestor(this), "Sửa thông tin sách", book);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Book updatedBook = dialog.getBook();
            updatedBook.setBookId(bookId);
            
            if (bookService.updateBook(updatedBook)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách cần xóa!");
            return;
        }
        
        int modelRow = bookTable.convertRowIndexToModel(selectedRow);
        int bookId = (Integer) tableModel.getValueAt(modelRow, 0);
        String bookTitle = (String) tableModel.getValueAt(modelRow, 1);
        
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sách \"" + bookTitle + "\"?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            if (bookService.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Xóa sách thành công!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateButtonStates() {
        boolean hasSelection = bookTable.getSelectedRow() != -1;
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;
        
        btnEdit.setEnabled(hasSelection && isAdmin);
        btnDelete.setEnabled(hasSelection && isAdmin);
    }
}
