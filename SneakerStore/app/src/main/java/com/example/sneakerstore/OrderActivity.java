package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sneakerstore.adapter.CheckoutList;
import com.example.sneakerstore.model.CheckOutSection;
import com.example.sneakerstore.model.CheckoutSneaker;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.sneaker.CartSneaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class OrderActivity extends AppCompatActivity{

    ImageButton toMapBtn;
    TextView costText, placeView, subPrice;
    CheckBox creditCardBox, googlePayBox, shippingBox, pickUpBox;
    Button paymentBtn;
    RecyclerView paymentView;
    List<CheckoutSneaker> sneakerList;
    CheckoutList checkoutList;
    ImageButton orderBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        sneakerList = new ArrayList<>();

        List<CheckOutSection> checkOutSectionList = new ArrayList<>();

        CheckOutSection checkOutSection = new CheckOutSection("Order Summary", sneakerList);
        checkOutSectionList.add(checkOutSection);

        paymentView = findViewById(R.id.paymentView);
        creditCardBox = findViewById(R.id.creditCardBox);
        creditCardBox.setChecked(true);
        googlePayBox = findViewById(R.id.googlePayBox);
        shippingBox = findViewById(R.id.shippingCheckBox);
        shippingBox.setChecked(true);
        pickUpBox = findViewById(R.id.pickUpCheckBox);
        orderBackButton = findViewById(R.id.orderBackBtn);

        paymentBtn = findViewById(R.id.paymentBtn);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, CreditCardPaymentActivity.class);
                startActivityForResult(intent, 30);
            }
        });

        shippingBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    pickUpBox.setChecked(false);
                }else {
                    pickUpBox.setChecked(true);
                }
            }
        });

        pickUpBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    shippingBox.setChecked(false);
                }else {
                    shippingBox.setChecked(true);
                }
            }
        });



        googlePayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    creditCardBox.setChecked(false);
                }else {
                    creditCardBox.setChecked(true);
                }
            }
        });

        creditCardBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    googlePayBox.setChecked(false);
                }else {
                    googlePayBox.setChecked(true);
                }
            }
        });



        checkoutList = new CheckoutList(this);
        checkoutList.setData(sneakerList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paymentView.setLayoutManager(linearLayoutManager);
        paymentView.setAdapter(checkoutList);


        subPrice = findViewById(R.id.subPrice);
        costText = findViewById(R.id.shipPrice);
        placeView = findViewById(R.id.shippingAddress);
        costText.setText("0 vnd");

        toMapBtn = findViewById(R.id.toMapBtn);
        toMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shippingBox.isChecked()) {
                    Intent intent = new Intent(OrderActivity.this, MapActivity.class);
                    startActivityForResult(intent, 10);
                }

            }
        });

        orderBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DownloadCheckoutProduct downloadCheckoutProduct = new DownloadCheckoutProduct();
        downloadCheckoutProduct.execute(MainActivity.ROOT_API + "/product/cart?userid=" + MainActivity.appUser.getUserId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                float distance = data.getFloatExtra("distance", 0);
                String place = data.getStringExtra("place");

                placeView.setText(place);

                int distanceToDestination = Math.round(distance/1000);
                int shippingCost = (distanceToDestination * 3000);
                if (distance / 1000 != 0) {
                    costText.setText(shippingCost + " vnd");
                }else {
                    costText.setText("0 vnd");
                }


            }
        }else if (requestCode == 30) {
            if (resultCode == RESULT_OK) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    public class DownloadCheckoutProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpHandler.getMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                int totalProductPrice = 0;
                JSONArray productArr = new JSONArray(s);
                for (int i = 0; i < productArr.length(); i++) {
                    JSONObject object = productArr.getJSONObject(i);
                    sneakerList.add(new CheckoutSneaker(object.getInt("PRODUCT_ID"), object.getString("PICTURE"), object.getString("BRAND"), object.getString("PRODUCT_NAME"), object.getInt("PRICE"), object.getInt("QUANTITY"), object.getDouble("SIZE")));
                    totalProductPrice += object.getInt("QUANTITY") * object.getInt("PRICE");
                }
                checkoutList.notifyDataSetChanged();
                subPrice.setText(totalProductPrice + " $");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}