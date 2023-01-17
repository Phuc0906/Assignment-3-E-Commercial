package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sneakerstore.adapter.ProductAdapter;
import com.example.sneakerstore.fragment.ExploreFragment;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView productListView;
    ProductAdapter adapter;
    TextView categoryView;
    ImageButton backBtn, loadButton;
    private static List<Product> productList = new ArrayList<>();
    private String APIEndPoint;
    private ArrayList<Integer> wishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        productListView = findViewById(R.id.productList);
        categoryView = findViewById(R.id.categoryLayoutView);
        backBtn = findViewById(R.id.productListBackBtn);
        loadButton = findViewById(R.id.reloadButton);
        wishlist = new ArrayList<>();

        Intent intent = getIntent();
        int role = intent.getIntExtra("role", 0);
        if (intent.getIntExtra("id", 0) == 0) {
            APIEndPoint = "/latest";
        }else if (intent.getIntExtra("id", 0) == -1) {
            APIEndPoint = "/search/brand?brand=" + intent.getIntExtra("brand", 1);
        }else {
            APIEndPoint = "/search/category?category=" + intent.getIntExtra("id", 1);
        }

        categoryView.setText(intent.getStringExtra("cat"));

        adapter = new ProductAdapter(this, role);
        adapter.setData(productList);
        productListView.setLayoutManager(gridLayoutManager);
        productListView.setAdapter(adapter);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new readJSON().execute();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.clear();
                finish();
            }
        });

        if (role == 1) {
            new readJSON().execute();
        }else {
            new getWishlist().execute();
        }
    }

    public class getWishlist extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/product/wishlist?userid=" + MainActivity.user.getId()); // modify id here
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    wishlist.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        wishlist.add(object.getInt("PRODUCT_ID"));
                    }

                    new readJSON().execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public class readJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/product" + APIEndPoint);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONArray jsonArray =new JSONArray(s);
                    System.out.println(jsonArray);
                    productList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int isWish = 0;
                        for (int j = 0; j < wishlist.size(); j++) {
                            if (object.getInt("ID") == wishlist.get(j)) {
                                isWish = 1;
                                break;
                            }
                        }

                        productList.add(new Product(object.getInt("ID"),
                                object.getString("NAME"),
                                object.getString("DESCRIPTION"),
                                object.getDouble("PRICE"),
                                object.getString("category"),
                                object.getString("brand"),
                                object.getString("PICTURE"), isWish));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}