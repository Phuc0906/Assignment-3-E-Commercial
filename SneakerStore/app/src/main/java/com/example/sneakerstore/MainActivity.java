package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    ImageView arrow;
    TextView textView;
    int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initialize();
        setEvent();
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