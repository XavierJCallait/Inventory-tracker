package util;

import java.util.Properties;

public class EnvironmentVariableInitializer {
  private static final Properties environmentProperties;

  /**
   * Initializes the environment variables by loading them into a Properties object.
   *
   * @throws RuntimeException if environment variables are not found or invalid.
   */
  static {
    environmentProperties = getPropertiesFromEnvironment();
    if (!validProperties(environmentProperties)) {
      System.err.println("Validation of environment variables failed");
      throw new RuntimeException("Missing or invalid environment variables");
    }
  }

  /**
   * Loads environment variables into a Properties object.
   *
   * @return Properties object containing environment variables.
   */
  private static Properties getPropertiesFromEnvironment() {
    Properties properties = new Properties();
    properties.setProperty("SERVER_URL", System.getenv("SERVER_URL"));
    properties.setProperty("DB_NAME", System.getenv("DB_NAME"));
    properties.setProperty("DB_USER", System.getenv("DB_USER"));
    properties.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));
    properties.setProperty("DB_URL", System.getenv("DB_URL"));
    return properties;
  }

  /**
   * Validates the properties loaded by checking if null or contains expected characters.
   *
   * @param properties Properties to validate.
   * @return true if properties are valid, false otherwise.
   */
  private static boolean validProperties(Properties properties) {
    return properties.getProperty("SERVER_URL") != null
        && properties.getProperty("DB_URL") != null
        && properties.getProperty("DB_NAME") != null
        && properties.getProperty("DB_USER") != null
        && properties.getProperty("DB_PASSWORD") != null
        && properties.getProperty("SERVER_URL").startsWith("jdbc:mysql://")
        && properties.getProperty("DB_URL").startsWith("jdbc:mysql://")
        && properties.getProperty("DB_PASSWORD").length() >= 8
        && properties.getProperty("DB_NAME").matches("^[a-zA-Z0-9_]+$");
  }

  /**
   * Returns the validated static environment variable as properties.
   *
   * @return Properties object containing validated environment variables.
   */
  public static Properties getEnvironmentProperties() {
    return environmentProperties;
  }
}
