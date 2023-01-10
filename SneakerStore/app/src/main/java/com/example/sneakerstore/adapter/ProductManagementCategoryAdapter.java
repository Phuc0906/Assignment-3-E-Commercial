package com.example.sneakerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductManagementCategoryAdapter extends RecyclerView.Adapter<ProductManagementCategoryAdapter.ProductHolder> {

    Context context;
    List<String> cats;


    public ProductManagementCategoryAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<String> cats) {
        this.cats = cats;
        cats.add("Latest");
    }

    @NonNull
    @Override
    public ProductManagementCategoryAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_management_cat_item, parent, false);
        return new ProductManagementCategoryAdapter.ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManagementCategoryAdapter.ProductHolder holder, int position) {
        String category = cats.get(position);
        if (category != null) {
            holder.catName.setText(category);

            holder.catName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView catName;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.categoryManagementName);
        }
    }
}
