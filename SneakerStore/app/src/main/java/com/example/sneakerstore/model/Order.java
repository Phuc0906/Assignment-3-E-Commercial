package com.example.sneakerstore.model;

public class Order {
    private int userId;
    private double totalPrice;
    private int receiveStatus;

    public Order(int userId, double totalPrice, int receiveStatus) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiveStatus = receiveStatus;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }
}
