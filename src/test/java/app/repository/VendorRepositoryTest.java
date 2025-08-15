package app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.model.Vendor;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class VendorRepositoryTest {

  @Autowired private VendorRepository vendors;

  @Test
  void shouldFindVendorByName() {
    Vendor testVendor = new Vendor("TestVendor");
    vendors.save(testVendor);

    Optional<Vendor> vendor = vendors.findByVendorName("TestVendor");
    assertTrue(vendor.isPresent());
    assertEquals(testVendor.getVendorName(), vendor.get().getVendorName());
    assertEquals(testVendor.getVendorIdentifier(), vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindVendorByName() {
    Vendor realVendor = new Vendor("RealVendor");
    vendors.save(realVendor);

    Optional<Vendor> vendor = vendors.findByVendorName("TestVendor");
    assertTrue(vendor.isEmpty());
  }

  @Test
  void shouldFindVendorByIdentifier() {
    Vendor testVendor = new Vendor("TestVendor");
    vendors.save(testVendor);

    Optional<Vendor> vendor = vendors.findByVendorIdentifier(testVendor.getVendorIdentifier());
    assertTrue(vendor.isPresent());
    assertEquals(testVendor.getVendorName(), vendor.get().getVendorName());
    assertEquals(testVendor.getVendorIdentifier(), vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindVendorByIdentifier() {
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

  @Test
  void shouldNotFindExistingVendorByName() {
    Vendor realVendor = new Vendor("RealVendor");
    vendors.save(realVendor);

    Boolean exists = vendors.existsByVendorName("TestVendor");
    assertFalse(exists);
  }
}
