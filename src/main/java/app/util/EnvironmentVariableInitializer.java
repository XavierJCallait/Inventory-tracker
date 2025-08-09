package app.util;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentVariableInitializer {
  private static final Logger logger =
      LoggerFactory.getLogger(EnvironmentVariableInitializer.class);
  private static final Properties environmentProperties;

  /**
   * Initializes the environment variables by loading them into a Properties object.
   *
   * @throws RuntimeException if environment variables are not found or invalid.
   */
  static {
    environmentProperties = getPropertiesFromEnvironment();
    logger.debug("Fetching environment variables...");
    if (!validProperties(environmentProperties)) {
      logger.error("Validation of environment variables failed");
      throw new RuntimeException("Missing or invalid environment variables");
    }
    logger.debug("Fetched and validated environment variables!");
  }

  /**
   * Loads environment variables into a Properties object.
   *
   * @return Properties object containing environment variables.
   */
  private static Properties getPropertiesFromEnvironment() {
    Properties properties = new Properties();
    properties.setProperty("SERVER_URL", System.getenv("SERVER_URL"));
    properties.setProperty("DATABASE_NAME", System.getenv("DATABASE_NAME"));
    properties.setProperty(
        "SPRING_DATASOURCE_USERNAME", System.getenv("SPRING_DATASOURCE_USERNAME"));
    properties.setProperty(
        "SPRING_DATASOURCE_PASSWORD", System.getenv("SPRING_DATASOURCE_PASSWORD"));
    properties.setProperty("SPRING_DATASOURCE_URL", System.getenv("SPRING_DATASOURCE_URL"));
    return properties;
  }

  /**
   * Validates the properties loaded by checking if null or contains expected characters.
   *
   * @param properties Properties to validate.
   * @return true if properties are valid, false otherwise.
   */
  private static boolean validProperties(Properties properties) {
    if (properties.getProperty("SERVER_URL") == null) {
      logger.error("Missing SERVER_URL environment variable");
    } else if (!properties.getProperty("SERVER_URL").startsWith("jdbc:mysql://")) {
      logger.error("Invalid SERVER_URL format");
    }
    if (properties.getProperty("DATABASE_NAME") == null) {
      logger.error("Missing DATABASE_NAME environment variable");
    } else if (!properties.getProperty("DATABASE_NAME").matches("^[a-zA-Z0-9_]+$")) {
      logger.error("Invalid DATABASE_NAME format");
    }
    if (properties.getProperty("SPRING_DATASOURCE_URL") == null) {
      logger.error("Missing SPRING_DATASOURCE_URL environment variable");
    } else if (!properties.getProperty("SPRING_DATASOURCE_URL").startsWith("jdbc:mysql://")) {
      logger.error("Invalid SPRING_DATASOURCE_URL format");
    }
    if (properties.getProperty("SPRING_DATASOURCE_USERNAME") == null) {
      logger.error("Missing SPRING_DATASOURCE_USERNAME environment variable");
    }
    if (properties.getProperty("SPRING_DATASOURCE_PASSWORD") == null) {
      logger.error("Missing SPRING_DATASOURCE_PASSWORD environment variable");
    } else if (properties.getProperty("SPRING_DATASOURCE_PASSWORD").length() < 8) {
      logger.error("Invalid SPRING_DATASOURCE_PASSWORD format");
    }

    return properties.getProperty("SERVER_URL") != null
        && properties.getProperty("DATABASE_NAME") != null
        && properties.getProperty("SPRING_DATASOURCE_URL") != null
        && properties.getProperty("SPRING_DATASOURCE_PASSWORD") != null
        && properties.getProperty("SPRING_DATASOURCE_USERNAME") != null
        && properties.getProperty("SERVER_URL").startsWith("jdbc:mysql://")
        && properties.getProperty("SPRING_DATASOURCE_URL").startsWith("jdbc:mysql://")
        && properties.getProperty("SPRING_DATASOURCE_PASSWORD").length() >= 8
        && properties.getProperty("DATABASE_NAME").matches("^[a-zA-Z0-9_]+$");
  }

  /**
   * Returns the validated static environment variable as properties.
   *
   * @return Properties object containing validated environment variables.
   */
  public static Properties getEnvironmentProperties() {
    Properties returnableProperties = new Properties();
    returnableProperties.putAll(environmentProperties);
    return returnableProperties;
  }
}
