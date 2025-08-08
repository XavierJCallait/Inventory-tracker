package app.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;
import org.junit.jupiter.api.Test;

/** Unit tests for EnvironmentVariableInitializer. */
public class EnvironmentVariableInitializerTest {
  @Test
  public void shouldLoadEnvironmentVariables() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(properties.containsKey("SERVER_URL"), "SERVER_URL must be set");
    assertTrue(properties.containsKey("DB_NAME"), "DB_NAME must be set");
    assertTrue(properties.containsKey("DB_USER"), "DB_USER must be set");
    assertTrue(properties.containsKey("DB_PASSWORD"), "DB_PASSWORD must be set");
    assertTrue(properties.containsKey("DB_URL"), "DB_URL must be set");
  }

  @Test
  public void shouldStartWithURLPrefix() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("SERVER_URL").startsWith("jdbc:mysql://"),
        "SERVER_URL must start with jdbc:mysql://");
    assertTrue(
        properties.getProperty("DB_URL").startsWith("jdbc:mysql://"),
        "DB_URL must start with jdbc:mysql://");
  }

  @Test
  public void shouldHaveValidPasswordLength() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("DB_PASSWORD").length() >= 8,
        "Password must be at least 8 characters long");
  }

  @Test
  public void shouldHaveValidDatabaseName() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("DB_NAME").matches("^[a-zA-Z0-9_]+$"),
        "DB_NAME must contain only alphanumeric characters and underscores");
  }
}
