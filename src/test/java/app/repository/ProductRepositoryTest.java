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

  Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
  NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
  Vendor vendor1 = new Vendor("TestVendor1");
  Vendor vendor2 = new Vendor("TestVendor2");
  Clothes shirt =
      new Clothes(
          1L,
          19.99,
          "Shirt",
          "Location",
          10.0,
          dimensions,
          vendor1,
          "Blue",
          ClothesTypes.SizeTypes.MEDIUM,
          ClothesTypes.MaterialTypes.COTTON,
          ClothesTypes.Types.T_SHIRT);
  Clothes pants =
      new Clothes(
          2L,
          29.99,
          "Pants",
          "Location",
          10.0,
          dimensions,
          vendor1,
          "Black",
          ClothesTypes.SizeTypes.LARGE,
          ClothesTypes.MaterialTypes.WOOL,
          ClothesTypes.Types.PANTS);
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
  Food milk1 =
      new Food(
          1L,
          2.5,
          "Milk",
          "Location",
          10.0,
          dimensions,
          vendor1,
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
          vendor1,
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
          vendor1,
          true,
          FoodTypes.StorageTemperatureTypes.REFRIGERATED,
          FoodTypes.PackageTypes.JUG,
          Instant.now().plusSeconds(604800),
          nutritionValue,
          FoodTypes.Types.DAIRY);

  @Test
  void shouldFindShirtProductByShirtIdentifier() {
    vendorRepository.save(vendor1);
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
  void shouldNotFindShirtProductByRandomIdentifier() {
    vendorRepository.save(vendor1);
    productRepository.save(shirt);

    Optional<Product> product = productRepository.findByProductIdentifier(UUID.randomUUID());
    assertTrue(product.isEmpty());
  }

  @Test
  void shouldFindLaptop1AndLaptop2ProductByNameOfDifferentVendor1AndVendor2() {
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
  void shouldFindMilk1AndMilk2ProductByNameOfSameVendor1() {
    vendorRepository.save(vendor1);
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
  void shouldNotFindLaptop1ProductByName() {
    vendorRepository.save(vendor1);
    productRepository.save(laptop1);
    Page<Product> productPage = productRepository.findByProductName("Phone", PageRequest.of(0, 3));
    assertTrue(productPage.isEmpty());
  }

  @Test
  void shouldFindShirtAndPantsByVendor() {
    vendorRepository.save(vendor1);
    productRepository.save(shirt);
    productRepository.save(pants);
    Page<Product> productPage = productRepository.findByVendor(vendor1, PageRequest.of(0, 3));
    assertTrue(productPage.hasContent());
    if (productPage.hasContent()) {
      List<Product> products = productPage.getContent();
      assertEquals(2, products.size());
      assertEquals("Shirt", products.get(0).getProductName());
      assertEquals("Pants", products.get(1).getProductName());
      assertEquals(vendor1.getVendorIdentifier(), products.get(0).getVendorID());
      assertEquals(vendor1.getVendorIdentifier(), products.get(1).getVendorID());
    }
  }

  @Test
  void shouldFindShirtAndPantsByVendorAndProductName() {
    vendorRepository.save(vendor1);
    productRepository.save(shirt);
    productRepository.save(pants);
    Optional<Product> optProduct = productRepository.findByVendorAndProductName(vendor1, "Shirt");
    assertTrue(optProduct.isPresent());
    if (optProduct.isPresent()) {
      Clothes retrieved = (Clothes) optProduct.get();
      assertEquals(shirt.getProductName(), retrieved.getProductName());
      assertEquals(ClothesTypes.Types.T_SHIRT, ClothesTypes.Types.fromDbValue(retrieved.getType()));
    }
  }

  @Test
  void shouldFindAllMilk1AndMilk2AndMilk3ByPriceBetween0And2() {
    vendorRepository.save(vendor1);
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
  void shouldFindAllMilk1AndMilk2ByDiscountGreaterThan30() {
    vendorRepository.save(vendor1);
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
  void shouldNotFindAnyMilk3ByDiscountGreaterThan10() {
    vendorRepository.save(vendor1);
    productRepository.save(milk3);
    Page<Product> productPage =
        productRepository.findAllByDiscountGreaterThan(10.0, PageRequest.of(0, 3));
    assertTrue(productPage.isEmpty());
  }

  @Test
  void shouldShirtExistByProductName() {
    vendorRepository.save(vendor1);
    productRepository.save(shirt);
    Boolean productExists = productRepository.existsByProductName("Shirt");
    assertTrue(productExists);
  }

  @Test
  void shouldNotLaptop1ExistenceByFalseProductName() {
    vendorRepository.save(vendor1);
    productRepository.save(laptop1);
    Boolean productExists = productRepository.existsByProductName("Phone");
    assertFalse(productExists);
  }
}
