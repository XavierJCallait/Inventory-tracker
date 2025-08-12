package app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.model.Vendor;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
// @ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class VendorRepositoryTest {

  @Autowired private VendorRepository vendors;

  @Test
  void shouldFindVendorByName() {
    Vendor testVendor = new Vendor("TestVendor");
    vendors.save(testVendor);

    Optional<Vendor> vendor = vendors.findByVendorName("TestVendor");
    assertTrue(vendor.isPresent());
    assertEquals("TestVendor", vendor.get().getVendorName());
    assertNotNull(vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindVendorByName() {
    Vendor realVendor = new Vendor("RealVendor");
    vendors.save(realVendor);

    Optional<Vendor> vendor = vendors.findByVendorName("TestVendor");
    assertTrue(vendor.isEmpty());
  }

  @Test
  void shouldFindVendorById() {
    Vendor testVendor = new Vendor("TestVendor");
    vendors.save(testVendor);

    Optional<Vendor> vendor = vendors.findByVendorIdentifier(testVendor.getVendorIdentifier());
    assertTrue(vendor.isPresent());
    assertEquals("TestVendor", vendor.get().getVendorName());
    assertNotNull(vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindVendorById() {
    Vendor realVendor = new Vendor("RealVendor");
    vendors.save(realVendor);

    Optional<Vendor> vendor = vendors.findByVendorIdentifier(UUID.randomUUID());
    assertTrue(vendor.isEmpty());
  }

  @Test
  void shouldFindExistingVendorByName() {
    Vendor testVendor = new Vendor("TestVendor");
    vendors.save(testVendor);

    Boolean exists = vendors.existsByVendorName("TestVendor");
    assertTrue(exists);
  }
}
