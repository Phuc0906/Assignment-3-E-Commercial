package com.example.sneakerstore.sneaker;

public class CartSneaker extends Sneaker {
    private int quantity;
    private int price;
    private double size;

    public CartSneaker(int sneakerId, String resourceImage, String brand, String name, int quantity, int price, double size) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
