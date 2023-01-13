package com.example.sneakerstore.model;

import android.os.AsyncTask;

import com.example.sneakerstore.MainActivity;

import java.io.IOException;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String brand;
    private String picture;
    private int isWish;

    public Product(int id, String name, String description, double price, String category, String brand, String picture, int isWish) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.picture = MainActivity.ROOT_IMG + picture;
        this.isWish = isWish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getIsWish() {
        return isWish;
    }

    public void setIsWish(int isWish) {
        if (isWish == 1) {
            new postWishlist().execute();
        }else {
            new deleteWishlist().execute();
        }
        this.isWish = isWish;
    }

    public class postWishlist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                return HttpHandler.postMethod(MainActivity.ROOT_API + "/product/wishlist?userid=1&productid=" + id, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    public class deleteWishlist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                return HttpHandler.deleteMethod(MainActivity.ROOT_API + "/product/wishlist?userid=1&productid=" + id, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
