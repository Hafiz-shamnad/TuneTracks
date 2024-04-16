package Admin;

import Jdbc_Connector.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMusic extends JFrame {
    private JTextField directoryPathField;
    private JButton browseButton;
    private JButton addButton;
    private JButton cancelButton;

    public AddMusic() {
        setTitle("Add Music");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30)); // Dark background color
        panel.setLayout(null);

        JLabel directoryPathLabel = new JLabel("Directory Path:");
        directoryPathLabel.setForeground(Color.WHITE); // Text color
        directoryPathLabel.setBounds(20, 20, 120, 25);
        panel.add(directoryPathLabel);

        directoryPathField = new JTextField();
        directoryPathField.setBounds(140, 20, 200, 25);
        panel.add(directoryPathField);

        browseButton = new JButton("Browse");
        browseButton.setBounds(20, 60, 100, 25);
        browseButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        browseButton.setForeground(Color.WHITE); // Text color
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseDirectory();
            }
        });
        panel.add(browseButton);

        addButton = new JButton("Add");
        addButton.setBounds(140, 60, 100, 25);
        addButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        addButton.setForeground(Color.WHITE); // Text color
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMusicToDatabase();
            }
        });
        panel.add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(260, 60, 100, 25);
        cancelButton.setBackground(new Color(255, 0, 0)); // Red color
        cancelButton.setForeground(Color.WHITE); // Text color
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void browseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow selection of files
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            directoryPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void addMusicToDatabase() {
        String directoryPath = directoryPathField.getText();
        System.out.println("Directory Path: " + directoryPath); // Debugging statement
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path or directory does not exist."); // Debugging statement
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] musicBytes = new byte[(int) file.length()];
                        int bytesRead = fis.read(musicBytes);
                        if (bytesRead != -1) {
                            System.out.println("Successfully read " + bytesRead + " bytes from file: " + file.getName()); // Debugging statement
                            // Insert the byte array into the database
                            insertMusicIntoDatabase(file.getName(), musicBytes);
                        } else {
                            System.out.println("No bytes read from file: " + file.getName()); // Debugging statement
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Music added successfully!");
        }
    }

    private void insertMusicIntoDatabase(String fileName, byte[] musicBytes) {
        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare SQL statement for inserting data
            String query = "INSERT INTO Songs (title, song_data) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, fileName);
            statement.setBytes(2, musicBytes);

            // Execute the statement
            statement.executeUpdate();

            // Close resources
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add music to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        AddMusic addMusicUI = new AddMusic();
        addMusicUI.setVisible(true);
    }
}
