package app.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DatabaseManagerTest {
  private final String testDatabaseName = "test_inventory_db";

  @Test
  public void shouldConnectAndCreateDatabase() {
    Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
    databaseProperties.setProperty("DB_NAME", testDatabaseName);
    DatabaseManager.initializeDatabase(databaseProperties);

    String url = databaseProperties.getProperty("SERVER_URL");
    String user = databaseProperties.getProperty("DB_USER");
    String password = databaseProperties.getProperty("DB_PASSWORD");
    String databaseName = databaseProperties.getProperty("DB_NAME");
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      PreparedStatement ps =
          connection.prepareStatement(
              "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?");
      ps.setString(1, databaseName);
      ResultSet rs = ps.executeQuery();
      assertTrue(rs.next());
    } catch (Exception e) {
      System.err.println("Database connection and initialization failed");
      fail("Exception thrown during initialization test: " + e.getMessage());
    }
  }

  @AfterEach
  public void dropTestTable() {
    Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
    databaseProperties.setProperty("DB_NAME", testDatabaseName);

    String url = databaseProperties.getProperty("SERVER_URL");
    String user = databaseProperties.getProperty("DB_USER");
    String password = databaseProperties.getProperty("DB_PASSWORD");
    String databaseName = databaseProperties.getProperty("DB_NAME");
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      String dropDatabaseSQLQuery = "DROP DATABASE IF EXISTS `" + databaseName + "`";
      Statement statement = connection.createStatement();
      statement.executeUpdate(dropDatabaseSQLQuery);
    } catch (Exception e) {
      System.err.println("Failed to drop test database");
    }
  }
}
