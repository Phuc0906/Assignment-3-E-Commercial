package com.example.sneakerstore.sneaker;

import java.util.List;

public class Category {
    private String cateName;
    private List<Sneaker> list;

    public Category(String cateName, List<Sneaker> list) {
        this.cateName = cateName;
        this.list = list;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Sneaker> getList() {
        return list;
    }

    public void setList(List<Sneaker> list) {
        this.list = list;
    }
}
