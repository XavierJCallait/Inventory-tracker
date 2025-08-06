package model;

import java.util.UUID;

public class Product<T> {
  private Long quantity;
  private Double price;
  private String location;
  private final String name;
  private final UUID identifier;
  private final Dimensions dimensions;
  private final Vendor vendor;
  protected T type;

  public record Dimensions(Double length, Double width, Double height) {}

  public Product(
      Long quantity,
      Double price,
      String name,
      String location,
      Dimensions dimensions,
      Vendor vendor,
      T type) {
    this.quantity = quantity;
    this.price = price;
    this.location = location;
    this.name = name;
    this.identifier = UUID.randomUUID();
    this.dimensions = dimensions;
    this.vendor = vendor;
    this.type = type;
  }

  public Long getQuantity() {
    return this.quantity;
  }

  public void changeQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return this.price;
  }

  public void changePrice(Double price) {
    this.price = price;
  }

  public String getLocation() {
    return this.location;
  }

  public void changeLocation(String location) {
    this.location = location;
  }

  public String getName() {
    return this.name;
  }

  public UUID getIdentifier() {
    return this.identifier;
  }

  public Dimensions getDimensions() {
    return this.dimensions;
  }

  public Vendor getVendor() {
    return this.vendor;
  }

  public T getType() {
    return this.type;
  }
}
