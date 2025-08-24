package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.model.Vendor;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(VendorService.class)
public class VendorServiceTest {
  @Autowired private VendorService vendorService;

  String vendorName1 = "TestVendor1";
  String vendorName2 = "TestVendor2";
  String vendorName3 = "TestVendor3";
  Vendor testVendor1 = new Vendor(vendorName1);
  Vendor testVendor2 = new Vendor(vendorName2);
  Vendor testVendor3 = new Vendor(vendorName3);

  @Test
  void shouldCreateTestVendor1() {
    Vendor createdVendor = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName(vendorName1);
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotCreateTestVendor2WithDuplicateVendorName1() {
    Vendor createdVendor1 = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor1.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor1.getVendorIdentifier());

    Vendor vendorWithSameNameDifferentUUID = new Vendor(vendorName1);
    Vendor createdVendor2 = vendorService.createVendor(vendorWithSameNameDifferentUUID);
    assertNotEquals(
        vendorWithSameNameDifferentUUID.getVendorIdentifier(),
        createdVendor2.getVendorIdentifier());
    assertEquals(createdVendor1.getVendorIdentifier(), createdVendor2.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName(vendorName1);
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor1.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor1.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
    assertNotEquals(testVendor2.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldGetTestVendor1ByTestVendor1Identifier() {
    Vendor createdVendor = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorById(createdVendor.getVendorIdentifier());
    assertNotNull(retrievedVendor);
    assertEquals(testVendor1.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotGetTestVendor1ByIdentifier() {
    Vendor createdVendor = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorById(UUID.randomUUID());
    assertNull(retrievedVendor);
  }

  @Test
  void shouldGetTestVendor1ByVendorName1() {
    Vendor createdVendor = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName(vendorName1);
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotGetTestVendor1ByVendorName2() {
    Vendor createdVendor = vendorService.createVendor(testVendor1);
    assertEquals(testVendor1.getVendorName(), createdVendor.getVendorName());
    assertEquals(testVendor1.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName(vendorName2);
    assertNull(retrievedVendor);
  }

  @Test
  void shouldGetAllTestVendors() {
    Vendor createdVendor1 = vendorService.createVendor(testVendor1);
    Vendor createdVendor2 = vendorService.createVendor(testVendor2);
    Vendor createdVendor3 = vendorService.createVendor(testVendor3);
    assertEquals(testVendor1.getVendorName(), createdVendor1.getVendorName());
    assertEquals(testVendor2.getVendorName(), createdVendor2.getVendorName());
    assertEquals(testVendor3.getVendorName(), createdVendor3.getVendorName());

    Pageable pageable = PageRequest.of(0, 2);
    Page<Vendor> allVendors = vendorService.getAllVendors(pageable);
    assertTrue(allVendors.hasContent());
    assertEquals(3, allVendors.getTotalElements());
    assertEquals(2, allVendors.getContent().size());
    pageable = PageRequest.of(1, 2);
    allVendors = vendorService.getAllVendors(pageable);
    assertTrue(allVendors.hasContent());
    assertEquals(3, allVendors.getTotalElements());
    assertEquals(1, allVendors.getContent().size());
  }

  @Test
  void shouldGetNoVendors() {
    Pageable pageable = PageRequest.of(0, 2);
    Page<Vendor> allVendors = vendorService.getAllVendors(pageable);
    assertFalse(allVendors.hasContent());
    assertEquals(0, allVendors.getTotalElements());
    assertEquals(0, allVendors.getContent().size());
  }
}
