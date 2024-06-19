package Cmn_Utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel errorLabel;

    public Login() {
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

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        registerButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationPage();
            }
        });
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Dummy authentication (replace with actual logic)
        if (username.equals("admin") && password.equals("admin")) {
            // Successful login
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Open main application window
            openMainApp();
        } else {
            // Invalid credentials
            errorLabel.setText("Invalid username or password.");
        }
    }

    private void openRegistrationPage() {
        // Open registration page
        JOptionPane.showMessageDialog(this, "Registration page will open here.", "Registration", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openMainApp() {
        // Open main application window
        JOptionPane.showMessageDialog(this, "Main application will open here.", "Main App", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login entryUI = new Login();
            entryUI.setVisible(true);
        });
    }
}

