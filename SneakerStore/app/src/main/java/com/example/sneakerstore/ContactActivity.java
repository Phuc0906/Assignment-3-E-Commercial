package com.example.sneakerstore;

import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactActivity extends Activity {
    ImageView phoneImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);
        //init
        phoneImage = findViewById(R.id.contact_phone);
        //set animation
        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.custom_rotate);
        phoneImage.startAnimation(rotateAnim);
        //set event

        phoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(
                        ContactActivity.this, Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:12345678"));
                    startActivity(intent);
                } else {
                    // You can directly ask for the permission.
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 100);
                }
            }
        });
    }
}