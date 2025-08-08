package app.model;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/** Unit tests for Product model. */
class ProductTest {
  @Test
  void shouldInitializeProduct() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName", UUID.randomUUID());
    Product product =
        new Product(1L, 1000.00, "Name", UUID.randomUUID(), "Location", 10.0, dimensions, vendor);
    assertNotNull(product);
    assertEquals(1L, product.getQuantity());
    assertEquals(1000.00, product.getPrice());
    assertEquals("Name", product.getName());
    assertEquals("Location", product.getLocation());
    assertEquals(10.0, product.getWeight());
    assertEquals(dimensions, product.getDimensions());
    assertEquals(vendor.getIdentifier(), product.getVendorID());
    assertNotNull(product.getIdentifier());
  }

  @Test
  void shouldUpdateProduct() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName", UUID.randomUUID());
    Product product =
        new Product(1L, 1000.00, "Name", UUID.randomUUID(), "Location", 10.0, dimensions, vendor);
    product.changePrice(100.00);
    product.changeQuantity(10L);
    product.changeLocation("New Location");
    assertEquals(100.00, product.getPrice());
    assertEquals(10L, product.getQuantity());
    assertEquals("New Location", product.getLocation());
  }
}
