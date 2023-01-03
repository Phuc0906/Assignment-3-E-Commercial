package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductDetailActivity extends AppCompatActivity {
    int productId;

    TextView productName, productDescription, productPrice, productQuantity, productSize;
    ImageView productImg;
    Button addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent productIntent = getIntent();
        productId = Integer.parseInt(productIntent.getStringExtra("id"));

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice=  findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        productQuantity.setText("1");
        productSize = findViewById(R.id.productSize);
        productSize.setText("5.5");

        productImg = findViewById(R.id.productImage);

        addToCartBtn = findViewById(R.id.addToCartBtn);

        DownloadProduct downloadProduct = new DownloadProduct();
        downloadProduct.execute(MainActivity.ROOT_API + "/product/detail?id=" + productId);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadCartProduct uploadCartProduct = new UploadCartProduct();
                uploadCartProduct.execute(MainActivity.ROOT_API + "/product/cart");
            }
        });
    }

    public class UploadCartProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String status = "0";
            URL url = null;
            try {
                url = new URL(urls[0]);

                // Uploading process
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                JSONObject jsonData = new JSONObject();

                // setting data
                jsonData.put("userid", MainActivity.appUser.getUserId());
                jsonData.put("productid", productId);
                jsonData.put("size", Double.parseDouble(productSize.getText().toString()));
                jsonData.put("quantity", Integer.parseInt(productQuantity.getText().toString()));


                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(jsonData.toString());
                status = Integer.toString(connection.getResponseCode());
                os.flush();
                os.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Log.i("INFO", status);
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    public class DownloadProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                System.out.println("IN");
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                System.out.println(result);

            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject productObj = new JSONObject(s);
                productName.setText(productObj.getString("Name"));
                productDescription.setText(productObj.getString("Description"));
                productPrice.setText(Integer.toString(productObj.getInt("Price")));
                Glide.with(ProductDetailActivity.this).load(MainActivity.ROOT_IMG + productObj.getString("PICTURE")).into(productImg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}