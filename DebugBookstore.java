import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DebugBookstore {
    private static JFrame frame;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("=== KHOI TAO UNG DUNG ===");
                createFrame();
                System.out.println("=== UNG DUNG DA KHOI TAO ===");
            } catch (Exception e) {
                System.out.println("LOI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    private static void createFrame() {
        System.out.println("Tao frame chinh...");
        
        frame = new JFrame("Debug Bookstore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JLabel headerLabel = new JLabel("QUAN LY CUA HANG SACH", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Button panel in center
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Create 4 large buttons
        JButton addButton = createButton("THEM SACH", Color.GREEN);
        JButton editButton = createButton("SUA SACH", Color.BLUE);
        JButton deleteButton = createButton("XOA SACH", Color.RED);
        JButton refreshButton = createButton("LAM MOI", Color.GRAY);
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("Trang thai: San sang");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.BLACK);
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        
        System.out.println("Hien thi frame...");
        frame.setVisible(true);
        System.out.println("Frame da duoc hien thi!");
    }
    
    private static JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(150, 80));
        button.setFocusPainted(false);
        
        // Add action listener with detailed debugging
        button.addActionListener(e -> {
            System.out.println("=== NUT DUOC NHAN: " + text + " ===");
            
            switch (text) {
                case "THEM SACH":
                    System.out.println("Xu ly them sach...");
                    String tenSach = JOptionPane.showInputDialog(frame, 
                        "Nhap ten sach moi:", 
                        "Them sach", 
                        JOptionPane.QUESTION_MESSAGE);
                    if (tenSach != null && !tenSach.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame,
                            "DA THEM SACH THANH CONG!\n\nTen sach: " + tenSach,
                            "Thanh cong",
                            JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Them sach thanh cong: " + tenSach);
                    } else {
                        System.out.println("Nguoi dung huy hoac khong nhap ten sach");
                    }
                    break;
                    
                case "SUA SACH":
                    System.out.println("Xu ly sua sach...");
                    String[] books = {"Lap trinh Java", "Cau truc du lieu", "Triet hoc doi song"};
                    String selectedBook = (String) JOptionPane.showInputDialog(frame,
                        "Chon sach can sua:",
                        "Sua sach",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        books,
                        books[0]);
                    if (selectedBook != null) {
                        String newName = JOptionPane.showInputDialog(frame,
                            "Nhap ten moi cho sach:",
                            selectedBook);
                        if (newName != null && !newName.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(frame,
                                "DA SUA SACH THANH CONG!\n\n" +
                                "Ten cu: " + selectedBook + "\n" +
                                "Ten moi: " + newName,
                                "Thanh cong",
                                JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Sua sach thanh cong: " + selectedBook + " -> " + newName);
                        }
                    }
                    break;
                    
                case "XOA SACH":
                    System.out.println("Xu ly xoa sach...");
                    String[] booksToDelete = {"Lap trinh Java", "Cau truc du lieu", "Triet hoc doi song"};
                    String bookToDelete = (String) JOptionPane.showInputDialog(frame,
                        "Chon sach can xoa:",
                        "Xoa sach",
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        booksToDelete,
                        booksToDelete[0]);
                    if (bookToDelete != null) {
                        int confirm = JOptionPane.showConfirmDialog(frame,
                            "Ban co chac chan muon xoa sach:\n" + bookToDelete + "?",
                            "Xac nhan xoa",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                        if (confirm == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(frame,
                                "DA XOA SACH THANH CONG!\n\nSach da xoa: " + bookToDelete,
                                "Thanh cong",
                                JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Xoa sach thanh cong: " + bookToDelete);
                        }
                    }
                    break;
                    
                case "LAM MOI":
                    System.out.println("Xu ly lam moi...");
                    JOptionPane.showMessageDialog(frame,
                        "DA LAM MOI DU LIEU!\n\nTat ca thong tin da duoc cap nhat.",
                        "Thanh cong",
                        JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Lam moi thanh cong");
                    break;
            }
            
            System.out.println("=== KET THUC XU LY: " + text + " ===");
        });
        
        return button;
    }
}
