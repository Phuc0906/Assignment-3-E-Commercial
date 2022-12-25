package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.sneakerstore.adapter.CheckoutList;
import com.example.sneakerstore.model.CheckOutSection;
import com.example.sneakerstore.model.CheckoutSneaker;

import java.util.ArrayList;
import java.util.List;



public class OrderActivity extends AppCompatActivity{

    Button toMapBtn;
    TextView distanceText, costText, placeView;

    RecyclerView paymentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        List<CheckoutSneaker> sneakerList = new ArrayList<>();
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));
        sneakerList.add(new CheckoutSneaker(R.drawable.air_max, "Nike", "Air max", 3000000, 2, 8.5));

        List<CheckOutSection> checkOutSectionList = new ArrayList<>();

        CheckOutSection checkOutSection = new CheckOutSection("Order Summary", sneakerList);
        checkOutSectionList.add(checkOutSection);

        paymentView = findViewById(R.id.paymentView);

        CheckoutList checkoutList = new CheckoutList(this);
        checkoutList.setData(sneakerList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paymentView.setLayoutManager(linearLayoutManager);
        paymentView.setAdapter(checkoutList);


//        distanceText = findViewById(R.id.distanceText);
//        costText = findViewById(R.id.costText);
//        placeView = findViewById(R.id.placeView);
//
//        toMapBtn = findViewById(R.id.toMapBtn);
//        toMapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OrderActivity.this, MapActivity.class);
//                startActivityForResult(intent, 10);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 10) {
//            if (resultCode == RESULT_OK) {
//                float distance = data.getFloatExtra("distance", 0);
//                String place = data.getStringExtra("place");
//
//                placeView.setText("Place: " + place);
//
//                int distanceToDestination = Math.round(distance/1000);
//                distanceText.setText("Distance: " + distanceToDestination + " km");
//                if (distance / 1000 != 0) {
//                    costText.setText("Cost: " + (distanceToDestination * 3000) + " vnd");
//                }else {
//                    costText.setText("Free ship");
//                }
//            }
//        }
    }
}