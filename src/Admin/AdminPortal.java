package Admin;

import Cmn_Utilities.EditUserInfo;
import Cmn_Utilities.Registration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagement extends JFrame {
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;

    public UserManagement() {
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);

        addUserButton = new JButton("Add User");
        addUserButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        addUserButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(addUserButton, gbc);

        editUserButton = new JButton("Edit User");
        editUserButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        editUserButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(editUserButton, gbc);

        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBackground(new Color(255, 0, 0)); // Red color
        deleteUserButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(deleteUserButton, gbc);

        add(panel);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Registration().setVisible(true);
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });

        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EditUserInfo().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManagement userManagementUI = new UserManagement();
            userManagementUI.setVisible(true);
        });
    }
}
