package Admin;

import Jdbc_Connector.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteMusic extends JFrame {
    private JTextField searchField;
    private JList<String> musicList;

    public DeleteMusic() {
        setTitle("Delete Music");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel titleLabel = new JLabel("Delete Music");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel searchLabel = new JLabel("Search by Music ID:");
        searchLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(searchLabel, gbc);

        searchField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        searchButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(searchButton, gbc);

        // Music List
        DefaultListModel<String> musicListModel = new DefaultListModel<>();
        musicList = new JList<>(musicListModel);
        JScrollPane musicScrollPane = new JScrollPane(musicList);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(musicScrollPane, gbc);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(193, 26, 26)); // Red color
        deleteButton.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);

        add(panel);

        // Load music list on startup
        loadMusicList();

        // Add action listeners
        searchButton.addActionListener(e -> searchMusic());
        deleteButton.addActionListener(e -> deleteSelectedMusic());
    }

    private void loadMusicList() {
        DefaultListModel<String> musicListModel = (DefaultListModel<String>) musicList.getModel();
        musicListModel.clear();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Songs";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                musicListModel.addElement(id + ": " + title);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load music list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchMusic() {
        String searchID = searchField.getText();
        DefaultListModel<String> musicListModel = (DefaultListModel<String>) musicList.getModel();
        musicListModel.clear();

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Songs WHERE song_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, searchID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String title = rs.getString("title");
                musicListModel.addElement(id + ": " + title);
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for music.", "Error", JOptionPane.ERROR_MESSAGE);
            //developed by http://surl.li/tarlg
        }
    }

    private void deleteSelectedMusic() {
        String selectedMusic = musicList.getSelectedValue();
        if (selectedMusic != null) {
            int id = Integer.parseInt(selectedMusic.split(":")[0].trim());
            try {
                Connection conn = DatabaseConnection.getConnection();
                String query = "DELETE FROM Songs WHERE song_id=?";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, id);
                int deletedRows = statement.executeUpdate();
                if (deletedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Music deleted successfully!");
                    loadMusicList(); // Reload the music list after deletion
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete music.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                statement.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete music.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a music to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteMusic deleteMusicUI = new DeleteMusic();
            deleteMusicUI.setVisible(true);
        });
    }
}
