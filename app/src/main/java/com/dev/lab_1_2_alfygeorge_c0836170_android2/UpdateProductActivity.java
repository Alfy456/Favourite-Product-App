package com.dev.lab_1_2_alfygeorge_c0836170_android2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.database.RoomDB;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityProductDetailBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityUpdateProductBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductActivity extends AppCompatActivity {
    ActivityUpdateProductBinding binding;
    Products products;
    private GoogleMap mMap;
    List<Products> productsList = new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new Products();
        try {
            products = (Products) getIntent().getSerializableExtra("products_update");
            binding.edtTitle.setText(products.getProduct_name());
            binding.edtDesc.setText(products.getProduct_description());
            binding.edtPrice.setText(products.getProduct_price());
            binding.edtLat.setText(String.valueOf(products.getProduct_latitude()));
            binding.edtLng.setText(String.valueOf(products.getProduct_longitude()));

        }catch (Exception e){
            e.printStackTrace();
        }

        binding.btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtTitle.getText().toString();
                String desc = binding.edtDesc.getText().toString();
                String price = binding.edtPrice.getText().toString();
                String lat = binding.edtLat.getText().toString();
                String lng = binding.edtLng.getText().toString();

                if (name.isEmpty()){
                    binding.edtTitle.setError("Field should not be empty");
                    return;
                }else if(desc.isEmpty()){
                    binding.edtDesc.setError("Field should not be empty");
                    return;
                }else if(price.isEmpty()){
                    binding.edtPrice.setError("Field should not be empty");
                    return;
                }else if(lat.isEmpty()){
                    binding.edtPrice.setError("Field should not be empty");
                    return;
                }else if(lng.isEmpty()){
                    binding.edtPrice.setError("Field should not be empty");
                    return;
                }


                products.setProduct_name(name);
                products.setProduct_price(price);
                products.setProduct_description(desc);
                products.setProduct_latitude(Double.parseDouble(lat));
                products.setProduct_longitude(Double.parseDouble(lng));

                Intent intent = new Intent(UpdateProductActivity.this,MainActivity.class);
                intent.putExtra("products",products);
                setResult(Activity.RESULT_OK,intent);
                startActivity(intent);
            }
        });

    }
}