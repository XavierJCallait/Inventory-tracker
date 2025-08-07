package model.category;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Instant;
import java.util.UUID;
import model.Product;
import model.Vendor;
import model.category.types.FoodTypes;
import model.category.types.FoodTypes.PackageTypes;
import model.category.types.FoodTypes.StorageTemperatureTypes;
import model.category.types.FoodTypes.Types;

@Entity
@DiscriminatorValue("food")
public class Food extends Product {
  private Boolean isOrganic;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private StorageTemperatureTypes storageTemperatureType;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private PackageTypes packageType;

  private Instant expirationDate;
  private Instant packagedDate = Instant.now();

  @Embedded private NutritionValue nutritionValue;

  @Embeddable
  public static record NutritionValue(
      Integer calories,
      Double servingSizeGrams,
      Double proteinGrams,
      Double carbsGrams,
      Double fatsGrams) {}

  public Food() {}

  public Food(
      Long quantity,
      Double price,
      String name,
      UUID identifier,
      String location,
      Dimensions dimensions,
      Vendor vendor,
      Boolean isOrganic,
      StorageTemperatureTypes storageTemperatureType,
      PackageTypes packageType,
      Instant expirationDate,
      NutritionValue nutritionValue,
      Types type) {
    super(quantity, price, name, identifier, location, dimensions, vendor);
    this.isOrganic = isOrganic;
    this.storageTemperatureType = storageTemperatureType;
    this.packageType = packageType;
    this.expirationDate = expirationDate;
    this.nutritionValue = nutritionValue;
    this.setFoodType(type);
  }

  public FoodTypes.Types getFoodType() {
    return FoodTypes.Types.fromDbValue(super.getType());
  }

  public final void setFoodType(FoodTypes.Types foodType) {
    super.setType(foodType);
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
