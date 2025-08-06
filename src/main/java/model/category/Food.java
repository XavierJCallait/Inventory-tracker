package model.category;

import java.time.Instant;
import model.Product;
import model.Vendor;
import model.category.types.FoodTypes;
import model.category.types.FoodTypes.PackageTypes;
import model.category.types.FoodTypes.StorageTemperatureTypes;

public class Food extends Product<FoodTypes.Types> {
  private final Boolean isOrganic;
  private final StorageTemperatureTypes storageTemperatureType;
  private final PackageTypes packageType;
  private final Instant expirationDate;
  private final Instant packagedDate = Instant.now();
  private final NutritionValue nutritionValue;

  public record NutritionValue(
      Integer calories,
      Double servingSizeGrams,
      Double proteinGrams,
      Double carbsGrams,
      Double fatsGrams) {}

  public Food(
      Long quantity,
      Double price,
      String location,
      String name,
      Dimensions dimensions,
      Vendor vendor,
      FoodTypes.Types type,
      Boolean isExpired,
      Boolean isOrganic,
      StorageTemperatureTypes storageTemperatureType,
      PackageTypes packageType,
      Instant expirationDate,
      NutritionValue nutritionValue) {
    super(quantity, price, name, location, dimensions, vendor, type);
    this.isOrganic = isOrganic;
    this.storageTemperatureType = storageTemperatureType;
    this.packageType = packageType;
    this.expirationDate = expirationDate;
    this.nutritionValue = nutritionValue;
  }

  public Boolean getIsOrganic() {
    return this.isOrganic;
  }

  public StorageTemperatureTypes getStorageTemperatureType() {
    return this.storageTemperatureType;
  }

  public PackageTypes getPackageType() {
    return this.packageType;
  }

  public Instant getExpirationDate() {
    return this.expirationDate;
  }

  public Instant getPackagedDate() {
    return this.packagedDate;
  }

  public NutritionValue getNutritionValue() {
    return this.nutritionValue;
  }

  public Boolean isExpired() {
    return Instant.now().isAfter(this.expirationDate);
  }
}
