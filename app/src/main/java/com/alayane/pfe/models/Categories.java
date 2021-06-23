package com.alayane.pfe.models;

public class Categories  {
    private  int ID;
    private String name;
    private String image;

    public Categories() {
    }

    public Categories(int ID, String name, String image) {
        this.ID = ID;
        this.name = name;
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
