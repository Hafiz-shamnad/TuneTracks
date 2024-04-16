package Cmn_Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE); // Text color
        nameLabel.setBounds(20, 50, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 50, 260, 25);
        panel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.WHITE); // Text color
        ageLabel.setBounds(20, 80, 80, 25);
        panel.add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(100, 80, 260, 25);
        panel.add(ageField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Text color
        passwordLabel.setBounds(20, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 110, 260, 25);
        panel.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); // Text color
        emailLabel.setBounds(20, 140, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 140, 260, 25);
        panel.add(emailField);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 180, 100, 25);
        registerButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        registerButton.setForeground(Color.WHITE); // Text color
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get values from fields
                String username = usernameField.getText();
                String name = nameField.getText();
                String age = ageField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                // Implement registration logic here
                // For demo, just display a message
                JOptionPane.showMessageDialog(Registration.this, "Registered successfully!");
                // For demonstration, closing the registration window
                dispose();
                // You can redirect to login page or any other page after registration
            }
        });
        panel.add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 180, 100, 25);
        cancelButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        cancelButton.setForeground(Color.WHITE); // Text color
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the registration window
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    public static void main(String[] args) {
        Registration registrationUI = new Registration();
        registrationUI.setVisible(true);
    }
}
