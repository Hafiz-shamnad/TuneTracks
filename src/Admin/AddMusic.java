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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddMusic extends JFrame {
    private JTextField directoryPathField;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField artistField;
    private JTextField imagePathField;
    private JButton browseButton;
    private JButton addButton;
    private JButton cancelButton;

    public AddMusic() {
        setTitle("Add Music");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Directory Path
        JLabel directoryPathLabel = new JLabel("Song Path:");
        directoryPathLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(directoryPathLabel, gbc);

        directoryPathField = new JTextField();
        directoryPathField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(directoryPathField, gbc);

        browseButton = new JButton("Browse");
        browseButton.setBounds(20, 260, 100, 25);
        browseButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        browseButton.setForeground(Color.WHITE); // Text color
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseDirectory();
            }
        });
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(browseButton, gbc);

        // Title
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);

        titleField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(titleField, gbc);

        // Artist
        JLabel artistLabel = new JLabel("Artist:");
        artistLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(artistLabel, gbc);

        artistField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(artistField, gbc);

        // Duration
        JLabel durationLabel = new JLabel("Duration (HH:MM:SS):");
        durationLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(durationLabel, gbc);

        durationField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(durationField, gbc);

        // Genre
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(genreLabel, gbc);

        genreField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(genreField, gbc);

        // Image Path
        JLabel imagePathLabel = new JLabel("Image Path:");
        imagePathLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(imagePathLabel, gbc);

        imagePathField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(imagePathField, gbc);

        addButton = new JButton("Add");
        addButton.setBounds(140, 260, 100, 25);
        addButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        addButton.setForeground(Color.WHITE); // Text color
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMusicToDatabase();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(addButton, gbc);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(260, 260, 100, 25);
        cancelButton.setBackground(new Color(255, 0, 0)); // Red color
        cancelButton.setForeground(Color.WHITE); // Text color
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(cancelButton, gbc);

        add(panel);

        add(panel);
    }

    private void browseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Only allow selection of files
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            directoryPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void addMusicToDatabase() {

        String filePath = directoryPathField.getText();
        String artist = artistField.getText();
        String durationString = durationField.getText();
        String genre = genreField.getText();
        String imagePath = imagePathField.getText();
        String title = titleField.getText();

        // Convert duration string to Time object
        Time duration = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            java.util.Date parsed = format.parse(durationString);
            duration = new Time(parsed.getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        // Insert music into the database
        insertMusicIntoDatabase(filePath, artist, duration, genre, imagePath, title);
        JOptionPane.showMessageDialog(this, "Music added successfully!");
    }

    private void insertMusicIntoDatabase(String filePath, String artist, Time duration, String genre, String imagePath,
            String title) {
        try {
            // Establish database connection
            Connection conn = DatabaseConnection.getConnection();

            // Prepare SQL statement for inserting data
            String query = "INSERT INTO Songs (title, file_path, artist, duration, genre , image_path) VALUES (?, ?, ?, ?, ? , ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title); // Assuming file path is the title for now
            statement.setString(2, filePath); // Use the same file path for music_path
            statement.setString(3, artist);
            statement.setTime(4, duration);
            statement.setString(5, genre);
            statement.setString(6, imagePath);

            // Execute the statement
            statement.executeUpdate();

            // Close resources
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add music to the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        AddMusic addMusicUI = new AddMusic();
        addMusicUI.setVisible(true);
    }
}
