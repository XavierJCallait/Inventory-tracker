package app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import app.model.Product;
import app.model.Vendor;
import app.model.category.Clothes;
import app.model.category.Electronics;
import app.model.category.types.ClothesTypes;
import app.model.category.types.ElectronicsTypes;

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
          assertEquals(ElectronicsTypes.Types.LAPTOP, ElectronicsTypes.Types.fromDbValue(e.getType()));
        }
      }
      assertNotEquals(products.get(0).getVendorID(), products.get(1).getVendorID());
    }
  }
}
