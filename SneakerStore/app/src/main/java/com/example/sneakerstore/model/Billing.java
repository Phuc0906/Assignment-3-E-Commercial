package com.example.sneakerstore.model;

public class Billing {
    private boolean status;
    private int billingNo;
    private String customerName;
    private double billingPrice;
    private String billingAddress;
    private boolean payment; // 1: google pay, 0: credit card

    public Billing(int billingNo, String customerName, double billingPrice, boolean status, String billingAddress, boolean payment) {
        this.billingNo = billingNo;
        this.customerName = customerName;
        this.billingPrice = billingPrice;
        this.status = status;
        this.billingAddress = billingAddress;
        this.payment = payment;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public boolean isPayment() {
        return payment;
    }

    public boolean getStatus() {
        return status;
    }

    public int getBillingNo() {
        return billingNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBillingPrice() {
        return billingPrice;
    }
}
