package User;

import Jdbc_Connector.DatabaseConnection;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class UserView extends JFrame {
    private Clip clip;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private long clipTimePosition = 0;

    public UserView() {
        setTitle("TuneTrack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30)); // Dark background color

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(20, 20, 20)); // Darker background color
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel titleLabel = new JLabel("TuneTrack");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(30, 30, 30)); // Dark background color

        // Playback Controls Panel
        JPanel playbackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playbackPanel.setBackground(new Color(20, 20, 20)); // Darker background color
        JButton playButton = new JButton("▶");
        JButton pauseButton = new JButton("||");
        JButton nextButton = new JButton("►►");
        JButton previewButton = new JButton("◄◄");
        playButton.setBackground(new Color(30, 215, 96)); // Green play button
        playButton.setForeground(Color.WHITE);
        pauseButton.setBackground(new Color(30, 215, 96)); // Green pause button
        pauseButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(30, 215, 96)); // Green next button
        nextButton.setForeground(Color.WHITE);
        previewButton.setBackground(new Color(30, 215, 96)); // Green preview button
        previewButton.setForeground(Color.WHITE);

        // Add action listeners for playback controls
        playbackPanel.add(previewButton);
        playbackPanel.add(pauseButton);
        playbackPanel.add(playButton);
        playbackPanel.add(nextButton);

        // Favorites Panel
        JPanel favoritesPanel = new JPanel();
        favoritesPanel.setBackground(new Color(30, 30, 30)); // Dark background color
        JLabel favoritesLabel = new JLabel("Add to Favorites:");
        favoritesLabel.setForeground(Color.WHITE);
        // Add favorite buttons here

        // Add components to main content panel
        contentPanel.add(playbackPanel, BorderLayout.NORTH);
        contentPanel.add(favoritesPanel, BorderLayout.SOUTH);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Sample action listener for play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPlaying) {
                    // Retrieve audio data from the database
                    byte[] audioData = retrieveAudioDataFromDatabase("closer.wav");
                    // Play the audio
                    playAudio(audioData);
                    isPlaying = true;
                } else if (isPaused) {
                    // Resume playback
                    resumeAudio();
                    isPaused = false;
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying && !isPaused) {
                    // Pause playback
                    pauseAudio();
                    isPaused = true;
                }
            }
        });
    }

    // Method to retrieve audio data from the database
    private byte[] retrieveAudioDataFromDatabase(String filename) {
        byte[] audioData = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT song_data FROM Songs WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, filename);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                audioData = rs.getBytes("song_data");
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return audioData;
    }

    // Method to play audio from byte array
    private void playAudio(byte[] audioData) {
        try {
            // Convert byte array to InputStream
            ByteArrayInputStream bis = new ByteArrayInputStream(audioData);
            // Create AudioInputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
            // Get Clip
            clip = AudioSystem.getClip();
            // Open AudioInputStream to the clip
            clip.open(ais);
            // Start playing the sound
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to pause audio playback
    private void pauseAudio() {
        // Pause the currently playing clip
        clip.stop();
    }

    // Method to resume audio playback
    private void resumeAudio() {
        // Resume the paused clip
        clip.start();
    }

    // Your existing methods here

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserView().setVisible(true);
            }
        });
    }
}
