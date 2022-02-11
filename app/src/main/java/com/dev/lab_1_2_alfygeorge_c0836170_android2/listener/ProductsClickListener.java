package com.dev.lab_1_2_alfygeorge_c0836170_android2.listener;

import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;

public interface ProductsClickListener {
    void onClick(Products products);
    void onClickDelete(Products products, ImageView imageView);
    void onClickEdit(Products products, ImageView imageView);
}
