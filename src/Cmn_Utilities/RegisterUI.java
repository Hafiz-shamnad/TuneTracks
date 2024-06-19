package Cmn_Utilities;

import Jdbc_Connector.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registration extends JFrame {
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField ageField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;

    public Registration() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
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

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(ageLabel, gbc);

        ageField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ageField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(emailLabel, gbc);

        emailField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        registerButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(registerButton, gbc);

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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
                dispose();
                new Login().setVisible(true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the registration window
            }
        });
    }

    private void registerUser() {
        // Get values from fields
        String username = usernameField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();

        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare SQL statement for inserting data
            String query = "INSERT INTO Users (username, name, age, password, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, age);
            statement.setString(4, password);
            statement.setString(5, email);

            // Execute the statement
            statement.executeUpdate();

            // Close resources
            statement.close();
            conn.close();

            // Display success message
            JOptionPane.showMessageDialog(this, "Registered successfully!");

            // Close the registration window
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to register user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Registration registrationUI = new Registration();
            registrationUI.setVisible(true);
        });
    }
}
