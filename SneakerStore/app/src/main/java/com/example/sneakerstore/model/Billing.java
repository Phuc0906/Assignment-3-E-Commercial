package com.example.sneakerstore.model;

public class Billing {
    private boolean status;
    private int billingNo;
    private String customerName;
    private int billingPrice;

    public Billing(int billingNo, String customerName, int billingPrice, boolean status) {
        this.billingNo = billingNo;
        this.customerName = customerName;
        this.billingPrice = billingPrice;
        this.status = status;
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

    public int getBillingPrice() {
        return billingPrice;
    }
}
