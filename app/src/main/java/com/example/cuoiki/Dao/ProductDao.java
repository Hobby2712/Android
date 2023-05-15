package com.example.cuoiki.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cuoiki.RoomDatabase.RoomProduct;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<RoomProduct> getAll();

    @Query("DELETE FROM product")
    int deleteAll();

    @Query("SELECT * FROM product WHERE id IN (:productIds)")
    List<RoomProduct> loadAllByIds(int[] productIds);

    @Query("SELECT * FROM product WHERE id  IN (:id)")
    RoomProduct checkProduct(int id);

    @Query("SELECT * FROM product WHERE meal  Like '%' || :name || '%'")
    List<RoomProduct> searchName(String name);

    @Insert
    void insertAll(RoomProduct... products);
    @Update
    void update(RoomProduct... products);
    @Delete
    void delete(RoomProduct products);

}
