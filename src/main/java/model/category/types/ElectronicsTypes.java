package model.category.types;

public class ElectronicsTypes {
  public enum Types {
    SMARTPHONE,
    LAPTOP,
    TABLET,
    TELEVISION,
    HEADPHONES,
    SPEAKER
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
