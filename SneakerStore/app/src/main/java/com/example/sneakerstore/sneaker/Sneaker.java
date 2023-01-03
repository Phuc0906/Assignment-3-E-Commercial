package com.example.sneakerstore.sneaker;

public class Sneaker {
    private int sneakerId;
    private String resourceImage;
    private String brand;
    private String name;

    public Sneaker(int sneakerId, String resourceImage, String brand, String name) {
        this.sneakerId = sneakerId;
        this.resourceImage = resourceImage;
        this.brand = brand;
        this.name = name;
    }

    public int getSneakerId() {
        return sneakerId;
    }

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
