package com.example.sneakerstore.sneaker;

public class Sneaker {
    private int resourceImage;
    private String brand;
    private String name;

    public Sneaker(int resourceImage, String brand, String name) {
        this.resourceImage = resourceImage;
        this.brand = brand;
        this.name = name;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
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
