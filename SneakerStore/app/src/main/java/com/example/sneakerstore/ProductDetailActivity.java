package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.adapter.CategoryAdapter;
import com.example.sneakerstore.adapter.SizeAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.SneakerSize;
import com.example.sneakerstore.sneaker.CartSneaker;
import com.example.sneakerstore.sneaker.Category;
import com.example.sneakerstore.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    ScrollView scrollView;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;
    String productID;
    TextView detailName, detailDes, detailPrice;
    ImageView detailImage;
    RecyclerView detailSize, suggestView;
    Button addBtn, buyBtn;
    String url, resourceImage, sizeSelected, brandName;
    SizeAdapter sizeAdapter;
    List<Sneaker> sneakerList;
    List<Category> categoryList;
    CategoryAdapter categoryAdapter;

    private List<SneakerSize> sizeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        url = getProductIntent();

        //init
        detailName = findViewById(R.id.detail_name);
        detailDes = findViewById(R.id.detail_description);
        detailPrice = findViewById(R.id.detail_price);
        detailImage = findViewById(R.id.detail_image);
        detailSize = findViewById(R.id.detail_size_list);
        suggestView = findViewById(R.id.recyclerList);
        addBtn = findViewById(R.id.add_button);
        buyBtn = findViewById(R.id.buy_button);
        sizeList = new ArrayList<>();
        categoryList = new ArrayList<Category>();
        sizeAdapter = new SizeAdapter(this);
        sneakerList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        categoryAdapter = new CategoryAdapter(this);
        scrollView = findViewById(R.id.scrollView3);
        constraintLayout = findViewById(R.id.constraintLayout4);

        //set up for sizeRecyclerView
        sizeAdapter.setAdapter(sizeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailSize.setLayoutManager(linearLayoutManager);
        detailSize.setAdapter(sizeAdapter);
        new readJSON().execute();

        // set up for suggest Recyclerview
        new DownloadLatestProduct().execute();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryList.add(new Category("Relevant Product", sneakerList));
        categoryList.add(new Category("Other Product", sneakerList));
        categoryAdapter.setData(categoryList);
        suggestView.setLayoutManager(linearLayoutManager1);
        suggestView.setAdapter(categoryAdapter);

        //set up event for add button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeSelected = getSizeSelect();
                if (sizeSelected != null) {
                    new writeJSON().execute();
                    Toast.makeText(ProductDetailActivity.this, "Add to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Please select size", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set up event for buy button
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeSelected = getSizeSelect();
                if (sizeSelected != null) {
                    Intent intent = new Intent(ProductDetailActivity.this, OrderActivity.class);

                    intent.putExtra("product_in_cart", new String[] {new CartSneaker(Integer.parseInt(productID),
                            resourceImage,
                            brandName,
                            detailName.getText().toString().trim() ,1,
                            Integer.parseInt(detailPrice.getText().toString().trim()),
                            Double.parseDouble(sizeSelected)).toString()});
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Please select size", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getSizeSelect() {
        for (int i = 0; i < sizeList.size(); i++) {
            if (sizeList.get(i).isSelected()) {
                return sizeList.get(i).getSize();
            }
        }
        return null;
    }

    private String getProductIntent() {
        Intent intent = getIntent();
        productID = intent.getStringExtra("product_id");
        return MainActivity.ROOT_API + "/product/detail?id=" + productID;
    }

    public class readJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    detailName.setText(object.getString("Name"));
                    detailDes.setText(object.getString("Description"));
                    detailPrice.setText(object.getString("Price"));
                    resourceImage = object.getString("Picture");
                    brandName = object.getString("Brand");
                    Glide.with(ProductDetailActivity.this).load(MainActivity.ROOT_IMG + resourceImage).into(detailImage);
                    String[] sizeArr = object.getString("Quantity").split(",");

                    for (double i = 5; i < 10; i += 0.5) {
                        int index = (int) (i - 5) * 2;
                        if (Integer.parseInt(sizeArr[index]) > 0) {
                            sizeList.add(new SneakerSize(String.valueOf(i), false));
                        }
                    }
                    sizeAdapter.notifyDataSetChanged();
                    scrollView.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class writeJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpHandler.postToCart(MainActivity.ROOT_API + "/product/cart", "1", productID, sizeSelected, 1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class DownloadLatestProduct extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/product/latest");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    sneakerList.add(new Sneaker(object.getInt("ID"),
                            MainActivity.ROOT_IMG + object.getString("PICTURE"),
                            object.getString("brand"),
                            object.getString("NAME")));
                }
                categoryAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}