package util;

import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static SessionFactory sessionFactory;

  private HibernateUtil() {
    // Prevent instantiation
  }

  /**
   * Initializes the Hibernate SessionFactory using provided properties. Can only be called once.
   *
   * @param databaseProperties Properties containing validated database connection details.
   * @throws IllegalStateException if the SessionFactory is already initialized.
   * @throws ExceptionInInitializerError if the SessionFactory initialization fails.
   */
  public static synchronized void initializeSessionFactory(Properties databaseProperties) {
    if (sessionFactory != null) {
      throw new IllegalStateException("SessionFactory is already initialized.");
    }

    try {
      Configuration configuration = new Configuration().configure();
      configuration.setProperty(
          "hibernate.connection.url", databaseProperties.getProperty("DB_URL"));
      configuration.setProperty(
          "hibernate.connection.username", databaseProperties.getProperty("DB_USER"));
      configuration.setProperty(
          "hibernate.connection.password", databaseProperties.getProperty("DB_PASSWORD"));

      sessionFactory =
          configuration.buildSessionFactory(
              new StandardServiceRegistryBuilder()
                  .applySettings(configuration.getProperties())
                  .build());
    } catch (org.hibernate.HibernateException | ExceptionInInitializerError ex) {
      System.err.println("SessionFactory initialization failed");
      throw new ExceptionInInitializerError("Failed to initialize SessionFactory: " + ex);
    }
  }

  /**
   * Returns a session instance created by SessionFactory.
   *
   * @return Session instance.
   * @throws IllegalStateException if the SessionFactory instance is not initialized.
   */
  public static Session getSessionFromFactory() {
    if (sessionFactory == null) {
      throw new IllegalStateException(
          "SessionFactory is not initialized. Call initializeSessionFactory() first.");
    }
    return sessionFactory.openSession();
  }

  /** Closes the SessionFactory if it is initialized. */
  public static void closeSessionFactory() {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }
}
