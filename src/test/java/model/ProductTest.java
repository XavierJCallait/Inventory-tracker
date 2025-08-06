package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/** Unit tests for Product model. */
class ProductTest {
  @Test
  void shouldInitializeProduct() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Object extra = new Object();
    Product<Object> product =
        new Product<>(1L, 1000.00, "Name", "Location", dimensions, vendor, extra);
    assertNotNull(product);
    assertEquals(1L, product.getQuantity());
    assertEquals(1000.00, product.getPrice());
    assertEquals("Name", product.getName());
    assertEquals("Location", product.getLocation());
    assertEquals(dimensions, product.getDimensions());
    assertEquals(vendor, product.getVendor());
    assertNotNull(product.getIdentifier());
    assertEquals(extra, product.getType());
  }

  @Test
  void shouldUpateProduct() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Object extra = new Object();
    Product<Object> product =
        new Product<>(1L, 1000.00, "Name", "Location", dimensions, vendor, extra);
    product.changePrice(100.00);
    product.changeQuantity(10L);
    product.changeLocation("New Location");
    assertEquals(100.00, product.getPrice());
    assertEquals(10L, product.getQuantity());
    assertEquals("New Location", product.getLocation());
  }
}
