package app.model;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class VendorTest {
  @Test
  void shouldInitializeVendor() {
    Vendor vendor = new Vendor("VendorName", UUID.randomUUID());
    assertNotNull(vendor);
    assertEquals("VendorName", vendor.getVendorName());
    assertNotNull(vendor.getIdentifier());
    assertEquals(0, vendor.getProducts().size());
  }
}
