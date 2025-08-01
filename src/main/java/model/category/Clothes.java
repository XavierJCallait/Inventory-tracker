package model.category;

import model.Product;

public class Clothes extends Product {
    private String size;
    private String type;
    private String color;
    private String material;

    public Clothes(long quantity, Double price, String location, String size, String type, String color, String material) {
        super(size + color + type, price, "Clothes", location, quantity);
        this.size = size;
        this.type = type;
        this.color = color;
        this.material = material;
    }

    public String getSize() {
        return this.size;
    }

    public String getType() {
        return this.type;
    }

    public String getColor() {
        return this.color;
    }

    public String getMaterial() {
        return this.material;
    }
}