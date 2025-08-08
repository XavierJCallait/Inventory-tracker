package app;

import app.model.Vendor;
import app.repository.VendorRepository;
import app.util.DatabaseManager;
import app.util.EnvironmentVariableInitializer;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApp {

  public static void main(String[] args) {
    Properties databaseProperties = EnvironmentVariableInitializer.getEnvironmentProperties();
    DatabaseManager.initializeDatabase(databaseProperties);
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
