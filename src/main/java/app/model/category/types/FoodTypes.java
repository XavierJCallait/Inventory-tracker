package app.model.category.types;

public class FoodTypes {
  public enum Types implements ProductTypeEnum {
    FRUIT("Fruit"),
    VEGETABLE("Vegetable"),
    GRAIN("Grain"),
    MEAT("Meat"),
    DAIRY("Dairy"),
    DESSERT("Dessert"),
    SPICES("Spices"),
    CONDIMENTS("Condiments"),
    SAUCES("Sauces"),
    FISH("Fish"),
    SODA("Soda"),
    WATER("Water"),
    ALCOHOL("Alcohol"),
    SNACKS("Snacks");

    private final String dbValue;

    Types(String dbValue) {
      this.dbValue = dbValue;
    }

    @Override
    public String getDbValue() {
      return this.dbValue;
    }

    public static Types fromDbValue(String value) {
      if (value == null || value.isBlank()) {
        throw new IllegalArgumentException("Food type cannot be null or empty.");
      }
      for (Types type : Types.values()) {
        if (type.getDbValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Unknown Food Type: " + value);
    }
  }

  public enum StorageTemperatureTypes {
    FROZEN,
    REFRIGERATED,
    AMBIENT
  }

  public enum PackageTypes {
    BOX,
    BAG,
    CAN,
    JAR,
    BOTTLE,
    CARTON,
    JUG
  }
}
