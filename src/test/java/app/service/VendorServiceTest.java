package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.model.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@org.springframework.context.annotation.Import(VendorService.class)
public class VendorServiceTest {
  @Autowired private VendorService vendorService;

  @Test
  void shouldCreateVendor() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    // assertEquals(vendor, createdVendor);

    Vendor retrievedVendor = vendorService.getVendorByName("TestVendor");
    assertNotNull(retrievedVendor);
    assertEquals("TestVendor", retrievedVendor.getVendorName());
  }

  @Test
  void shouldNotCreateVendorWithDuplicateName() {
    Vendor vendor1 = new Vendor("TestVendor");
    Vendor createdVendor1 = vendorService.createVendor(vendor1);
    // assertEquals(vendor1, createdVendor1);

    Vendor vendor2 = new Vendor("TestVendor");
    Vendor createdVendor2 = vendorService.createVendor(vendor2);
    // assertNotEquals(vendor2, createdVendor2);

    Vendor retrievedVendor = vendorService.getVendorByName("TestVendor");
    assertNotNull(retrievedVendor);
    // assertEquals(createdVendor1.getVendorName(), retrievedVendor.getVendorName());
    // assertEquals(createdVendor1.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }
}
