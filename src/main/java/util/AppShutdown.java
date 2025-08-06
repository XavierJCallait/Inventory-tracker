package util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppShutdown {
  private static final Logger logger = LoggerFactory.getLogger(AppShutdown.class);

  /**
   * Cleans up resources and shuts down the application gracefully. Should only be called once
   * during application shutdown.
   */
  public static void shutdown() {
    logger.info("Shutting down application...");
    AbandonedConnectionCleanupThread.checkedShutdown();
    HibernateUtil.closeSessionFactory();
  }
}
