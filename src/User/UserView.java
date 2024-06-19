package User;

import Cmn_Utilities.EditUserInfo;
import Cmn_Utilities.LoginUI;
import Jdbc_Connector.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Stack;

public class UserView extends JFrame {
    private Clip clip;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private final JLabel genreLabel;
    private final JLabel artistLabel;
    private final JLabel titleLabel;
    private final JProgressBar progressBar;
    private JLabel profileImageLabel;
    private int currentSongID;

    public UserView() {
        setTitle("TuneTrack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Create the content panel with BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(30, 30, 30));

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(20, 20, 20));
        JLabel headerLabel = new JLabel("TuneTrack");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segue UI", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(new Color(40, 40, 40));
        contentPanel.add(displayPanel, BorderLayout.CENTER);

        // Left Panel for Controls
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.setBackground(new Color(30, 30, 30));
        controlsPanel.setPreferredSize(new Dimension(300, getHeight())); // Fixed width
        contentPanel.add(controlsPanel, BorderLayout.WEST);

        // User Profile Panel
        JPanel userProfilePanel = new JPanel(new GridBagLayout());
        userProfilePanel.setBackground(new Color(30, 30, 30));

        // User Profile label
        JLabel userProfileLabel = new JLabel("User Profile");
        userProfileLabel.setForeground(Color.WHITE);
        GridBagConstraints userProfileLabelConstraints = new GridBagConstraints();
        userProfileLabelConstraints.gridx = 0;
        userProfileLabelConstraints.gridy = 0;
        userProfileLabelConstraints.insets = new Insets(10, 10, 10, 10);
        userProfilePanel.add(userProfileLabel, userProfileLabelConstraints);

        // Welcome message label
        JLabel welcomeLabel = new JLabel("Welcome, ");
        welcomeLabel.setForeground(Color.WHITE);
        GridBagConstraints welcomeLabelConstraints = new GridBagConstraints();
        welcomeLabelConstraints.gridx = 0;
        welcomeLabelConstraints.gridy = 1;
        welcomeLabelConstraints.insets = new Insets(10, 10, 10, 10);
        userProfilePanel.add(welcomeLabel, welcomeLabelConstraints);

        // Edit Profile button

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBackground(new Color(30, 215, 96));
        editProfileButton.setForeground(Color.WHITE);

        GridBagConstraints editProfileButtonConstraints = new GridBagConstraints();
        editProfileButtonConstraints.gridx = 0;
        editProfileButtonConstraints.gridy = 3;
        editProfileButtonConstraints.insets = new Insets(10, 10, 10, 10);

        userProfilePanel.add(editProfileButton, editProfileButtonConstraints);
        controlsPanel.add(userProfilePanel);

        profileImageLabel = new JLabel(); // Initialize profileImageLabel
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setBorder(new EmptyBorder(5, 0, 10, 0)); // Add padding
        controlsPanel.add(profileImageLabel, BorderLayout.CENTER);

        // Image Label
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        displayPanel.add(imageLabel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
        int userId = 2; // Replace with the actual user ID of the logged-in admin
        String imagePath = getImagePathFromDatabase(userId);
        loadImageFromPath(imagePath); // Load the image using the retrieved path http://surl.li/tarlg

        JPanel logoutButtonPanel = new JPanel(new GridBagLayout());
        logoutButtonPanel.setBackground(new Color(30, 30, 30));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 0, 0));
        logoutButton.setForeground(Color.WHITE);

        GridBagConstraints logoutButtonConstraints = new GridBagConstraints();
        logoutButtonConstraints.gridx = 2; // Column index
        logoutButtonConstraints.gridy = 0; // Row index
        logoutButtonConstraints.insets = new Insets(10, 10, 10, 10); // Padding

        logoutButtonPanel.add(logoutButton, logoutButtonConstraints);
        controlsPanel.add(logoutButtonPanel, BorderLayout.SOUTH);

        controlsPanel.add(userProfilePanel, BorderLayout.NORTH);

        // Playback Controls Panel
        JPanel playbackPanel = new JPanel(new BorderLayout()); // Change to BorderLayout
        playbackPanel.setBackground(new Color(30, 30, 30));

