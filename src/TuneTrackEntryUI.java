import Cmn_Utilities.LoginUI;
import Cmn_Utilities.RegisterUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TuneTrackEntryUI extends JFrame {

    public TuneTrackEntryUI() {
        setTitle("TuneTrack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(30, 30, 30));
        JLabel titleLabel = new JLabel("Welcome to TuneTrack");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        loginButton.setBackground(new Color(30, 215, 96));
        registerButton.setBackground(new Color(255, 0, 0));
        loginButton.setForeground(Color.WHITE);
        registerButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(30, 30, 30));
        JLabel footerLabel = new JLabel("© 2024 TuneTrack. All Rights Reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the login window
                new LoginUI().setVisible(true);
                dispose(); // Close the entry UI
            }
        });

        // Register Button Action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the registration window
                new RegisterUI().setVisible(true);
                dispose(); // Close the entry UI
            }
        });

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TuneTrackEntryUI entryUI = new TuneTrackEntryUI();
            entryUI.setVisible(true);
        });
    }
}
