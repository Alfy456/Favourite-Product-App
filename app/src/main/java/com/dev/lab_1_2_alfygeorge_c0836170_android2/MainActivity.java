package com.dev.lab_1_2_alfygeorge_c0836170_android2;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.adapter.ProductsAdapter;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.database.RoomDB;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityMainBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.listener.ProductsClickListener;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.utils.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Products> productsList = new ArrayList<>();
    RoomDB database;
    Products products;
    ProductsAdapter adapter;
    Products selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize database
        database = RoomDB.getInstance(this);
        try {
            products =  (Products) getIntent().getSerializableExtra("products_update");
        database.mainDAO().update(products.getProduct_id(),products.getProduct_name(),products.getProduct_description(),products.getProduct_price(),products.getProduct_latitude(),products.getProduct_longitude());
            productsList.clear();
            productsList.addAll(database.mainDAO().getAllProducts());
        adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
           // if(!SharedPrefHelper.getInstance(getApplicationContext()).getBolIsSaved()) {
                products = (Products) getIntent().getSerializableExtra("products");
                database.mainDAO().insert(products);
                productsList.clear();
                productsList.addAll(database.mainDAO().getAllProducts());
                adapter.notifyDataSetChanged();
                //SharedPrefHelper.getInstance(getApplicationContext()).setBolIsSaved(false);
           // }
        }catch (Exception e){
            e.printStackTrace();
        }
        productsList = database.mainDAO().getAllProducts();
        binding.txtPdtNo.setText("No. of Products :"+productsList.size());
        updateRecycler(productsList);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddProductActivity.class);
                startActivityForResult(intent,103);

            }
        });

        binding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Products> filterList = new ArrayList<>();
        for (Products singleProduct :
                productsList) {
            if (singleProduct.getProduct_name().toLowerCase().contains(newText.toLowerCase())
                    || singleProduct.getProduct_description().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(singleProduct);
            }
        }
        adapter.filterList(filterList);
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

      if(!SharedPrefHelper.getInstance(getApplicationContext()).getBolIsSaved()) {
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("products", products);
            startActivityForResult(intent, 101);
       }
    }

    @Override
    public void onClickDelete(Products products, ImageView imageView) {
        selectedNote = new Products();
        selectedNote = products;
        database.mainDAO().delete(selectedNote);
        productsList.remove(selectedNote);
        adapter.notifyDataSetChanged();

        Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickEdit(Products products, ImageView imageView) {
        Intent intent = new Intent(MainActivity.this,UpdateProductActivity.class);
        intent.putExtra("products_update",products);
        startActivity(intent);
    }
};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==103){
            if (resultCode == Activity.RESULT_OK){
                Products new_products = (Products) data.getSerializableExtra("products");
                database.mainDAO().insert(new_products);
                productsList.clear();
                productsList.addAll(database.mainDAO().getAllProducts());
                adapter.notifyDataSetChanged();
            }
        }

    }
}