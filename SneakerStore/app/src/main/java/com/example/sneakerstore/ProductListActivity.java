package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sneakerstore.adapter.ProductListAdapter;
import com.example.sneakerstore.model.ProductExplore;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    TextView categoryLayoutName;
    RecyclerView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        categoryLayoutName = findViewById(R.id.categoryLayoutView);
        itemListView = findViewById(R.id.productList);

        categoryLayoutName.setText("Shoes");

        ProductListAdapter productListAdapter = new ProductListAdapter(this);
        productListAdapter.setData(getProductList());
        itemListView.setLayoutManager(gridLayoutManager);
        productListAdapter.notifyDataSetChanged();
        itemListView.setAdapter(productListAdapter);
    }

    private List<ProductExplore> getProductList() {
        List<ProductExplore> list = new ArrayList<>();
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));        
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        return list;
    }
}