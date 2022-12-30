package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.ProductListAdapter;
import com.example.sneakerstore.model.ProductList;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    TextView categoryLayoutName;
    RecyclerView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryLayoutName = findViewById(R.id.categoryLayoutView);
        itemListView = findViewById(R.id.productList);

        categoryLayoutName.setText("Shoes");

        List<ProductList> productLists = new ArrayList<>();
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        productLists.add(new ProductList("Nike 1", "Nike 2", "1200000", "3200000", R.drawable.air_max, R.drawable.air_max));
        ProductListAdapter productListAdapter = new ProductListAdapter(this);
        productListAdapter.setData(productLists);
        itemListView.setLayoutManager(linearLayoutManager);
        productListAdapter.notifyDataSetChanged();
        itemListView.setAdapter(productListAdapter);

    }
}