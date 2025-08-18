package app.service;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import app.model.Vendor;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(VendorService.class)
public class VendorServiceTest {
  @Autowired private VendorService vendorService;

  @Test
  void shouldCreateVendor() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    assertEquals(vendor.getVendorName(), createdVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName("TestVendor");
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotCreateVendorWithDuplicateName() {
    Vendor vendor1 = new Vendor("TestVendor");
    Vendor createdVendor1 = vendorService.createVendor(vendor1);
    assertEquals(vendor1.getVendorName(), createdVendor1.getVendorName());
    assertEquals(vendor1.getVendorIdentifier(), createdVendor1.getVendorIdentifier());

    Vendor vendor2 = new Vendor("TestVendor");
    Vendor createdVendor2 = vendorService.createVendor(vendor2);
    assertNotEquals(vendor2.getVendorIdentifier(), createdVendor2.getVendorIdentifier());
    assertEquals(createdVendor1.getVendorIdentifier(), createdVendor2.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName("TestVendor");
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor1.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor1.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
    assertNotEquals(vendor2.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldGetVendorById() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    assertEquals(vendor.getVendorName(), createdVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorById(createdVendor.getVendorIdentifier());
    assertNotNull(retrievedVendor);
    assertEquals(vendor.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotGetVendorById() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    assertEquals(vendor.getVendorName(), createdVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorById(UUID.randomUUID());
    assertNull(retrievedVendor);
  }

  @Test
  void shouldGetVendorByName() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    assertEquals(vendor.getVendorName(), createdVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName("TestVendor");
    assertNotNull(retrievedVendor);
    assertEquals(createdVendor.getVendorName(), retrievedVendor.getVendorName());
    assertEquals(createdVendor.getVendorIdentifier(), retrievedVendor.getVendorIdentifier());
  }

  @Test
  void shouldNotGetVendorByName() {
    Vendor vendor = new Vendor("TestVendor");
    Vendor createdVendor = vendorService.createVendor(vendor);
    assertEquals(vendor.getVendorName(), createdVendor.getVendorName());
    assertEquals(vendor.getVendorIdentifier(), createdVendor.getVendorIdentifier());

    Vendor retrievedVendor = vendorService.getVendorByName("RealVendor");
    assertNull(retrievedVendor);
  }

  @Test
  void shouldGetAllVendors() {
    Vendor vendor1 = new Vendor("TestVendor1");
    Vendor vendor2 = new Vendor("TestVendor2");
    Vendor vendor3 = new Vendor("TestVendor3");
    Vendor createdVendor1 = vendorService.createVendor(vendor1);
    Vendor createdVendor2 = vendorService.createVendor(vendor2);
    Vendor createdVendor3 = vendorService.createVendor(vendor3);
    assertEquals(vendor1.getVendorName(), createdVendor1.getVendorName());
    assertEquals(vendor2.getVendorName(), createdVendor2.getVendorName());
    assertEquals(vendor3.getVendorName(), createdVendor3.getVendorName());

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
