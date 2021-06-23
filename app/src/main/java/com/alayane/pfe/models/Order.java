package com.alayane.pfe.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    @ColumnInfo
    public int qte;
    @ColumnInfo
    public String Image;
    @ColumnInfo
    public String Name;
    @ColumnInfo
    public String Price;
    @ColumnInfo
    public int itemId;
    @ColumnInfo
    public int CustomerId;
    @ColumnInfo
    public int TableId;


    public Order() {
    }

    public Order(int qte, String image, String name, String price, int itemId, int customerId, int tableId) {
        this.qte = qte;
        Image = image;
        Name = name;
        Price = price;
        this.itemId = itemId;
        CustomerId = customerId;
        TableId = tableId;
    }
}
