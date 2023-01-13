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
import android.widget.Toast;

import com.example.sneakerstore.adapter.CheckoutList;
import com.example.sneakerstore.model.CheckOutSection;
import com.example.sneakerstore.model.CheckoutSneaker;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.Order;
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
    List<CartSneaker> sneakerList;
    CheckoutList checkoutList;
    ImageButton orderBackButton;

    // declaration for billing
    public static Order userOder;
    private int receiveOption;
    private int totalProductPrice;
    private int paymentOption;


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

        receiveOption = 0; // receive option = 1: shipping, 0: pick up
        paymentOption = 0;

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userOder = new Order(MainActivity.appUser.getUserId(), totalProductPrice + Integer.parseInt(costText.getText().toString().split(" ")[0]), receiveOption, (googlePayBox.isChecked()) ? 1 : 0, (shippingBox.isChecked()) ? placeView.getText().toString() : "");
                if (googlePayBox.isChecked()) {
                    Intent intent = new Intent(OrderActivity.this, OrderSuccessActivity.class);
                    startActivityForResult(intent, 30);
                }else {
                    Intent intent = new Intent(OrderActivity.this, CreditCardPaymentActivity.class);
                    startActivityForResult(intent, 30);
                }
            }
        });

        shippingBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    receiveOption = 1;
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
                    receiveOption = 0;
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
        getProductArr();
        checkoutList.setData(sneakerList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paymentView.setLayoutManager(linearLayoutManager);
        paymentView.setAdapter(checkoutList);


        subPrice = findViewById(R.id.subPrice);
        costText = findViewById(R.id.shipPrice);
        placeView = findViewById(R.id.shippingAddress);
        costText.setText("0 $");

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
    }

    private void getProductArr() {
        Intent intent = getIntent();
        String[] arr = intent.getStringArrayExtra("product_in_cart");
        for (String s : arr) {
            if (s != null) {
                String[] values = s.split(",");
                sneakerList.add(new CartSneaker(Integer.parseInt(values[0]), values[1],
                        values[2], values[3], Integer.parseInt(values[4]),
                        Integer.parseInt(values[5]), Double.parseDouble(values[6])));
            }
        }
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
                int shippingCost = distanceToDestination;
                if (distance / 1000 != 0) {
                    costText.setText(shippingCost + " $");
                }else {
                    costText.setText("0 $");
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
}