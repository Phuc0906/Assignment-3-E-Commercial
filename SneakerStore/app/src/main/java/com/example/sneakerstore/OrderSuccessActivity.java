package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sneakerstore.model.HttpHandler;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderSuccessActivity extends AppCompatActivity {

    Button backToShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        backToShopping = findViewById(R.id.backToShopping);

        backToShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        UpdateBilling updateBilling = new UpdateBilling();
        updateBilling.execute(MainActivity.ROOT_API + "/product/billing");
    }

    public class UpdateBilling extends AsyncTask<String, Void, String> {

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

                jsonData.put("userid", OrderActivity.userOder.getUserId());
                jsonData.put("totalPrice", OrderActivity.userOder.getTotalPrice());
                jsonData.put("status", OrderActivity.userOder.getReceiveStatus());
                jsonData.put("payment", OrderActivity.userOder.getPaymentMethod());
                jsonData.put("address", OrderActivity.userOder.getShippingAddress());

                System.out.println("ADDRESS " + OrderActivity.userOder.getShippingAddress());

                DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                os.writeBytes(jsonData.toString());
                status = connection.getResponseMessage();
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
            MainActivity.user.reload();
            new UpdateStock().execute(MainActivity.ROOT_API + "/product/stock/update");
        }
    }

    public class UpdateStock extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpHandler.getMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}