package model.clothes;

import model.category.Clothes;

public class Shirt extends Clothes {
    private String sleeveLength;
    private String collarType;
    private String fitType;
    private String patternType;

    public Shirt(long quantity, String price, String location, String size, String type, String color, String material, String sleeveLength, String collarType, String fitType, String patternType) {
        super(quantity, price, location, size, type, color, material);
        this.sleeveLength = sleeveLength;
        this.collarType = collarType;
        this.fitType = fitType;
        this.patternType = patternType;
    }

    public String getSleeveLength() {
        return this.sleeveLength;
    }

    public String getCollarType() {
        return this.collarType;
    }

    public String getFitType() {
        return this.fitType;
    }

    public String getPatternType() {
        return this.patternType;
    }
}