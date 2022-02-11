package com.dev.lab_1_2_alfygeorge_c0836170_android2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.databinding.ActivityAddProductBinding;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;

public class AddProductActivity extends AppCompatActivity {
    ActivityAddProductBinding binding;
    Products products;
    boolean isOldProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCreateTask.setOnClickListener(new View.OnClickListener() {
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

                products = new Products();
                products.setProduct_name(name);
                products.setProduct_price(price);
                products.setProduct_description(desc);
                products.setProduct_latitude(Double.parseDouble(lat));
                products.setProduct_longitude(Double.parseDouble(lng));

                Intent intent = new Intent(AddProductActivity.this,MainActivity.class);
                intent.putExtra("products",products);
                setResult(Activity.RESULT_OK,intent);
                startActivity(intent);


            }
        });
    }
}