package Cmn_Utilities;

import Admin.AdminPortal;
import Jdbc_Connector.DatabaseConnection;
import User.UserView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUI extends JFrame {

    static boolean admin = false;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginUI() {
        setTitle("Music Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350); // Increased height for better layout
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(41, 41, 41)); // Dark background color
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        JLabel titleLabel = new JLabel("Welcome to TuneTrack");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED); // Error text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(errorLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        loginButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);

        add(panel);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Perform login authentication logic here
        // For demonstration, check if username is "admin" and password is "admin"

        if (validateUser(username, password)) {
            // Open Admin Entry UI
            dispose(); // Close the login window
            if (admin) {
                new AdminPortal().setVisible(true);
            } else {
                new UserView().setVisible(true);
            }
        } else {
            dispose(); // Close the login window
            JOptionPane.showMessageDialog(null, "Login Failed! Try again."); //http://surl.li/tarlg
            new LoginUI().setVisible(true);
        }
    }

    public static boolean validateUser(String username, String password) {
        // SQL query to retrieve user information based on the provided username
        String sql = "SELECT * FROM Users WHERE username = ?";

        // Create a PreparedStatement with the SQL query
        try (
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            // Execute the query and retrieve the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the password stored in the database
                    String storedPassword = resultSet.getString("password");
                    String usertype = resultSet.getString("user_type");
                    // Compare the stored password with the provided password
                    if (password.equals(storedPassword)) {
                        if (usertype.equals(("admin"))) {
                            admin = true;
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        }

        // If the username doesn't exist or passwords don't match, user is not validated
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginUI entryUI = new LoginUI();
            entryUI.setVisible(true);
        });
    }
}
