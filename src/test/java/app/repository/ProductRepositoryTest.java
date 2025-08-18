package app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import app.model.Product;
import app.model.Vendor;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProductRepositoryTest {
  @Autowired private ProductRepository productRepository;

  @Test
  void shouldFindProductByIdentifier() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Product testProduct = new Product(1L, 1000.00, "Name", "Location", 10.0, dimensions, vendor);
    productRepository.save(testProduct);

    Optional<Product> product = productRepository.findByProductIdentifier(testProduct.getProductIdentifier());
    assertTrue(product.isPresent());
    assertEquals(testProduct.getProductName(), product.get().getProductName());
    assertEquals(testProduct.getProductIdentifier(), product.get().getProductIdentifier());
  }

  @Test
  void shouldNotFindProductByIdentifier() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("TestVendor");
    Product testProduct = new Product(1L, 1000.00, "Name", "Location", 10.0, dimensions, vendor);
    productRepository.save(testProduct);

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
