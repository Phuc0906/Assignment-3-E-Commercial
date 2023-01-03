package com.example.sneakerstore.sneaker;

public class CartSneaker extends Sneaker {
    private int quantity;
    private int price;

    public CartSneaker(int sneakerId, String resourceImage, String brand, String name, int quantity, int price) {
        super(sneakerId, resourceImage, brand, name);
        this.quantity = quantity;
        this.price = price;
    }

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
