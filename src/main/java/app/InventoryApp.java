package app;

import java.util.Properties;

import org.hibernate.SessionFactory;

import model.Product;
import service.Inventory;
import util.AppShutdown;
import util.DatabaseManager;
import util.EnvironmentVariableInitializer;
import util.HibernateUtil;

public class InventoryApp {
    public static void main(String[] args) {
        Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
        DatabaseManager.initializeDatabase(databaseProperties);
        HibernateUtil.initializeSessionFactory(databaseProperties);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Inventory inventory = new Inventory();
        Product product = new Product("laptop", 1000.00, "Electronics", "Warehouse A", 50);
        inventory.addProduct(product.getIdentifier(), product);

        AppShutdown.Shutdown();
    }
}