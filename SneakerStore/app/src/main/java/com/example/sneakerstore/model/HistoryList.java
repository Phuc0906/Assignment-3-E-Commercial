package com.example.sneakerstore.model;

import com.example.sneakerstore.sneaker.CartSneaker;


import java.util.List;

public class HistoryList {
    private String date;
    private List<CartSneaker> list;

    public HistoryList(String date, List<CartSneaker> list) {
        this.date = date;
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartSneaker> getList() {
        return list;
    }

    public void setList(List<CartSneaker> list) {
        this.list = list;
    }
}
