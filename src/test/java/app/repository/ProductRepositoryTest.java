package app.repository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import app.model.Product;
import app.model.Vendor;
import app.model.category.Clothes;
import app.model.category.types.ClothesTypes;

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

    Optional<Product> optProduct = productRepository.findByProductIdentifier(shirt.getProductIdentifier());
    assertTrue(optProduct.isPresent());
    if (optProduct.isPresent()) {
      Clothes retrieved = (Clothes) optProduct.get();
      assertEquals(ClothesTypes.Types.T_SHIRT, retrieved.getType());
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

  // @Test
  // void shouldFindProductByVendorAndProductName() {
  //   Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
  //   Vendor vendor = new Vendor("TestVendor");
  //   Product testProduct = new Product(1L, 1000.00, "Name", "Location", 10.0, dimensions, vendor);
  //   productRepository.save(testProduct);

  //   Optional<Product> product = productRepository.findByVendorAndProductName(vendor.getVendorName(), testProduct.getProductName());
  //   assertTrue(product.isPresent());
  //   assertEquals(testProduct.getProductName(), product.get().getProductName());
  //   assertEquals(testProduct.getProductIdentifier(), product.get().getProductIdentifier());
  // }
}
