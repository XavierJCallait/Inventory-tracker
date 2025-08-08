package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {
  private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

  /**
   * Initializes the database by loading validated properties and creating a connection. If the
   * desired database does not exist, it will be created.
   *
   * @param databaseProperties Properties containing validated database connection details.
   * @throws RuntimeException if the database initialization fails.
   */
  public static void initializeDatabase(Properties databaseProperties) {
    logger.debug("Initializing database...");
    String databaseName = databaseProperties.getProperty("DATABASE_NAME");
    String createDatabaseSQLQuery = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "`";
    executeUpdateStatement(databaseProperties, createDatabaseSQLQuery, "SERVER_URL");
  }

  /**
   * Executes an update statement on the database using the provided properties and query.
   *
   * @param databaseProperties Properties containing validated database connection details.
   * @param updateQuery The SQL query to execute.
   * @param URLType The type of URL to use (either "DB_URL" or "SERVER_URL").
   * @throws RuntimeException if the update execution fails.
   */
  private static void executeUpdateStatement(
      Properties databaseProperties, String updateQuery, String URLType) {
    String url = databaseProperties.getProperty(URLType);
    String user = databaseProperties.getProperty("SPRING_DATASOURCE_USERNAME");
    String password = databaseProperties.getProperty("SPRING_DATASOURCE_PASSWORD");
    try (Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(updateQuery);
    } catch (Exception e) {
      System.err.println("Failed to execute update statement: " + updateQuery);
      throw new RuntimeException("Database operation failed: " + e.getMessage());
    }
  }
}
