package com.example.sneakerstore.logo;

public class Logo {
    private int resourceID;
    private String name;

    public Logo(int resourceID) {
        this.resourceID = resourceID;
    }

    public Logo(int resourceID, String name) {
        this.resourceID = resourceID;
        this.name = name;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getName() {
        return name;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }
}
