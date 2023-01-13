package com.example.sneakerstore.model;

import com.example.sneakerstore.sneaker.CartSneaker;

import java.util.List;

public class CheckOutSection {
    private String sectionName;
    private List<CartSneaker> checkoutSneakerList;

    public CheckOutSection(String sectionName, List<CartSneaker> checkoutSneakerList) {
        this.sectionName = sectionName;
        this.checkoutSneakerList = checkoutSneakerList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public List<CartSneaker> getCheckoutSneakerList() {
        return checkoutSneakerList;
    }
}
