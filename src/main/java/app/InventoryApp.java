package app;

import app.model.Vendor;
import app.repository.VendorRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// public class InventoryApp {
//   private static final Logger logger = LoggerFactory.getLogger(InventoryApp.class);

// public class InventoryApp {
//   private static final Logger logger = LoggerFactory.getLogger(InventoryApp.class);

//   public static void main(String[] args) {
//     logger.info("Starting Inventory Application...");
//     Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
//     DatabaseManager.initializeDatabase(databaseProperties);
//     HibernateUtil.initializeSessionFactory(databaseProperties);

//     Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
//     Vendor vendor = new Vendor("VendorName", UUID.randomUUID());
//     Food milk =
//         new Food(
//             1L,
//             2.5,
//             "Milk",
//             UUID.randomUUID(),
//             "Location",
//             10.0,
//             dimensions,
//             vendor,
//             true,
//             FoodTypes.StorageTemperatureTypes.REFRIGERATED,
//             FoodTypes.PackageTypes.JUG,
//             Instant.now().plusSeconds(604800),
//             new NutritionValue(150, 250.0, 8.0, 12.0, 5.0),
//             FoodTypes.Types.DAIRY);

//     Clothes shirt =
//         new Clothes(
//             1L,
//             19.99,
//             "Shirt",
//             UUID.randomUUID(),
//             "Location",
//             10.0,
//             dimensions,
//             vendor,
//             "Blue",
//             ClothesTypes.SizeTypes.MEDIUM,
//             ClothesTypes.MaterialTypes.COTTON,
//             ClothesTypes.Types.T_SHIRT);

//     Electronics laptop =
//         new Electronics(
//             1L,
//             999.99,
//             "Laptop",
//             UUID.randomUUID(),
//             "Location",
//             10.0,
//             dimensions,
//             vendor,
//             600.0,
//             5.0,
//             120.0,
//             ElectronicsTypes.ConnectivityTypes.WIRED,
//             ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
//             ElectronicsTypes.CurrentTypes.AC,
//             ElectronicsTypes.Types.LAPTOP);

//     Session session = HibernateUtil.getSessionFromFactory();
//     session.beginTransaction();
//     session.persist(vendor);
//     session.getTransaction().commit();
//     session.close();

//     Session sessionM = HibernateUtil.getSessionFromFactory();
//     sessionM.beginTransaction();
//     sessionM.persist(milk);
//     sessionM.getTransaction().commit();
//     sessionM.close();

//     Session sessionS = HibernateUtil.getSessionFromFactory();
//     sessionS.beginTransaction();
//     sessionS.persist(shirt);
//     sessionS.getTransaction().commit();
//     sessionS.close();

//     Session sessionL = HibernateUtil.getSessionFromFactory();
//     sessionL.beginTransaction();
//     sessionL.persist(laptop);
//     sessionL.getTransaction().commit();
//     sessionL.close();

//     AppShutdown.shutdown();
//     logger.info("Inventory Application has been shut down.");
//   }
// }

@SpringBootApplication
public class InventoryApp {

  public static void main(String[] args) {
    SpringApplication.run(InventoryApp.class, args);
  }

  @Bean
  CommandLineRunner testDatabaseConnection(VendorRepository vendorRepository) {
    return args -> {
      System.out.println("📦 Testing database connection...");

      Vendor vendor = new Vendor("Test Vendor", UUID.randomUUID());
      vendorRepository.save(vendor);

      System.out.println("✅ Saved vendor: " + vendor.getVendorName());

      List<Vendor> vendors = vendorRepository.findAll();
      System.out.println("📊 Found " + vendors.size() + " vendors in the database.");
    };
  }
}
