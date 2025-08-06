package service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.Product;

public class Inventory {
  private long totalQuantityProducts = 0;
  private final Map<UUID, Product<?>> products = new HashMap<>();

  public Inventory() {
    // Default constructor
  }

  public void addProduct(UUID identifier, Product<?> product) {
    this.products.put(identifier, product);
    this.totalQuantityProducts += product.getQuantity();
  }

  public void removeProduct(UUID identifier, Product<?> product) {
    this.products.remove(identifier);
    this.totalQuantityProducts -= product.getQuantity();
  }

  public Product<?> getProduct(UUID identifier) {
    return this.products.get(identifier);
  }

  public long getTotalQuantityProducts() {
    return this.totalQuantityProducts;
  }
}
