package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({ProductService.class, VendorService.class})
public class ProductServiceTest {
  @Autowired private ProductService productService;
  @Autowired private VendorService vendorService;

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
  public void shouldFindProductById() {
    vendorService.createVendor(vendor1);
    Product createdProduct = productService.createProduct(shirt);

    assertEquals(createdProduct.getProductIdentifier(), shirt.getProductIdentifier());
    Product foundProduct = productService.findProductById(createdProduct.getProductIdentifier());
    assertEquals(foundProduct.getProductIdentifier(), shirt.getProductIdentifier());
  }

  @Test
  public void shouldFindProductsByName() {
    vendorService.createVendor(vendor1);
    productService.createProduct(shirt);
    productService.createProduct(pants);

    Page<Product> foundProducts = productService.findProductsByName("Shirt", Pageable.unpaged());
    assertEquals(1, foundProducts.getTotalElements());
    assertEquals("Shirt", foundProducts.getContent().get(0).getProductName());
  }

  @Test
  public void shouldFindProductsByVendor() {
    vendorService.createVendor(vendor1);
    productService.createProduct(shirt);
    productService.createProduct(pants);

    Page<Product> foundProducts = productService.findProductsByVendor(vendor1, Pageable.unpaged());
    assertEquals(2, foundProducts.getTotalElements());
  }

  @Test
  public void shouldFindProductByVendorAndName() {
    vendorService.createVendor(vendor1);
    productService.createProduct(shirt);
    productService.createProduct(pants);

    Product foundProducts = productService.findProductByVendorAndName(vendor1, "Shirt");
    assertNotNull(foundProducts);
    assertEquals("Shirt", foundProducts.getProductName());
  }

  @Test
  public void shouldFindProductsByMinimumDiscount() {
    vendorService.createVendor(vendor1);
    milk1.changeDiscount(10.0);
    milk2.changeDiscount(50.0);
    productService.createProduct(milk1);
    productService.createProduct(milk2);
    productService.createProduct(milk3);

    Page<Product> foundProducts =
        productService.findProductsByMinimumDiscount(20.0, Pageable.unpaged());
    assertEquals(1, foundProducts.getTotalElements());
    assertEquals(
        milk2.getProductIdentifier(), foundProducts.getContent().get(0).getProductIdentifier());
  }

  @Test
  public void shouldFindProductsByPriceRange() {
    vendorService.createVendor(vendor1);
    productService.createProduct(shirt);
    productService.createProduct(pants);

    Page<Product> foundProducts =
        productService.findProductsByPriceRange(10.0, 20.0, Pageable.unpaged());
    assertEquals(1, foundProducts.getTotalElements());
    assertEquals("Shirt", foundProducts.getContent().get(0).getProductName());
  }

  @Test
  public void shouldFindProductsByType() {
    vendorService.createVendor(vendor1);
    productService.createProduct(shirt);
    productService.createProduct(pants);

    Page<Product> foundProducts = productService.findProductsByType("T_Shirt", Pageable.unpaged());
    assertEquals(1, foundProducts.getTotalElements());
  }

  @Test
  public void shouldGetAllProducts() {
    vendorService.createVendor(vendor1);
    vendorService.createVendor(vendor2);
    productService.createProduct(laptop1);
    productService.createProduct(laptop2);

    Page<Product> foundProducts = productService.getAllProducts(Pageable.unpaged());
    assertEquals(2, foundProducts.getTotalElements());
  }
}
