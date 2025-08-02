package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private static void bindParametersToStatement(PreparedStatement statement, List<Object> parameters) {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
        } catch (SQLException e) {
            System.err.println("Failed to bind parameters to statement");
            throw new RuntimeException("Error binding parameters: " + e.getMessage());
        }
    }

    /**
     * Initializes the database by loading validated properties and creating a connection.
     * If the desired database does not exist, it will be created.
     * @param databaseProperties Properties containing validated database connection details.
     * @throws RuntimeException if the database initialization fails.
     */
    public static void initializeDatabase(Properties databaseProperties) {
        String databaseName = databaseProperties.getProperty("DB_NAME");
        String createDatabaseSQLQuery = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "`";
        executeUpdateStatement(databaseProperties, createDatabaseSQLQuery, "SERVER_URL");
    }

    public static void executeUpdateStatement(Properties databaseProperties, String updateQuery, String URLType) {
        String url = databaseProperties.getProperty(URLType);
        String user = databaseProperties.getProperty("DB_USER");
        String password = databaseProperties.getProperty("DB_PASSWORD");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateQuery);
        } catch (Exception e) {
            System.err.println("Failed to execute update statement: " + updateQuery);
            throw new RuntimeException("Database operation failed: " + e.getMessage());
        }
    }

    public static ResultSet executeQueryPreparedStatement(Properties databaseProperties, String updateQuery, List<Object> parameters) {
        String url = databaseProperties.getProperty("DB_URL");
        String user = databaseProperties.getProperty("DB_USER");
        String password = databaseProperties.getProperty("DB_PASSWORD");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            bindParametersToStatement(preparedStatement, parameters);
            return preparedStatement.executeQuery();
        } catch (Exception e
        ) {
            System.err.println("Failed to execute query prepared statement");
            throw new RuntimeException("Database operation failed: " + e.getMessage());
        }
    }
}