package app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.model.Product;
import app.model.Vendor;
import app.model.category.Clothes;
import app.model.category.Electronics;
import app.model.category.Food;
import app.model.category.Food.NutritionValue;
import app.model.category.types.ClothesTypes;
import app.model.category.types.ElectronicsTypes;
import app.model.category.types.FoodTypes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryTest {
  @Autowired private ProductRepository productRepository;
  @Autowired private VendorRepository vendorRepository;

  @Test
  void shouldFindProductByIdentifier() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    vendorRepository.save(vendor);
    productRepository.save(shirt);

    Optional<Product> optProduct =
        productRepository.findByProductIdentifier(shirt.getProductIdentifier());
    assertTrue(optProduct.isPresent());
    if (optProduct.isPresent()) {
      Clothes retrieved = (Clothes) optProduct.get();
      assertEquals(ClothesTypes.Types.T_SHIRT, ClothesTypes.Types.fromDbValue(retrieved.getType()));
    }
    assertEquals(shirt.getProductName(), optProduct.get().getProductName());
    assertEquals(shirt.getProductIdentifier(), optProduct.get().getProductIdentifier());
  }

  @Test
  void shouldNotFindProductByIdentifier() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    vendorRepository.save(vendor);
    productRepository.save(shirt);

    Optional<Product> product = productRepository.findByProductIdentifier(UUID.randomUUID());
    assertTrue(product.isEmpty());
  }

  @Test
  void shouldFindProductByNameOfDifferentVendors() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor1 = new Vendor("TestVendor1");
    Electronics laptop1 =
        new Electronics(
            1L,
            999.99,
            "Laptop",
            "Location",
            10.0,
            dimensions,
            vendor1,
            600.0,
            5.0,
            120.0,
            ElectronicsTypes.ConnectivityTypes.BOTH,
            ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
            ElectronicsTypes.CurrentTypes.DC,
            ElectronicsTypes.Types.LAPTOP);
    Vendor vendor2 = new Vendor("TestVendor2");
    Electronics laptop2 =
        new Electronics(
            1L,
            999.99,
            "Laptop",
            "Location",
            10.0,
            dimensions,
            vendor2,
            600.0,
            5.0,
            120.0,
            ElectronicsTypes.ConnectivityTypes.BOTH,
            ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
            ElectronicsTypes.CurrentTypes.DC,
            ElectronicsTypes.Types.LAPTOP);
    vendorRepository.save(vendor1);
    vendorRepository.save(vendor2);
    productRepository.save(laptop1);
    productRepository.save(laptop2);
    Page<Product> productPage = productRepository.findByProductName("Laptop", PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(2, products.size());
      assertEquals("Laptop", products.get(0).getProductName());
      assertEquals("Laptop", products.get(1).getProductName());
      for (Product p : products) {
        if (p instanceof Electronics e) {
          assertEquals(
              ElectronicsTypes.Types.LAPTOP, ElectronicsTypes.Types.fromDbValue(e.getType()));
        }
      }
      assertNotEquals(products.get(0).getVendorID(), products.get(1).getVendorID());
    }
  }

  @Test
  void shouldFindProductByNameOfSameVendor() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk1 =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    Food milk2 =
        new Food(
            1L,
            1.25,
            "Milk",
            "Location",
            5.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    vendorRepository.save(vendor);
    productRepository.save(milk1);
    productRepository.save(milk2);
    Page<Product> productPage = productRepository.findByProductName("Milk", PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(2, products.size());
      assertEquals("Milk", products.get(0).getProductName());
      assertEquals("Milk", products.get(1).getProductName());
      for (Product p : products) {
        if (p instanceof Food f) {
          assertEquals(FoodTypes.Types.DAIRY, FoodTypes.Types.fromDbValue(f.getType()));
        }
      }
      assertEquals(products.get(0).getVendorID(), products.get(1).getVendorID());
    }
  }

  @Test
  void shouldNotFindProductByName() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Electronics laptop =
        new Electronics(
            1L,
            999.99,
            "Laptop",
            "Location",
            10.0,
            dimensions,
            vendor,
            600.0,
            5.0,
            120.0,
            ElectronicsTypes.ConnectivityTypes.BOTH,
            ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
            ElectronicsTypes.CurrentTypes.DC,
            ElectronicsTypes.Types.LAPTOP);
    vendorRepository.save(vendor);
    productRepository.save(laptop);
    Page<Product> productPage = productRepository.findByProductName("Phone", PageRequest.of(0, 3));
    assertTrue(productPage.isEmpty());
  }

  @Test
  void shouldFindByVendor() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    Clothes pants =
        new Clothes(
            1L,
            49.99,
            "Pants",
            "Location",
            12.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.LARGE,
            ClothesTypes.MaterialTypes.DENIM,
            ClothesTypes.Types.PANTS);
    vendorRepository.save(vendor);
    productRepository.save(shirt);
    productRepository.save(pants);
    Page<Product> productPage = productRepository.findByVendor(vendor, PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(2, products.size());
      assertEquals("Shirt", products.get(0).getProductName());
      assertEquals("Pants", products.get(1).getProductName());
      assertEquals(vendor.getVendorIdentifier(), products.get(0).getVendorID());
      assertEquals(vendor.getVendorIdentifier(), products.get(1).getVendorID());
    }
  }

  @Test
  void shouldFindByVendorAndProductName() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    Clothes pants =
        new Clothes(
            1L,
            49.99,
            "Pants",
            "Location",
            12.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.LARGE,
            ClothesTypes.MaterialTypes.DENIM,
            ClothesTypes.Types.PANTS);
    vendorRepository.save(vendor);
    productRepository.save(shirt);
    productRepository.save(pants);
    Optional<Product> optProduct = productRepository.findByVendorAndProductName(vendor, "Shirt");
    assertTrue(optProduct.isPresent());
    if (optProduct.isPresent()) {
      Clothes retrieved = (Clothes) optProduct.get();
      assertEquals(shirt.getProductName(), retrieved.getProductName());
      assertEquals(ClothesTypes.Types.T_SHIRT, ClothesTypes.Types.fromDbValue(retrieved.getType()));
    }
  }

  @Test
  void shouldFindAllByPriceBetween() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk1 =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    Food milk2 =
        new Food(
            1L,
            1.25,
            "Milk",
            "Location",
            5.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    Food milk3 =
        new Food(
            1L,
            0.25,
            "Milk",
            "Location",
            1.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    vendorRepository.save(vendor);
    productRepository.save(milk1);
    productRepository.save(milk2);
    productRepository.save(milk3);
    Page<Product> productPage =
        productRepository.findAllByPriceBetween(0.0, 2.0, PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(2, products.size());
      assertEquals("Milk", products.get(0).getProductName());
      assertEquals(1.25, products.get(0).getPrice());
      assertEquals(0.25, products.get(1).getPrice());
      for (Product p : products) {
        if (p instanceof Food f) {
          assertEquals(FoodTypes.Types.DAIRY, FoodTypes.Types.fromDbValue(f.getType()));
        }
      }
    }
  }

  @Test
  void shouldFindAllByDiscountGreaterThan() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk1 =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    Food milk2 =
        new Food(
            1L,
            1.25,
            "Milk",
            "Location",
            5.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    Food milk3 =
        new Food(
            1L,
            0.25,
            "Milk",
            "Location",
            1.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    vendorRepository.save(vendor);
    milk1.changeDiscount(20.0);
    milk2.changeDiscount(40.0);
    productRepository.save(milk1);
    productRepository.save(milk2);
    productRepository.save(milk3);
    Page<Product> productPage =
        productRepository.findAllByDiscountGreaterThan(30.0, PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(1, products.size());
      assertEquals("Milk", products.get(0).getProductName());
      assertEquals(1.25, products.get(0).getPrice());
      assertEquals(40.0, products.get(0).getDiscount());
      for (Product p : products) {
        if (p instanceof Food f) {
          assertEquals(FoodTypes.Types.DAIRY, FoodTypes.Types.fromDbValue(f.getType()));
        }
      }
    }
  }

  @Test
  void shouldNotFindAnyByDiscountGreaterThan() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    vendorRepository.save(vendor);
    productRepository.save(milk);
    Page<Product> productPage =
        productRepository.findAllByDiscountGreaterThan(10.0, PageRequest.of(0, 3));
    assertTrue(productPage.isEmpty());
  }

  @Test
  void shouldExistByProductName() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    vendorRepository.save(vendor);
    productRepository.save(shirt);

    Boolean productExists = productRepository.existsByProductName("Shirt");
    assertTrue(productExists);
  }

  @Test
  void shouldNotExistByProductName() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Electronics laptop =
        new Electronics(
            1L,
            999.99,
            "Laptop",
            "Location",
            10.0,
            dimensions,
            vendor,
            600.0,
            5.0,
            120.0,
            ElectronicsTypes.ConnectivityTypes.BOTH,
            ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
            ElectronicsTypes.CurrentTypes.DC,
            ElectronicsTypes.Types.LAPTOP);
    vendorRepository.save(vendor);
    productRepository.save(laptop);
    Boolean productExists = productRepository.existsByProductName("Phone");
    assertFalse(productExists);
  }
}
