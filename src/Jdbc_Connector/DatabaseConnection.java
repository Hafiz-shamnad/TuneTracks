package Jdbc_Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tunetrack";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "User@2024";

    // Database connection instance
    private static Connection connection = null;

    // Private constructor to prevent instantiation from outside
    private DatabaseConnection() {
    }

    // Static method to get the database connection instance
    public static Connection getConnection() {
        try {
            // Create a new connection instance if it's not already created or closed
            if (connection == null || connection.isClosed()) {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish the connection
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                // Print a message when the connection is established
                System.out.println("Database connection established successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Method to close the database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
