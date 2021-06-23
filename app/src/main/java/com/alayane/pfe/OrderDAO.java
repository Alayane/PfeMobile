package com.alayane.pfe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alayane.pfe.models.Order;

import java.util.List;

@Dao
public interface OrderDAO {

    @Insert
    void insertOrder(Order order);

    @Query("select count(*) from orders")
    int getBadge();

    @Query("select * from orders")
    List<Order> getAll();


    @Query("delete from orders where Id= :id")
    void deleteOrder(int id);

    @Query("delete from orders")
    void deleteAll();

}
