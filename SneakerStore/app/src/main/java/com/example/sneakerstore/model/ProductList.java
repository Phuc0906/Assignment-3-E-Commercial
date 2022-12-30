package com.example.sneakerstore.model;

public class ProductList {
    private String item1Name, item2Name;
    private String item1Price, item2Price;
    private int item1Img, item2Img;

    public ProductList(String item1Name, String item2Name, String item1Price, String item2Price, int item1Img, int item2Img) {
        this.item1Name = item1Name;
        this.item2Name = item2Name;
        this.item1Price = item1Price;
        this.item2Price = item2Price;
        this.item1Img = item1Img;
        this.item2Img = item2Img;
    }

    public String getItem1Name() {
        return item1Name;
    }

    public String getItem2Name() {
        return item2Name;
    }

    public String getItem1Price() {
        return item1Price;
    }

    public String getItem2Price() {
        return item2Price;
    }

    public int getItem1Img() {
        return item1Img;
    }

    public int getItem2Img() {
        return item2Img;
    }
}
