package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.model.HttpHandler;
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

public class ProductDetailActivity extends AppCompatActivity {
    String productID;
    TextView detailName, detailDes, detailPrice;
    ImageView detailImage;
    RecyclerView detailSize;
    Button addBtn, buyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getProductIntent();

        //init
        detailName = findViewById(R.id.detail_name);
        detailDes = findViewById(R.id.detail_description);
        detailPrice = findViewById(R.id.detail_price);
        detailImage = findViewById(R.id.detail_image);
        detailSize = findViewById(R.id.detail_size_list);
        addBtn = findViewById(R.id.add_button);
        buyBtn = findViewById(R.id.buy_button);

        new readJSON().execute();

    }

    private void getProductIntent() {
        Intent intent = getIntent();
        productID = intent.getStringExtra("product_id");
    }

    public class readJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod("http://localhost:3000/product/detail?id=" + productID);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    System.out.println(object);
                    detailName.setText(object.getString("Name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public class UploadCartProduct extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String status = "0";
//            URL url = null;
//            try {
//                url = new URL(urls[0]);
//
//                // Uploading process
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                connection.setRequestProperty("Accept", "application/json");
//                connection.setDoInput(true);
//                connection.setDoOutput(true);
//                JSONObject jsonData = new JSONObject();
//
//                // setting data
//                jsonData.put("userid", MainActivity.appUser.getUserId());
//                jsonData.put("productid", productId);
//                jsonData.put("size", productSize.getText().toString());
//                jsonData.put("quantity", Integer.parseInt(productQuantity.getText().toString()));
//
//                System.out.println(jsonData);
//
//                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
//                os.writeBytes(jsonData.toString());
//                status = Integer.toString(connection.getResponseCode());
//                os.flush();
//                os.close();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            Log.i("INFO", status);
//            return status;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//        }
//    }
//

}