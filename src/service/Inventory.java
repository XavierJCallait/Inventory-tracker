package service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.Product;

public class Inventory {
    private static long totalQuantityProducts = 0;
    private Map<UUID, Product> products = new HashMap<>();

    public Inventory() {
        this.products = new HashMap<>();
    }

    public void addProduct(UUID identifier, Product product) {
        this.products.put(identifier, product);
        Inventory.totalQuantityProducts += product.getQuantity();
    }

    public void removeProduct(UUID identifier, Product product) {
        this.products.remove(identifier);
        Inventory.totalQuantityProducts -= product.getQuantity();
    }

    public Product getProduct(UUID identifier) {
        return this.products.get(identifier);
    }

    public static long getTotalQuantityProducts() {
        return Inventory.totalQuantityProducts;
    } 
}