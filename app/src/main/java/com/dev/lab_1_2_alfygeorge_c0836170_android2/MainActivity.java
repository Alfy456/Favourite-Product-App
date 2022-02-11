package com.dev.lab_1_2_alfygeorge_c0836170_android2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.adapter.ProductsAdapter;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.database.RoomDB;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityMainBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.listener.ProductsClickListener;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Products> productsList = new ArrayList<>();
    RoomDB database;
    Products products;
    ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize database
        database = RoomDB.getInstance(this);

        productsList.clear();
        productsList = database.mainDAO().getAllProducts();
        updateRecycler(productsList);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }

    private void updateRecycler(List<Products> productsList) {
        binding.recyclerHome.setHasFixedSize(true);
        binding.recyclerHome.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductsAdapter(MainActivity.this, productsList,productsClickListener);
        binding.recyclerHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

private  final ProductsClickListener productsClickListener = new ProductsClickListener() {
    @Override
    public void onClick(Products products) {

        Intent intent = new Intent(MainActivity.this,ProductDetailActivity.class);
        intent.putExtra("products",products);
        startActivityForResult(intent,101);

    }
        };

}