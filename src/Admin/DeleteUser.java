package Admin;

import Jdbc_Connector.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteUser extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton cancelButton;
    private JList<String> userList;
    private JButton deleteUserButton;
    private DefaultListModel<String> userListModel;
    private Connection connection;

    public DeleteUser() {
        setTitle("Delete User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30)); // Dark background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel titleLabel = new JLabel("Delete User");
        titleLabel.setForeground(Color.WHITE); // Text color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel searchLabel = new JLabel("Search by Username:");
        searchLabel.setForeground(Color.WHITE); // Text color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(searchLabel, gbc);

        searchField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(searchField, gbc);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 0, 0)); // Spotify green color
        cancelButton.setForeground(Color.WHITE); // Text color
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminPortal().setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cancelButton, gbc);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(30, 215, 96)); // Spotify green color
        searchButton.setForeground(Color.WHITE); // Text color
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUser();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(searchButton, gbc);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(userListScrollPane, gbc);

        deleteUserButton = new JButton("Delete Selected User");
        deleteUserButton.setBackground(new Color(255, 0, 0)); // Red color
        deleteUserButton.setForeground(Color.WHITE); // Text color
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteUserButton, gbc);

        add(panel);
    }

    private void searchUser() {
        String username = searchField.getText();
        userListModel.clear(); // Clear previous search results
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT username FROM Users WHERE username LIKE ?");
            statement.setString(1, "%" + username + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                userListModel.addElement(foundUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for users.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        String selectedUsername = userList.getSelectedValue();
        if (selectedUsername != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete user '" + selectedUsername + "'?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM Users WHERE username = ?");
                    statement.setString(1, selectedUsername);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "User '" + selectedUsername + "' deleted successfully!");
                        searchUser(); // Refresh the user list after deletion
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    statement.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteUser deleteUserUI = new DeleteUser();
            deleteUserUI.setVisible(true);
        });
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
