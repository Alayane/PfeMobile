package com.alayane.pfe;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alayane.pfe.models.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
        public abstract OrderDAO orderDAO();

}
