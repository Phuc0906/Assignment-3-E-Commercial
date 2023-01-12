package com.example.sneakerstore.model;

public class Order {
    private int userId;
    private double totalPrice;
    private int receiveStatus;
    private int paymentMethod; // 1: google pay, 2, credit card
    private String shippingAddress;

    public Order(int userId, double totalPrice, int receiveStatus, int paymentMethod, String shippingAddress) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiveStatus = receiveStatus;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public int getPaymentMethod() {
        return paymentMethod;
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
