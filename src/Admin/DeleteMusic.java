package Admin;

import javax.swing.*;
import java.awt.*;

public class DeleteMusic extends JFrame {
    private JTextField searchField;

    public DeleteMusic() {
        setTitle("Delete Music");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        JLabel titleLabel = new JLabel("Delete Music");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30, 30, 30)); // Dark background color

        JLabel searchLabel = new JLabel("Search by Music ID:");
        searchLabel.setForeground(Color.WHITE); // Text color
        searchPanel.add(searchLabel);

        searchField = new JTextField(10);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        searchButton.setForeground(Color.WHITE); // Text color
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.CENTER);

        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setText("Select the music you want to delete:");
        infoTextArea.setBackground(new Color(30, 30, 30)); // Dark background color
        infoTextArea.setForeground(Color.WHITE); // Text color
        infoTextArea.setEditable(false);
        panel.add(infoTextArea, BorderLayout.SOUTH);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(193, 26, 26)); // Red color
        deleteButton.setForeground(Color.WHITE); // Text color
        panel.add(deleteButton, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        DeleteMusic deleteMusicUI = new DeleteMusic();
        deleteMusicUI.setVisible(true);
    }
}
