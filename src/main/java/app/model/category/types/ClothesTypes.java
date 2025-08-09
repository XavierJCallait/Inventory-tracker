package app.model.category.types;

public class ClothesTypes {
  public enum Types implements ProductTypeEnum {
    T_SHIRT("T_Shirt"),
    BUTTONED_SHIRT("Buttoned_Shirt"),
    PANTS("Pants"),
    DRESS("Dress"),
    JACKET("Jacket"),
    SWEATSHIRT("Sweatshirt"),
    SKIRT("Skirt"),
    SHORTS("Shorts"),
    SWEATER("Sweater"),
    COAT("Coat"),
    SHOES("Shoes"),
    HAT("Hat"),
    SCARF("Scarf"),
    GLOVES("Gloves"),
    BELT("Belt"),
    SOCKS("Socks"),
    SWIMWEAR("Swimwear"),
    UNDERWEAR("Underwear"),
    PAJAMAS("Pajamas"),
    JEWELRY("Jewelry"),
    SUNGLASSES("Sunglasses"),
    WATCH("Watch"),
    BOOTS("Boots"),
    SANDALS("Sandals");

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
        throw new IllegalArgumentException("Clothes type cannot be null or empty.");
      }
      for (Types type : Types.values()) {
        if (type.getDbValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Unknown Clothes Type: " + value);
    }
  }

  public enum MaterialTypes {
    COTTON,
    WOOL,
    POLYESTER,
    LEATHER,
    SILK,
    LINEN,
    VELVET,
    DENIM,
    CORDUROY,
    SATIN,
    FLEECE,
    CASHMERE,
    SPANDEX,
    FUR
  }

  public enum SizeTypes {
    XSMALL,
    SMALL,
    MEDIUM,
    LARGE,
    XLARGE,
    XXLARGE
  }
}
