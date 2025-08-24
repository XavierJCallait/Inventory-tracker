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

  String vendorName1 = "TestVendor1";
  String vendorName2 = "TestVendor2";
  Vendor testVendor1 = new Vendor(vendorName1);
  Vendor testVendor2 = new Vendor(vendorName2);

  @Test
  void shouldFindTestVendor1ByVendorName1() {
    vendors.save(testVendor1);

    Optional<Vendor> vendor = vendors.findByVendorName(vendorName1);
    assertTrue(vendor.isPresent());
    assertEquals(testVendor1.getVendorName(), vendor.get().getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindTestVendor2ByVendorName1() {
    vendors.save(testVendor2);

    Optional<Vendor> vendor = vendors.findByVendorName(vendorName1);
    assertTrue(vendor.isEmpty());
  }

  @Test
  void shouldFindTestVendor1ByTestVendor1Identifier() {
    vendors.save(testVendor1);

    Optional<Vendor> vendor = vendors.findByVendorIdentifier(testVendor1.getVendorIdentifier());
    assertTrue(vendor.isPresent());
    assertEquals(testVendor1.getVendorName(), vendor.get().getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), vendor.get().getVendorIdentifier());
  }

  @Test
  void shouldNotFindTestVendor2ByRandomIdentifier() {
    vendors.save(testVendor2);

    Optional<Vendor> vendor = vendors.findByVendorIdentifier(UUID.randomUUID());
    assertTrue(vendor.isEmpty());
  }

  @Test
  void shouldFindTestVendor1ExistenceByVendorName1() {
    vendors.save(testVendor1);

    Boolean exists = vendors.existsByVendorName(vendorName1);
    assertTrue(exists);
  }

  @Test
  void shouldNotFindTestVendor2ExistenceByVendorName1() {
    vendors.save(testVendor2);

    Boolean exists = vendors.existsByVendorName(vendorName1);
    assertFalse(exists);
  }
}
