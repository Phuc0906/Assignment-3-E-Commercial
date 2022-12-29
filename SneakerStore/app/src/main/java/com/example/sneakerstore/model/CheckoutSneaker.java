package com.example.sneakerstore.model;

import com.example.sneakerstore.sneaker.Sneaker;

public class CheckoutSneaker extends Sneaker {
    private double price;
    private int quantity;
    private double size;

    public CheckoutSneaker(String resourceImage, String brand, String name, double price, int quantity, double size) {
        super(resourceImage, brand, name);
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSize() {
        return size;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
