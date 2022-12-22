package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    Button toMapBtn;
    TextView distanceText, costText, placeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        distanceText = findViewById(R.id.distanceText);
        costText = findViewById(R.id.costText);
        placeView = findViewById(R.id.placeView);

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

                placeView.setText("Place: " + place);

                int distanceToDestination = Math.round(distance/1000);
                distanceText.setText("Distance: " + distanceToDestination + " km");
                if (distance / 1000 != 0) {
                    costText.setText("Cost: " + (distanceToDestination * 3000) + " vnd");
                }else {
                    costText.setText("Free ship");
                }
            }
        }
    }
}