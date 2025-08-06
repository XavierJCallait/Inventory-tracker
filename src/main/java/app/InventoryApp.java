package app;

import java.util.Properties;
import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Inventory;
import util.AppShutdown;
import util.DatabaseManager;
import util.EnvironmentVariableInitializer;
import util.HibernateUtil;

public class InventoryApp {
  private static final Logger logger = LoggerFactory.getLogger(InventoryApp.class);

  public static void main(String[] args) {
    logger.info("Starting Inventory Application...");
    Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
    DatabaseManager.initializeDatabase(databaseProperties);
    HibernateUtil.initializeSessionFactory(databaseProperties);

    Inventory inventory = new Inventory();
    Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
    inventory.addProduct(product.getIdentifier(), product);
    if (inventory.getProduct(product.getIdentifier()) != null) {
      logger.debug("Product is in inventory!");
      inventory.removeProduct(product.getIdentifier(), product);
    }

    AppShutdown.shutdown();
    logger.info("Inventory Application has been shut down.");
  }
}
