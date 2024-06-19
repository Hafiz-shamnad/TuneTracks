package Admin;

import Cmn_Utilities.LoginUI;
import Cmn_Utilities.RegisterUI;
import Jdbc_Connector.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPortal extends JFrame {
    private JButton addMusicButton;
    private JButton deleteMusicButton;
    private JButton addUserButton;
    private JButton removeUserButton;
    private JButton logoutButton;
    private JLabel profileImageLabel;

    public AdminPortal() {
        setTitle("Admin Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Set window size
        setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(41, 41, 41)); // Dark background color

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(30, 30, 30));
        leftPanel.setPreferredSize(new Dimension(150, 0)); // Set width

        JLabel welcomeLabel = new JLabel("Welcome Admin");
        welcomeLabel.setForeground(Color.WHITE); // Text color
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        welcomeLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Add padding
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(193, 26, 26));
        logoutButton.setForeground(Color.WHITE); // Text color
        logoutButton.setBorderPainted(false); // Remove border
        logoutButton.setFocusPainted(false); // Remove focus border

        logoutButton.setPreferredSize(new Dimension(50, 50)); // Set button size

        leftPanel.add(logoutButton, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        profileImageLabel = new JLabel(); // Initialize profileImageLabel
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setBorder(new EmptyBorder(5, 0, 10, 0)); // Add padding
        leftPanel.add(profileImageLabel, BorderLayout.CENTER);

        int userId = 2; // Replace with the actual user ID of the logged-in admin
        String imagePath = getImagePathFromDatabase(userId);
        loadImageFromPath(imagePath); // Load the image using the retrieved path

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(41, 41, 41)); // Dark background color
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add Music Button
        addMusicButton = createButton("Add Music");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(addMusicButton, gbc);

        // Delete Music Button
        deleteMusicButton = createButton("Delete Music");
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(deleteMusicButton, gbc);

        // Add User Button
        addUserButton = createButton("Add User");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(addUserButton, gbc);

        // Remove User Button
        removeUserButton = createButton("Remove User");
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(removeUserButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Add action listeners
        addMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add music button click
                dispose();
                new AddMusic().setVisible(true);
            }
        });

        deleteMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle delete music button click
                dispose();
                new DeleteMusic().setVisible(true);
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add user button click
                new RegisterUI().setVisible(true);
            }
        });

        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle remove user button click
                dispose();
                new DeleteUser().setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click
                int choice = JOptionPane.showConfirmDialog(AdminPortal.this, "Are you sure you want to logout?",
                        "Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Perform logout actions here, such as closing the admin portal and opening the
                    // login page
                    dispose(); // Close admin portal
                    // Open login page
                    new LoginUI().setVisible(true);
                }
            }
        });
    }

    private String getImagePathFromDatabase(int userId) {
        String imagePath = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            conn = DatabaseConnection.getConnection();

            // Prepare SQL statement
            String query = "SELECT image_path FROM Users WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, userId);

            // Execute query
            resultSet = statement.executeQuery();

            // Process result
            if (resultSet.next()) {
                imagePath = resultSet.getString("image_path");
                System.out.println(imagePath);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return imagePath;
    }

    private void loadImageFromPath(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Read image file
                File file = new File(imagePath);
                BufferedImage image = ImageIO.read(file);

                // Scale image to fit label
                Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // Set image to label
                profileImageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Failed to load image: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                System.err.println("Invalid image file: " + ex.getMessage());
            }
        } else {
            // Handle case where imagePath is null or empty
            System.err.println("Image path is null or empty.");
        }
    }

    private JButton createButton(String text) {
        // Create button with text
        JButton button = new JButton(text);
        button.setBackground(new Color(30, 215, 96)); // Spotify green color
        button.setForeground(Color.WHITE); // Text color
        button.setBorderPainted(false); // Remove border
        button.setFocusPainted(false); // Remove focus border
        button.setContentAreaFilled(true); // Set default background
        button.setPreferredSize(new Dimension(150, 50)); // Set button size
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPortal adminPortal = new AdminPortal();
            adminPortal.setVisible(true);
        });
    }
}
