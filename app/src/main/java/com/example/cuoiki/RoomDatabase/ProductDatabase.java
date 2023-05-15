package com.example.cuoiki.RoomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cuoiki.Dao.ProductDao;

@Database(entities = {RoomProduct.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "cart.db";

    private static ProductDatabase instance;

    public static synchronized ProductDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            ,ProductDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract ProductDao productDao();

}
