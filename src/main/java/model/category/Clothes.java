package model.category;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import model.Product;
import model.Vendor;
import model.category.types.ClothesTypes;
import model.category.types.ClothesTypes.MaterialTypes;
import model.category.types.ClothesTypes.SizeTypes;
import model.category.types.ClothesTypes.Types;

@Entity
@DiscriminatorValue("clothes")
public class Clothes extends Product {
  private String color;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private SizeTypes size;

  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private MaterialTypes material;

  public Clothes() {}

  public Clothes(
      Long quantity,
      Double price,
      String name,
      UUID identifier,
      String location,
      Double weight,
      Dimensions dimensions,
      Vendor vendor,
      String color,
      SizeTypes size,
      MaterialTypes material,
      Types types) {
    super(quantity, price, name, identifier, location, weight, dimensions, vendor);
    this.color = color;
    this.size = size;
    this.material = material;
    this.setClothesType(types);
  }

  public ClothesTypes.Types getClothesType() {
    return ClothesTypes.Types.fromDbValue(super.getType());
  }

  public final void setClothesType(ClothesTypes.Types clothesType) {
    super.setType(clothesType);
  }

  public String getColor() {
    return this.color;
  }

  public SizeTypes getSize() {
    return this.size;
  }

  public MaterialTypes getMaterial() {
    return this.material;
  }
}
