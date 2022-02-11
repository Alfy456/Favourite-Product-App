package com.dev.lab_1_2_alfygeorge_c0836170_android2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Products")
public class Products implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int product_id = 0;

    @ColumnInfo(name = "product_name")
    String  product_name = "";

    @ColumnInfo(name = "product_description")
    String  product_description = "";

    @ColumnInfo(name = "product_price")
    String  product_price = "";

    @ColumnInfo(name = "product_latitude")
    double product_latitude = 0.0;

    @ColumnInfo(name = "product_longitude")
    double product_longitude = 0.0;

    public Products() {

    }

    public Products( String product_name, String product_description, String product_price, double product_latitude, double product_longitude) {

        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_latitude = product_latitude;
        this.product_longitude = product_longitude;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public double getProduct_latitude() {
        return product_latitude;
    }

    public void setProduct_latitude(double product_latitude) {
        this.product_latitude = product_latitude;
    }

    public double getProduct_longitude() {
        return product_longitude;
    }

    public void setProduct_longitude(double product_longitude) {
        this.product_longitude = product_longitude;
    }
}
