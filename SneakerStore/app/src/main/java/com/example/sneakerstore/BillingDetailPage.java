package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sneakerstore.adapter.CheckoutList;
import com.example.sneakerstore.model.CheckoutSneaker;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillingDetailPage extends AppCompatActivity {
    ImageButton billingDetailBackBtn;
    TextView billingDetailNumber, billingTotalPrice, billingShippingDetail, billingCustomer;
    ImageView shippingImg, paymentImg;
    RecyclerView billingItems;
    Button billingOkBtn;
    RecyclerView itemsList;
    List<CheckoutSneaker> sneakerList;
    CheckoutList billingSneakers;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail_page);

        // get intent
        intent = getIntent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sneakerList = new ArrayList<>();
        billingSneakers = new CheckoutList(this);
        billingSneakers.setData(sneakerList);

        billingDetailBackBtn = findViewById(R.id.billingDetailBackBtn);
        billingDetailNumber = findViewById(R.id.billingDetailNumber);
        billingTotalPrice = findViewById(R.id.billingTotalPrice);
        shippingImg = findViewById(R.id.shippingStatusImg);
        billingShippingDetail = findViewById(R.id.billingShippingDetail);
        paymentImg = findViewById(R.id.billingPaymentImg);
        billingItems = findViewById(R.id.billingOrderItems);
        billingOkBtn = findViewById(R.id.billingOkBtn);
        billingCustomer = findViewById(R.id.billingCustomer);
        itemsList = findViewById(R.id.billingOrderItems);
        itemsList.setLayoutManager(linearLayoutManager);
        itemsList.setAdapter(billingSneakers);

        billingDetailNumber.setText("Billing Number: " + Integer.toString(intent.getIntExtra("billing_id", 0)));
        billingCustomer.setText("Customer: " + intent.getStringExtra("customer"));
        shippingImg.setImageResource((intent.getIntExtra("status", 0) == 1) ? R.drawable.shipping_icon : R.drawable.ic_baseline_store_24);
        billingShippingDetail.setText((intent.getIntExtra("status", 0) == 1) ? intent.getStringExtra("address") : "Pick up at store");
        billingTotalPrice.setText("Billing Total: " + Double.toString(intent.getDoubleExtra("total", 0)) + " $");
        paymentImg.setImageResource((intent.getIntExtra("payment", 0) == 1) ? R.drawable.gpay_logo : R.drawable.ic_baseline_credit_card_24);

        new readJSON().execute();

        // setting for button
        billingDetailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        billingOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class readJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getMethod(MainActivity.ROOT_API + "/product/billing/detail?buyid=" + intent.getIntExtra("billing_id", 0));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONArray jsonArray =new JSONArray(s);
                    System.out.println(jsonArray);
                    sneakerList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sneakerList.add(new CheckoutSneaker(object.getInt("ID"), object.getString("PICTURE"), object.getString("BRAND"), object.getString("NAME"), object.getInt("ITEM_PRICE"), object.getInt("QUANTITY"), object.getDouble("SIZE")));
                    }
                    billingSneakers.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}