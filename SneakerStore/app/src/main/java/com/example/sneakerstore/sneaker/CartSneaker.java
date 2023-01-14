package com.example.sneakerstore.sneaker;

import java.io.Serializable;

public class CartSneaker extends Sneaker implements Serializable{
    private int quantity;
    private double price;
    private double size;

    public CartSneaker(int sneakerId, String resourceImage, String brand, String name, int quantity, double price, double size) {
        super(sneakerId, resourceImage, brand, name);
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    public double getSize() { return this.size; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {return this.quantity;}

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return getSneakerId() + "," + getResourceImage() + ","
                + getBrand() + "," + getName() + "," + this.price + ","
                + this.quantity+ "," + this.size;
    }
}
