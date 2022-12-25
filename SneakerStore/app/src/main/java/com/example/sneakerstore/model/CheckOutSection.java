package com.example.sneakerstore.model;

import java.util.List;

public class CheckOutSection {
    private String sectionName;
    private List<CheckoutSneaker> checkoutSneakerList;

    public CheckOutSection(String sectionName, List<CheckoutSneaker> checkoutSneakerList) {
        this.sectionName = sectionName;
        this.checkoutSneakerList = checkoutSneakerList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public List<CheckoutSneaker> getCheckoutSneakerList() {
        return checkoutSneakerList;
    }
}
