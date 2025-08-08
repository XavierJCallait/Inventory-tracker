package app.util;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/** Unit tests for EnvironmentVariableInitializer. */
public class EnvironmentVariableInitializerTest {
  @Test
  public void shouldLoadEnvironmentVariables() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(properties.containsKey("SERVER_URL"), "SERVER_URL must be set");
    assertTrue(properties.containsKey("DATABASE_NAME"), "DATABASE_NAME must be set");
    assertTrue(properties.containsKey("SPRING_DATASOURCE_USERNAME"), "SPRING_DATASOURCE_USERNAME must be set");
    assertTrue(properties.containsKey("SPRING_DATASOURCE_PASSWORD"), "SPRING_DATASOURCE_PASSWORD must be set");
    assertTrue(properties.containsKey("SPRING_DATASOURCE_URL"), "SPRING_DATASOURCE_URL must be set");
  }

  @Test
  public void shouldStartWithURLPrefix() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("SERVER_URL").startsWith("jdbc:mysql://"),
        "SERVER_URL must start with jdbc:mysql://");
    assertTrue(
        properties.getProperty("SPRING_DATASOURCE_URL").startsWith("jdbc:mysql://"),
        "SPRING_DATASOURCE_URL must start with jdbc:mysql://");
  }

  @Test
  public void shouldHaveValidPasswordLength() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("SPRING_DATASOURCE_PASSWORD").length() >= 8,
        "SPRING_DATASOURCE_PASSWORD must be at least 8 characters long");
  }

  @Test
  public void shouldHaveValidDatabaseName() {
    Properties properties = EnvironmentVariableInitializer.getEnvironmentProperties();
    assertTrue(
        properties.getProperty("DATABASE_NAME").matches("^[a-zA-Z0-9_]+$"),
        "DATABASE_NAME must contain only alphanumeric characters and underscores");
  }
}
