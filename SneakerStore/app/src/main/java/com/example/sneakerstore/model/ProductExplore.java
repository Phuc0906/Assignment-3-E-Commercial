package com.example.sneakerstore.model;

public class ProductExplore {
    private String brandName;
    private String shoesName;
    private int resourceImage;

    public ProductExplore(String brandName, String shoesName, int resourceImage) {
        this.brandName = brandName;
        this.shoesName = shoesName;
        this.resourceImage = resourceImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShoesName() {
        return shoesName;
    }

    public void setShoesName(String shoesName) {
        this.shoesName = shoesName;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }
}
