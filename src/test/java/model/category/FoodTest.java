package model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import model.Product;
import model.Vendor;
import model.category.types.FoodTypes;
import org.junit.jupiter.api.Test;

class FoodTest {
  @Test
  void shouldInitializeFood() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("Dairy Farm");
    Food.NutritionValue nutritionValue = new Food.NutritionValue(150, 250.0, 8.0, 30.0, 5.0);
    Food food =
        new Food(
            50L,
            5.00,
            "Location",
            "Milk",
            dimensions,
            vendor,
            FoodTypes.Types.DAIRY,
            false,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue);
    assertNotNull(food);
    assertEquals(50L, food.getQuantity());
    assertEquals(5.00, food.getPrice());
    assertEquals("Milk", food.getName());
    assertEquals("Location", food.getLocation());
    assertEquals(dimensions, food.getDimensions());
    assertEquals(vendor, food.getVendor());
    assertNotNull(food.getIdentifier());
    assertEquals(FoodTypes.Types.DAIRY, food.getType());
    assertEquals(true, food.getIsOrganic());
    assertEquals(FoodTypes.StorageTemperatureTypes.REFRIGERATED, food.getStorageTemperatureType());
    assertEquals(FoodTypes.PackageTypes.JUG, food.getPackageType());
    assertNotNull(food.getExpirationDate());
    assertNotNull(food.getPackagedDate());
    assertEquals(nutritionValue, food.getNutritionValue());
  }

  @Test
  void shouldUpdateFood() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("Dairy Farm");
    Food.NutritionValue nutritionValue = new Food.NutritionValue(150, 250.0, 8.0, 30.0, 5.0);
    Food food =
        new Food(
            50L,
            5.00,
            "Location",
            "Milk",
            dimensions,
            vendor,
            FoodTypes.Types.DAIRY,
            false,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().plusSeconds(604800),
            nutritionValue);
    food.changePrice(4.50);
    food.changeQuantity(60L);
    food.changeLocation("New Location");
    assertEquals(4.50, food.getPrice());
    assertEquals(60L, food.getQuantity());
    assertEquals("New Location", food.getLocation());
  }

  @Test
  void shouldBeExpired() {
    Product.Dimensions dimensions = new Product.Dimensions(10.0, 5.0, 2.0);
    Vendor vendor = new Vendor("Dairy Farm");
    Food.NutritionValue nutritionValue = new Food.NutritionValue(150, 250.0, 8.0, 30.0, 5.0);
    Food food =
        new Food(
            50L,
            5.00,
            "Location",
            "Milk",
            dimensions,
            vendor,
            FoodTypes.Types.DAIRY,
            false,
            true,
            FoodTypes.StorageTemperatureTypes.REFRIGERATED,
            FoodTypes.PackageTypes.JUG,
            Instant.now().minusSeconds(604800),
            nutritionValue);
    assertEquals(true, food.isExpired());
  }
}
