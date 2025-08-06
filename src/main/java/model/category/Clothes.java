package model.category;

import model.Product;
import model.Vendor;
import model.category.types.ClothesTypes;
import model.category.types.ClothesTypes.MaterialTypes;
import model.category.types.ClothesTypes.SizeTypes;

public class Clothes extends Product<ClothesTypes.Types> {
  private final String color;
  private final SizeTypes size;
  private final MaterialTypes material;

  public Clothes(
      Long quantity,
      Double price,
      String location,
      String name,
      Dimensions dimensions,
      Vendor vendor,
      ClothesTypes.Types type,
      String color,
      SizeTypes size,
      MaterialTypes material) {
    super(quantity, price, location, name, dimensions, vendor, type);
    this.color = color;
    this.size = size;
    this.material = material;
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
