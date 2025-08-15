package app.model;

import app.model.category.types.ProductTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category")
public class Product {
  @Id
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(length = 36, updatable = false)
  private UUID identifier = UUID.randomUUID();

  @Column(nullable = false, length = 100)
  private String name;

  private Long quantity;
  private Double price;
  private String location;
  private Double weight;

  @Embedded private Dimensions dimensions;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "vendor_id", nullable = false)
  private Vendor vendor;

  @Column(nullable = false, length = 15)
  private String type;

  @Embeddable
  public static record Dimensions(Double length, Double width, Double height) {}

  public Product() {}

  public Product(
      Long quantity,
      Double price,
      String name,
      String location,
      Double weight,
      Dimensions dimensions,
      Vendor vendor) {
    this.quantity = quantity;
    this.price = price;
    this.name = name;
    this.location = location;
    this.weight = weight;
    this.dimensions = dimensions;
    this.vendor = vendor;
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

  public String getProductName() {
    return this.name;
  }

  public UUID getProductIdentifier() {
    return this.identifier;
  }

  public Dimensions getDimensions() {
    return this.dimensions;
  }

  public UUID getVendorID() {
    return this.vendor.getVendorIdentifier();
  }

  protected void setType(ProductTypeEnum productType) {
    this.type = productType.getDbValue();
  }

  protected String getType() {
    return this.type;
  }

  public Double getWeight() {
    return this.weight;
  }
}
