package model.category;

import model.Product;

public class Electronics extends Product {
  private String size;
  private String type;
  private String power;

  public Electronics(
      long quantity, Double price, String location, String size, String type, String power) {
    super(size + type + power, price, "Electronics", location, quantity);
    this.size = size;
    this.type = type;
    this.power = power;
  }

  public String getSize() {
    return this.size;
  }

  public String getType() {
    return this.type;
  }

  public String getPower() {
    return this.power;
  }
}
