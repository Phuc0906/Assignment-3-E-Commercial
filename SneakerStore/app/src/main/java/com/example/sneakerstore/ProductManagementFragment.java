package com.example.sneakerstore;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.sneakerstore.adapter.ProductManagementCategoryAdapter;

import java.util.ArrayList;

public class ProductManagementFragment extends Fragment {

    ImageButton addProductBtn;
    RecyclerView productCatView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addProductBtn = view.findViewById(R.id.addProductBtn);
        ProductManagementCategoryAdapter productManagementCategoryAdapter = new ProductManagementCategoryAdapter(getContext());
        productManagementCategoryAdapter.setData(MainActivity.categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        productCatView = view.findViewById(R.id.productManageCat);
        productCatView.setLayoutManager(linearLayoutManager);
        productCatView.setAdapter(productManagementCategoryAdapter);

        System.out.println("ID: " + MainActivity.categoriesHashMap.get("He"));



        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productFormIntent = new Intent(getContext(), ProductFormActivity.class);
                productFormIntent.putExtra("status", 1); // status == 1 => add product, status == 0 => update product
                startActivityForResult(productFormIntent, 200);
                productManagementCategoryAdapter.notifyDataSetChanged();
            }
        });
    }
}