package app.model.category.types;

public class ElectronicsTypes {
  public enum Types implements ProductTypeEnum {
    SMARTPHONE("Smartphone"),
    LAPTOP("Laptop"),
    TABLET("Tablet"),
    TELEVISION("Television"),
    HEADPHONES("Headphones"),
    SPEAKER("Speaker");

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
        throw new IllegalArgumentException("Electronics type cannot be null or empty.");
      }
      for (Types type : Types.values()) {
        if (type.getDbValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Unknown Electronics Type: " + value);
    }
  }

  public enum ConnectivityTypes {
    WIRED,
    WIRELESS,
    BOTH
  }

  public enum CurrentTypes {
    AC,
    DC,
    BOTH
  }

  public enum PowerSourceTypes {
    REPLACEABLE,
    RECHARGEABLE,
    EXTERNAL
  }
}
