package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.adapter.SizeAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.SneakerSize;
import com.example.sneakerstore.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    String productID;
    TextView detailName, detailDes, detailPrice;
    ImageView detailImage;
    RecyclerView detailSize;
    Button addBtn, buyBtn;

    String url;
    SizeAdapter adapter;

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
        addBtn = findViewById(R.id.add_button);
        buyBtn = findViewById(R.id.buy_button);
        sizeList = new ArrayList<>();
        adapter = new SizeAdapter(this);




        //set up for recyclerView
        adapter.setAdapter(sizeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailSize.setLayoutManager(linearLayoutManager);
        detailSize.setAdapter(adapter);

        new readJSON().execute();

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
                    String pic = MainActivity.ROOT_IMG + object.getString("Picture");

                    Glide.with(ProductDetailActivity.this).load(pic).into(detailImage);
                    String[] sizeArr = object.getString("Quantity").split(",");

                    for (double i = 5; i < 10; i += 0.5) {
                        int index = (int) (i - 5) * 2;
                        if (Integer.parseInt(sizeArr[index]) > 0) {
                            sizeList.add(new SneakerSize(String.valueOf(i), false));
                        }
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}