package Jdbc_Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // JDBC URL, username, and password of MySQL server
    public static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/tunetrack";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "User@2024";

    // Database connection instance
    private static Connection connection;

    // Private constructor to prevent instantiation from outside
    public DatabaseConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            // Print a message when the connection is established
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    // Static method to get the database connection instance
    public static Connection getConnection() {
        // Create a new connection instance if it's not already created
        if (connection == null) {
            new DatabaseConnection();
        }
        return connection;
    }
}