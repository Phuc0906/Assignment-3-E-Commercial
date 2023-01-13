package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakerstore.model.User;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    ImageView arrow;
    TextView textView;
    int val;

    public static User appUser;

    public static final String ROOT_API = "https://mappingapi-372807.as.r.appspot.com";
    public static final String ROOT_IMG = "https://ass3-android-bucket.s3.ap-southeast-1.amazonaws.com/";

    ImageView shoesImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        appUser = new User(1);

        // get share preferences value
        SharedPreferences sharePref = this.getPreferences(Context.MODE_PRIVATE);
        int loginStatus = sharePref.getInt("session", 0); // if 0 => user is not in session, 1 => in session
        int role = sharePref.getInt("role", 0); // 0: user, 1: admin

        if (loginStatus == 0) {
            initialize();
            setEvent();
        }else {
            if (role == 0) {
                startActivity(new Intent(MainActivity.this, HomePage.class));
            }else {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
        }



    }

    private void initialize() {
        seekBar = findViewById(R.id.seekBar);
        arrow = findViewById(R.id.arrow);
        textView = findViewById(R.id.slideText);

    }

    private void setEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                val = i;
                if (i >= 15) {
                    arrow.setVisibility(View.INVISIBLE);
                }
                else {
                    arrow.setVisibility(View.VISIBLE );
                }

                if (i >= 40) {
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (val < seekBar.getMax()) {
                    seekBar.setProgress(0);
                } else {
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(0);
                        }
                    }, 300);
                }
            }
        });
    }
}