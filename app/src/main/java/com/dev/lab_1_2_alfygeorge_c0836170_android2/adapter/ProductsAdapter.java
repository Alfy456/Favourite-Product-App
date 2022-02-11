package com.dev.lab_1_2_alfygeorge_c0836170_android2.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.lab_1_2_alfygeorge_c0836170_android2.R;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.listener.ProductsClickListener;
import com.dev.lab_1_2_alfygeorge_c0836170_android2.model.Products;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    Context context;
    List<Products> productsList;
    ProductsClickListener listener;

    public ProductsAdapter(Context context, List<Products> productsList, ProductsClickListener listener) {
        this.context = context;
        this.productsList = productsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_products_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {

        holder.txt_pdt_name.setText(productsList.get(position).getProduct_name());
        holder.card_pdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(productsList.get(holder.getAdapterPosition()));
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickDelete(productsList.get(holder.getAdapterPosition()),holder.img_delete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void filterList(List<Products> filteredNotesList){
        productsList = filteredNotesList;
        notifyDataSetChanged();
    }
}

class ProductsViewHolder extends RecyclerView.ViewHolder{

    CardView card_pdt;
    TextView txt_pdt_name;
    ImageView img_delete;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        card_pdt = itemView.findViewById(R.id.card_pdt);
        txt_pdt_name = itemView.findViewById(R.id.txt_pdt_name);
        img_delete = itemView.findViewById(R.id.img_delete);
    }
}