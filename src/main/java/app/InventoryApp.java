package app;

import java.util.Properties;
import model.Product;
import model.Vendor;
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
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Object extra = new Object();
    Product<Object> product =
        new Product<>(1L, 1000.00, "Name", "Location", dimensions, vendor, extra);
    inventory.addProduct(product.getIdentifier(), product);
    if (inventory.getProduct(product.getIdentifier()) != null) {
      logger.debug("Product is in inventory!");
      inventory.removeProduct(product.getIdentifier(), product);
    }

    AppShutdown.shutdown();
    logger.info("Inventory Application has been shut down.");
  }
}
