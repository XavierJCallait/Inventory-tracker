package app.model.category;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import app.model.Product;
import app.model.Vendor;
import app.model.category.Food.NutritionValue;
import app.model.category.types.FoodTypes;

class FoodTest {
  @Test
  void shouldInitializeFood() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    assertNotNull(milk);
    assertEquals(1L, milk.getQuantity());
    assertEquals(2.5, milk.getPrice());
    assertEquals("Milk", milk.getName());
    assertEquals("Location", milk.getLocation());
    assertEquals(10.0, milk.getWeight());
    assertEquals(dimensions, milk.getDimensions());
    assertEquals(vendor.getIdentifier(), milk.getVendorID());
    assertTrue(milk.getIsOrganic());
    assertEquals(FoodTypes.StorageTemperatureTypes.REFRIGERATED, milk.getStorageTemperatureType());
    assertEquals(FoodTypes.PackageTypes.JUG, milk.getPackageType());
    assertNotNull(milk.getPackagedDate());
    assertNotNull(milk.getExpirationDate());
    assertEquals(nutritionValue, milk.getNutritionValue());
    assertEquals(FoodTypes.Types.DAIRY, milk.getFoodType());
  }

  @Test
  void shouldUpdateFood() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    milk.changePrice(4.50);
    milk.changeQuantity(60L);
    milk.changeLocation("New Location");
    assertEquals(4.50, milk.getPrice());
    assertEquals(60L, milk.getQuantity());
    assertEquals("New Location", milk.getLocation());
  }

  @Test
  void shouldBeExpired() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("VendorName");
    NutritionValue nutritionValue = new NutritionValue(150, 250.0, 8.0, 12.0, 5.0);
    Food milk =
        new Food(
            1L,
            2.5,
            "Milk",
            "Location",
            10.0,
            dimensions,
            vendor,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().minusSeconds(60),
            nutritionValue,
            FoodTypes.Types.DAIRY);
    assertTrue(milk.isExpired());
  }
}
