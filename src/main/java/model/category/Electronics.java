package model.category;

import model.Product;
import model.Vendor;
import model.category.types.ElectronicsTypes;
import model.category.types.ElectronicsTypes.ConnectivityTypes;
import model.category.types.ElectronicsTypes.CurrentTypes;
import model.category.types.ElectronicsTypes.PowerSourceTypes;

public class Electronics extends Product<ElectronicsTypes.Types> {
  private final Double power;
  private final Double current;
  private final Double voltage;
  private final ConnectivityTypes connectivityType;
  private final PowerSourceTypes powerSourceType;
  private final CurrentTypes currentType;

  public Electronics(
      Long quantity,
      Double price,
      String location,
      String name,
      Dimensions dimensions,
      Vendor vendor,
      ElectronicsTypes.Types type,
      Double power,
      Double current,
      Double voltage,
      ConnectivityTypes connectivityType,
      PowerSourceTypes powerSourceType,
      CurrentTypes currentType) {
    super(quantity, price, location, name, dimensions, vendor, type);
    this.power = power;
    this.current = current;
    this.voltage = voltage;
    this.connectivityType = connectivityType;
    this.powerSourceType = powerSourceType;
    this.currentType = currentType;
  }

  public Double getPower() {
    return this.power;
  }

  public Double getCurrent() {
    return this.current;
  }

  public Double getVoltage() {
    return this.voltage;
  }

  public ConnectivityTypes getConnectivityType() {
    return this.connectivityType;
  }

  public PowerSourceTypes getPowerSourceType() {
    return this.powerSourceType;
  }

  public CurrentTypes getCurrentType() {
    return this.currentType;
  }
}
