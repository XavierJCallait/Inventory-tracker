package util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

public class AppShutdown {
  /**
   * Cleans up resources and shuts down the application gracefully. Should only be called once
   * during application shutdown.
   */
  public static void shutdown() {
    AbandonedConnectionCleanupThread.checkedShutdown();
    HibernateUtil.closeSessionFactory();
  }
}
