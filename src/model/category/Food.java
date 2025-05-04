package model.category;

import model.Product;

public class Food extends Product {
    private String type;
    private String expirationDate;
    private String storageTemperature;
    private String nutritionValue;
    private String packageType;
    private Boolean isExpired;
    private Boolean isOrganic;

    public Food(long quantity, String price, String location, String type, String expirationDate, String storageTemperature, String nutritionValue, String packageType, Boolean isExpired, Boolean isOrganic) {
        super(type, price, "Food", location, quantity);
        this.type = type;
        this.expirationDate = expirationDate;
        this.storageTemperature = storageTemperature;
        this.nutritionValue = nutritionValue;
        this.packageType = packageType;
        this.isExpired = isExpired;
        this.isOrganic = isOrganic;
    }

    public String getType() {
        return this.type;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getStorageTemperature() {
        return this.storageTemperature;
    }

    public String getNutritionValue() {
        return this.nutritionValue;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public Boolean getIsExpired() {
        return this.isExpired;
    }

    public Boolean getIsOrganic() {
        return this.isOrganic;
    }
}