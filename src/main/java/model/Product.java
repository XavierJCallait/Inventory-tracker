package model;

import java.util.UUID;

public class Product {
    private String name;
    private String price;
    private String category;
    private String location;
    private long quantity;
    private final UUID identifier;

    public Product(String name, String price, String category, String location, long quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.identifier = UUID.randomUUID();
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public void changeQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return this.price;
    }

    public void changePrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return this.category;
    }

    public void changeCategory(String category) {
        this.category = category;
    }

    public UUID getIdentifier() {
        return this.identifier;
    }

    public String getLocation() {
        return this.location;
    }

    public void changeLocation(String location) {
        this.location = location;
    }
}