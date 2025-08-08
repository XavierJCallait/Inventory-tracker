package model.category;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import model.Product;
import model.Vendor;
import model.category.types.ElectronicsTypes;
import model.category.types.ElectronicsTypes.ConnectivityTypes;
import model.category.types.ElectronicsTypes.CurrentTypes;
import model.category.types.ElectronicsTypes.PowerSourceTypes;
import model.category.types.ElectronicsTypes.Types;

@Entity
@DiscriminatorValue("electronics")
public class Electronics extends Product {
  private Double power;
  private Double current;
  private Double voltage;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private ConnectivityTypes connectivityType;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private PowerSourceTypes powerSourceType;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private CurrentTypes currentType;

  public Electronics() {}

  public Electronics(
      Long quantity,
      Double price,
      String name,
      UUID identifier,
      String location,
      Double weight,
      Dimensions dimensions,
      Vendor vendor,
      Double power,
      Double current,
      Double voltage,
      ConnectivityTypes connectivityType,
      PowerSourceTypes powerSourceType,
      CurrentTypes currentType,
      Types type) {
    super(quantity, price, name, identifier, location, weight, dimensions, vendor);
    this.power = power;
    this.current = current;
    this.voltage = voltage;
    this.connectivityType = connectivityType;
    this.powerSourceType = powerSourceType;
    this.currentType = currentType;
    this.setElectronicsType(type);
  }

  public ElectronicsTypes.Types getElectronicsType() {
    return ElectronicsTypes.Types.fromDbValue(super.getType());
  }

  public final void setElectronicsType(ElectronicsTypes.Types electronicsType) {
    super.setType(electronicsType);
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
