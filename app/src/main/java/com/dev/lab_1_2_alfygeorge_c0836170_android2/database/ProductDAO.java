package com.dev.lab_1_2_alfygeorge_c0836170_android2.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert(onConflict = REPLACE)
    void insert(Products products);

    @Query("SELECT * FROM Products ORDER BY product_id DESC")
    List<Products> getAllProducts();

    @Query("UPDATE Products SET product_name = :product_name, product_description = :product_description,product_price = :product_price,product_latitude = :product_latitude,product_longitude = :product_longitude WHERE product_id = :product_id")
    void update(int product_id,String product_name,String product_description,String product_price,double product_latitude,double product_longitude);

    @Delete
    void delete(Products products);

}
