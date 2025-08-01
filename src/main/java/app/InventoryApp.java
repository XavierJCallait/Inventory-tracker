package app;

import model.Product;
import service.Inventory;

public class InventoryApp {
    public static void main(String[] args) {
        // Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
        // DatabaseManager.initializeDatabase(databaseProperties);
        // HibernateUtil.initializeSessionFactory(databaseProperties);
        // SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Inventory inventory = new Inventory();
        Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
        inventory.addProduct(product.getIdentifier(), product);

        // AppShutdown.Shutdown();
    }
}