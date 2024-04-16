package Admin;

import javax.swing.*;
import java.awt.*;

public class UserManagement extends JFrame {
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;

    public UserManagement() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30)); // Dark background color
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 20, 300, 30);
        panel.add(titleLabel);

        addUserButton = new JButton("Add User");
        addUserButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        addUserButton.setForeground(Color.WHITE); // Text color
        addUserButton.setBounds(50, 80, 120, 30);
        panel.add(addUserButton);

        editUserButton = new JButton("Edit User");
        editUserButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        editUserButton.setForeground(Color.WHITE); // Text color
        editUserButton.setBounds(170, 80, 120, 30);
        panel.add(editUserButton);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBackground(new Color(255, 0, 0)); // Red color
        deleteUserButton.setForeground(Color.WHITE); // Text color
        deleteUserButton.setBounds(290, 80, 120, 30);
        panel.add(deleteUserButton);

        add(panel);
    }

    public static void main(String[] args) {
        UserManagement userManagementUI = new UserManagement();
        userManagementUI.setVisible(true);
    }
}
