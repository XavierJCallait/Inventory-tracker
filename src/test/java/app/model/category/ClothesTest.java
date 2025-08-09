package app.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import app.model.Product;
import app.model.Vendor;
import app.model.category.types.ClothesTypes;

public class ClothesTest {
  @Test
  void shouldInitializeClothes() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);
    assertNotNull(shirt);
    assertEquals(1L, shirt.getQuantity());
    assertEquals(19.99, shirt.getPrice());
    assertEquals("Shirt", shirt.getName());
    assertNotNull(shirt.getIdentifier());
    assertEquals("Location", shirt.getLocation());
    assertEquals(10.0, shirt.getWeight());
    assertEquals(dimensions, shirt.getDimensions());
    assertEquals(vendor.getIdentifier(), shirt.getVendorID());
    assertEquals("Blue", shirt.getColor());
    assertEquals(ClothesTypes.SizeTypes.MEDIUM, shirt.getSize());
    assertEquals(ClothesTypes.MaterialTypes.COTTON, shirt.getMaterial());
    assertEquals(ClothesTypes.Types.T_SHIRT, shirt.getClothesType());
  }

  @Test
  void shouldUpdateClothes() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    Clothes shirt =
        new Clothes(
            1L,
            19.99,
            "Shirt",
            "Location",
            10.0,
            dimensions,
            vendor,
            "Blue",
            ClothesTypes.SizeTypes.MEDIUM,
            ClothesTypes.MaterialTypes.COTTON,
            ClothesTypes.Types.T_SHIRT);

    shirt.changePrice(15.99);
    shirt.changeQuantity(2L);
    shirt.changeLocation("New Location");

    assertEquals(15.99, shirt.getPrice());
    assertEquals(2L, shirt.getQuantity());
    assertEquals("New Location", shirt.getLocation());
  }
}
