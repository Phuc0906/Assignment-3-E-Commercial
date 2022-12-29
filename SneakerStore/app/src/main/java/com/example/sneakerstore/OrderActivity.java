package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;



public class OrderActivity extends AppCompatActivity{

    ImageButton toMapBtn;
    TextView costText, placeView;
    CheckBox creditCardBox, googlePayBox;
    Button paymentBtn;
    RecyclerView paymentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        List<CheckoutSneaker> sneakerList = new ArrayList<>();
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
//        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));

        List<CheckOutSection> checkOutSectionList = new ArrayList<>();

        CheckOutSection checkOutSection = new CheckOutSection("Order Summary", sneakerList);
        checkOutSectionList.add(checkOutSection);

        paymentView = findViewById(R.id.paymentView);
        creditCardBox = findViewById(R.id.creditCardBox);
        googlePayBox = findViewById(R.id.googlePayBox);

        paymentBtn = findViewById(R.id.paymentBtn);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, CreditCardPaymentActivity.class);
                startActivityForResult(intent, 30);
            }
        });



        googlePayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    creditCardBox.setSelected(false);
                }else {
                    creditCardBox.setSelected(true);
                }
            }
        });





        CheckoutList checkoutList = new CheckoutList(this);
        checkoutList.setData(sneakerList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paymentView.setLayoutManager(linearLayoutManager);
        paymentView.setAdapter(checkoutList);



        costText = findViewById(R.id.shipPrice);
        placeView = findViewById(R.id.shippingAddress);

        toMapBtn = findViewById(R.id.toMapBtn);
        toMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MapActivity.class);
                startActivityForResult(intent, 10);
            }
        });
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
}