import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestButtons {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Chuc Nang");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Test button 1
            JButton btn1 = new JButton("TEST 1 - Them Sach");
            btn1.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn1.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "CHUC NANG 1 HOAT DONG!\n\nBan vua nhan nut Them Sach",
                    "Test Thanh Cong", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            // Test button 2
            JButton btn2 = new JButton("TEST 2 - Sua Sach");
            btn2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn2.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "CHUC NANG 2 HOAT DONG!\n\nBan vua nhan nut Sua Sach",
                    "Test Thanh Cong", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            // Test button 3
            JButton btn3 = new JButton("TEST 3 - Xoa Sach");
            btn3.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn3.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "CHUC NANG 3 HOAT DONG!\n\nBan vua nhan nut Xoa Sach",
                    "Test Thanh Cong", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            // Test button 4
            JButton btn4 = new JButton("TEST 4 - Quan Ly User");
            btn4.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn4.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "CHUC NANG 4 HOAT DONG!\n\nBan vua nhan nut Quan Ly User",
                    "Test Thanh Cong", 
                    JOptionPane.INFORMATION_MESSAGE);
            });
            
            panel.add(btn1);
            panel.add(btn2);
            panel.add(btn3);
            panel.add(btn4);
            
            frame.add(panel);
            frame.setVisible(true);
            
            System.out.println("Test app started - Tat ca 4 nut deu co the nhan duoc!");
        });
    }
}
