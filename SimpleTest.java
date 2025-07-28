import javax.swing.*;
import java.awt.*;

public class SimpleTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SIMPLE TEST - 4 CHUC NANG");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            
            // Header
            JLabel header = new JLabel("TEST 4 CHUC NANG", JLabel.CENTER);
            header.setFont(new Font("Arial", Font.BOLD, 20));
            header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainPanel.add(header, BorderLayout.NORTH);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Button 1 - THEM
            JButton btn1 = new JButton("<html><center>THEM<br/>Chuc nang them moi</center></html>");
            btn1.setFont(new Font("Arial", Font.BOLD, 16));
            btn1.setPreferredSize(new Dimension(200, 80));
            btn1.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, "Nhap ten item can them:");
                if (input != null && !input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, 
                        "✅ DA THEM THANH CONG!\n\nItem: " + input,
                        "THEM THANH CONG", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
            
            // Button 2 - SUA
            JButton btn2 = new JButton("<html><center>SUA<br/>Chuc nang sua doi</center></html>");
            btn2.setFont(new Font("Arial", Font.BOLD, 16));
            btn2.setPreferredSize(new Dimension(200, 80));
            btn2.addActionListener(e -> {
                String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
                String selected = (String) JOptionPane.showInputDialog(
                    frame, "Chon item can sua:", "SUA ITEM",
                    JOptionPane.QUESTION_MESSAGE, null, items, items[0]);
                if (selected != null) {
                    String newValue = JOptionPane.showInputDialog(frame, "Nhap gia tri moi:");
                    if (newValue != null && !newValue.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame,
                            "✅ DA SUA THANH CONG!\n\nItem: " + selected + "\nGia tri moi: " + newValue,
                            "SUA THANH CONG",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            
            // Button 3 - XOA
            JButton btn3 = new JButton("<html><center>XOA<br/>Chuc nang xoa bo</center></html>");
            btn3.setFont(new Font("Arial", Font.BOLD, 16));
            btn3.setPreferredSize(new Dimension(200, 80));
            btn3.addActionListener(e -> {
                String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
                String selected = (String) JOptionPane.showInputDialog(
                    frame, "Chon item can xoa:", "XOA ITEM",
                    JOptionPane.WARNING_MESSAGE, null, items, items[0]);
                if (selected != null) {
                    int confirm = JOptionPane.showConfirmDialog(frame,
                        "Ban co chac chan muon xoa?\n\nItem: " + selected,
                        "XAC NHAN XOA",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(frame,
                            "✅ DA XOA THANH CONG!\n\nItem da xoa: " + selected,
                            "XOA THANH CONG",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            
            // Button 4 - LAM MOI
            JButton btn4 = new JButton("<html><center>LAM MOI<br/>Chuc nang cap nhat</center></html>");
            btn4.setFont(new Font("Arial", Font.BOLD, 16));
            btn4.setPreferredSize(new Dimension(200, 80));
            btn4.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame,
                    "✅ DA LAM MOI THANH CONG!\n\nTat ca du lieu da duoc cap nhat!",
                    "LAM MOI THANH CONG",
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            buttonPanel.add(btn1);
            buttonPanel.add(btn2);
            buttonPanel.add(btn3);
            buttonPanel.add(btn4);
            
            mainPanel.add(buttonPanel, BorderLayout.CENTER);
            
            // Footer
            JLabel footer = new JLabel("<html><center>Nhan bat ky nut nao de test chuc nang!<br/>Tat ca 4 nut deu phai hoat dong!</center></html>", JLabel.CENTER);
            footer.setFont(new Font("Arial", Font.ITALIC, 12));
            footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            mainPanel.add(footer, BorderLayout.SOUTH);
            
            frame.add(mainPanel);
            frame.setVisible(true);
            
            System.out.println("=== SIMPLE TEST STARTED ===");
            System.out.println("Co 4 nut: THEM, SUA, XOA, LAM MOI");
            System.out.println("Tat ca deu phai hoat dong!");
        });
    }
}