        // Music Progress Panel
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBackground(new Color(30, 30, 30));
        progressBar = new JProgressBar();
        progressBar.setBackground(new Color(30, 30, 30));
        progressBar.setForeground(new Color(30, 215, 96));
        progressBar.setBorderPainted(false);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        playbackPanel.add(progressPanel, BorderLayout.NORTH); // Add progress panel to NORTH

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        JButton previewButton = new JButton("◄◄");
        JButton playButton = new JButton("▶");
        JButton pauseButton = new JButton("||");
        JButton nextButton = new JButton("►►");
        // Set button colors and font
        previewButton.setBackground(new Color(30, 215, 96));
        previewButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(30, 215, 96));
        playButton.setForeground(Color.WHITE);
        pauseButton.setBackground(new Color(30, 215, 96));
        pauseButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(30, 215, 96));
        nextButton.setForeground(Color.WHITE);
        // (Button setup code omitted for brevity)
        buttonPanel.add(previewButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(nextButton);
        playbackPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to SOUTH http://surl.li/tarlg
        displayPanel.add(playbackPanel, BorderLayout.SOUTH); // Add the playback panel to the display panel

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.setBackground(new Color(30, 30, 30));
        JLabel emptyLabel = new JLabel(" ");
        titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.WHITE);
        genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(Color.WHITE);
        artistLabel = new JLabel("Artist:");
        artistLabel.setForeground(Color.WHITE);
        infoPanel.add(emptyLabel);
        infoPanel.add(titleLabel);
        infoPanel.add(genreLabel);
        infoPanel.add(artistLabel);
        displayPanel.add(infoPanel, BorderLayout.NORTH);

        // Right Panel to display list of songs
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(30, 30, 30));
        rightPanel.setPreferredSize(new Dimension(250, 200)); // Fixed width

        // Create the list model and list
        DefaultListModel<String> songListModel = new DefaultListModel<>();
        JList<String> songList = new JList<>(songListModel);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set the background color and text color of the list
        songList.setBackground(new Color(30, 30, 30));
        songList.setForeground(Color.WHITE);

        // Create a JPanel to hold the JList
        JPanel listPanel = new JPanel();
        JLabel listLabel = new JLabel("Available Music");
        listLabel.setForeground(Color.WHITE);
        listLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some space with an EmptyBorder
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); //http://surl.li/tarlg
        listPanel.setBackground(new Color(30, 30, 30));

        // Add the JList to the JPanel
        listPanel.add(listLabel);
        listPanel.add(songList);

        // Create a scroll pane for the JPanel
        JScrollPane songScrollPane = getjScrollPane(listPanel, songList);

        // Add the scroll pane to the right panel
        rightPanel.add(songScrollPane, BorderLayout.CENTER);

        // Add the right panel to the frame
        contentPanel.add(rightPanel, BorderLayout.EAST);

        // Sample action listener for song selection
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                stopAudio();
                int selectedIndex = songList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String[] paths = retrievePathsFromDatabase(selectedIndex + 1);
                    String audioFilePath = paths[0];//http://surl.li/tarlg
                    String imageFilePath = paths[1];
                    playAudio(audioFilePath);
                    isPlaying = true;
                    if (imageFilePath != null) {
                        displayImage(imageLabel, imageFilePath);
                    }
                }
            }
        });

        // Maintain a stack to store recently played song IDs
        Stack<Integer> recentlyPlayedSongs = new Stack<>();

        // Sample action listener for next button
        nextButton.addActionListener(e -> {
            stopAudio();
            int randomSongId = (int) (Math.random() * 7) + 1;
            String[] paths = retrievePathsFromDatabase(randomSongId);
            String audioFilePath = paths[0];
            String imageFilePath = paths[1];
            playAudio(audioFilePath);//http://surl.li/tarlg
            isPlaying = true;
            currentSongID = randomSongId;
            if (imageFilePath != null) {
                displayImage(imageLabel, imageFilePath);
            }
            // Add the current song ID to the recently played stack
            recentlyPlayedSongs.push(currentSongID);
        });

        // Preview button action listener
        previewButton.addActionListener(e -> {
            stopAudio();
            // Check if there are recently played songs
            if (!recentlyPlayedSongs.isEmpty()) {
                recentlyPlayedSongs.pop(); // Remove the most recently played song
                // Check if there's still a song available to play
                if (!recentlyPlayedSongs.isEmpty()) {
                    int prevSongId = recentlyPlayedSongs.peek(); // Peek the ID of the second last played song
                    String[] paths = retrievePathsFromDatabase(prevSongId);
                    String audioFilePath = paths[0];
                    String imageFilePath = paths[1];
                    System.out.println(audioFilePath);
                    playAudio(audioFilePath);
                    isPlaying = true;
                    if (imageFilePath != null) {
                        displayImage(imageLabel, imageFilePath);
                    }
                } else {
                    // Handle case when there are no other recently played songs //http://surl.li/tarlg
                    // For example, display a message to the user or play a default song
                }
            } else {
                // Handle case when there are no recently played songs
                // For example, display a message to the user or play a default song
            }
        });

        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditUserInfo().setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginUI().setVisible(true);
            }
        });

        // Sample action listener for play button
        playButton.addActionListener(e -> {
            if (!isPlaying) {
                int randomSongId = (int) (Math.random() * 7) + 1;
                String[] paths = retrievePathsFromDatabase(randomSongId);
                String audioFilePath = paths[0];
                String imageFilePath = paths[1];
                playAudio(audioFilePath);
                isPlaying = true;
                if (imageFilePath != null) {
                    displayImage(imageLabel, imageFilePath);
                }
            } else if (isPaused) {
                resumeAudio();
                isPaused = false;
            }
        });

        // Sample action listener for pause button
        pauseButton.addActionListener(e -> {
            if (isPlaying && !isPaused) {
                pauseAudio();
                isPaused = true;
            }
        });

        // Load songs into the list
        loadSongs(songListModel);
        initializeProgressBar();
        startProgressBarUpdate();
        updateProgressBar();
    }

    private JScrollPane getjScrollPane(JPanel listPanel, JList<String> songList) {
        JScrollPane songScrollPane = new JScrollPane(listPanel);
        songScrollPane.setBorder(null); // Remove the border around the scroll pane
        songScrollPane.setBackground(new Color(30, 30, 30)); // Set the background color of the scroll pane

        // Set the size of the scroll pane
        int scrollPaneHeight = Math.min(songList.getPreferredScrollableViewportSize().height, getHeight() - 100);
        songScrollPane.setPreferredSize(new Dimension(400, scrollPaneHeight)); // Adjust width as needed
        return songScrollPane;
    }

    // Method to load songs from the database into the list
    private void loadSongs(DefaultListModel<String> songListModel) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT title FROM Songs";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                songListModel.addElement(title);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to retrieve audio data from the database using the song ID
    private String[] retrievePathsFromDatabase(int songId) {
        String[] paths = new String[2];
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT file_path, image_path, genre, artist, title FROM Songs WHERE song_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, songId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                paths[0] = rs.getString("file_path");
                paths[1] = rs.getString("image_path");
                String genre = rs.getString("genre");
                String artist = rs.getString("artist");
                String title = rs.getString("title");
                // Update labels with retrieved genre and artist
                updateInfoLabels(genre, artist, title);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return paths;
    }

    // Method to update genre and artist labels
    private void updateInfoLabels(String genre, String artist, String title) {
        titleLabel.setText("Title: " + title);
        genreLabel.setText("Genre: " + genre);
        artistLabel.setText("Artist: " + artist);
    }

    // Method to display image on the image label
    private void displayImage(JLabel imageLabel, String imagePath) {
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
                    Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error loading image: " + ex.getMessage());
        }
    }

    // Method to play audio from file
    private void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (audioFile.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                startProgressBarUpdate();
            } else {
                System.err.println("File not found: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop audio playback
    private void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
            isPaused = false;
        }
    }

    // Method to pause audio playback
    private void pauseAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
        }
    }

    // Method to resume audio playback
    private void resumeAudio() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            isPaused = false;
        }
    }

    // Method to update the progress bar based on the current position of the audio
    // clip
    private void updateProgressBar() {
        if (clip != null && clip.isRunning()) {
            long currentMicrosecondPosition = clip.getMicrosecondPosition();
            long totalMicrosecondLength = clip.getMicrosecondLength();
            int progress = (int) ((double) currentMicrosecondPosition / totalMicrosecondLength * 100);
            progressBar.setValue(progress);
        }
    }

    // Method to start updating the progress bar
    private void startProgressBarUpdate() {
        Timer timer = new Timer(100, e -> {
            if (clip != null && clip.isRunning()) {
                long totalMicroSec = clip.getMicrosecondLength();
                long currentMicroSec = clip.getMicrosecondPosition();
                double progress = (double) currentMicroSec / totalMicroSec;
                int progressPercentage = (int) (progress * 100);
                progressBar.setValue(progressPercentage);
            }
        });
        timer.start();
    }

    // Method to initialize the progress bar
    private void initializeProgressBar() {
        // Set initial value and other properties of the progress bar as needed
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserView userView = new UserView();
            userView.setVisible(true);
        });
    }
}
