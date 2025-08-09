package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class VendorTest {
  @Test
  void shouldInitializeVendor() {
    Vendor vendor = new Vendor("VendorName");
    assertNotNull(vendor);
    assertEquals("VendorName", vendor.getVendorName());
    assertEquals(0, vendor.getProducts().size());
  }
}
