package com.example.sneakerstore.model;

public class ProductSizeQuantity {
    private double sizes;
    private int quantity;

    public ProductSizeQuantity(double sizes, int quantity) {
        this.sizes = sizes;
        this.quantity = quantity;
    }

    public ProductSizeQuantity(double sizes) {
        this.sizes = sizes;
        this.quantity = 10;
    }

    public double getSizes() {
        return sizes;
    }

    public void setSizes(double sizes) {
        this.sizes = sizes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
