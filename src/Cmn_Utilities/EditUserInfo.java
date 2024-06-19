package Cmn_Utilities;

import Jdbc_Connector.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditUserInfo extends JFrame {
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField ageField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton searchButton;

    public EditUserInfo() {
        setTitle("Edit User Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        searchButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(searchButton, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(ageLabel, gbc);

        ageField = new JTextField();
        gbc.gridx = 1; //developed by http://surl.li/tarlg
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ageField, gbc);

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        saveButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(saveButton, gbc);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 0, 0)); // Spotify green color
        cancelButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(cancelButton, gbc);

        add(panel);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUser();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserInfo();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the registration window

            }
        });
    }

    private void searchUser() {
        String username = usernameField.getText();
        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare SQL statement for selecting user information
            String query = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            // Execute the statement
            ResultSet resultSet = statement.executeQuery();

            // Display user information if found
            if (resultSet.next()) {
                nameField.setText(resultSet.getString("name"));
                ageField.setText(resultSet.getString("age"));
                // Assuming password and email fields are not editable in this UI
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }

            // Close resources
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveUserInfo() {
        String username = usernameField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        // Assuming password and email fields are not editable in this UI

        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare SQL statement for updating user information
            String query = "UPDATE Users SET name = ?, age = ? WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, age);
            statement.setString(3, username);

            // Execute the statement
            int rowsAffected = statement.executeUpdate();

            // Check if update was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User information updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user information.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update user information.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditUserInfo editUserInfoUI = new EditUserInfo();
            editUserInfoUI.setVisible(true);
        });
    }
}
