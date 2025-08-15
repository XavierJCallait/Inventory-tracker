package app.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import app.model.Product;
import app.model.Vendor;
import app.model.category.types.ElectronicsTypes;
import org.junit.jupiter.api.Test;

class ElectronicsTest {
  @Test
  void shouldInitializeElectronics() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Electronics laptop =
        new Electronics(
            1L,
            999.99,
            "Laptop",
            "Location",
            10.0,
            dimensions,
            vendor,
            600.0,
            5.0,
            120.0,
            ElectronicsTypes.ConnectivityTypes.BOTH,
            ElectronicsTypes.PowerSourceTypes.RECHARGEABLE,
            ElectronicsTypes.CurrentTypes.DC,
            ElectronicsTypes.Types.LAPTOP);
    assertNotNull(laptop);
    assertEquals(1L, laptop.getQuantity());
    assertEquals(999.99, laptop.getPrice());
    assertEquals("Laptop", laptop.getName());
    assertEquals("Location", laptop.getLocation());
    assertEquals(10.0, laptop.getWeight());
    assertEquals(dimensions, laptop.getDimensions());
    assertEquals(vendor.getVendorIdentifier(), laptop.getVendorID());
    assertEquals(600.0, laptop.getPower());
    assertEquals(5.0, laptop.getCurrent());
    assertEquals(120.0, laptop.getVoltage());
    assertEquals(ElectronicsTypes.ConnectivityTypes.BOTH, laptop.getConnectivityType());
    assertEquals(ElectronicsTypes.PowerSourceTypes.RECHARGEABLE, laptop.getPowerSourceType());
    assertEquals(ElectronicsTypes.CurrentTypes.DC, laptop.getCurrentType());
    assertEquals(ElectronicsTypes.Types.LAPTOP, laptop.getElectronicsType());
  }
}
