package app;

import java.util.Properties;
import model.Product;
import service.Inventory;
import util.AppShutdown;
import util.DatabaseManager;
import util.EnvironmentVariableInitializer;
import util.HibernateUtil;

public class InventoryApp {
  public static void main(String[] args) {
    System.out.println("Starting Inventory Application...");
    Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
    DatabaseManager.initializeDatabase(databaseProperties);
    HibernateUtil.initializeSessionFactory(databaseProperties);

    Inventory inventory = new Inventory();
    Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
    inventory.addProduct(product.getIdentifier(), product);
    if (inventory.getProduct(product.getIdentifier()) != null) {
      System.out.println("Product is in inventory!");
      inventory.removeProduct(product.getIdentifier(), product);
    }

    AppShutdown.shutdown();
    System.out.println("Inventory Application has been shut down.");
  }
}
