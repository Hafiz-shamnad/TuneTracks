package Cmn_Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public Login() {
        setTitle("Music Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30)); // Dark background color
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Text color
        usernameLabel.setBounds(20, 20, 80, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 260, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Text color
        passwordLabel.setBounds(20, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 260, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 90, 80, 25);
        loginButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        loginButton.setForeground(Color.WHITE); // Text color
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement authentication logic here
                // For demo, just display a message
                JOptionPane.showMessageDialog(Login.this, "Logged in successfully!");
                // For demonstration, closing the login window
                dispose();
                // You can open User UI or Admin UI here
            }
        });
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(200, 90, 100, 25);
        registerButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        registerButton.setForeground(Color.WHITE); // Text color
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement registration logic here
                // For demo, just display a message
                JOptionPane.showMessageDialog(Login.this, "Redirecting to registration page...");
                // For demonstration, opening a new registration window
                new Registration().setVisible(true);
            }
        });
        panel.add(registerButton);

        add(panel);
    }

    public static void main(String[] args) {
        Login entryUI = new Login();
        entryUI.setVisible(true);
    }
}
