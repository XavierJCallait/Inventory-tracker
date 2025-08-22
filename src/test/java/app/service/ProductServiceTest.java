package app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import app.repository.ProductRepository;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductServiceTest {
  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testFindProductById() {
    // Implement test
  }

  @Test
  public void testFindProductsByName() {
    // Implement test
  }

  @Test
  public void testFindProductsByVendor() {
    // Implement test
  }

  @Test
  public void testFindProductByVendorAndName() {
    // Implement test
  }

  @Test
  public void testFindProductsByMinimumDiscount() {
    // Implement test
  }

  @Test
  public void testFindProductsByPriceRange() {
    // Implement test
  }

  @Test
  public void testFindProductsByCategory() {
    // Implement test
  }

  @Test
  public void testGetAllProducts() {
    // Implement test
  }
}
