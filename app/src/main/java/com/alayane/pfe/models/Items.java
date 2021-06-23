package com.alayane.pfe.models;

public class Items {
    private String Image;
    private String Name;
    private String Price;
    private String Qte;
    private String Des;
    private int Id;

    public Items(int id, String image, String name, String price, String qte, String des) {
        Id=id;
        Image = image;
        Name = name;
        Price = price;
        Qte = qte;
        Des = des;
    }

    public int getId() {
        return Id;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getQte() {
        return Qte;
    }

    public String getDes() {
        return Des;
    }
}
