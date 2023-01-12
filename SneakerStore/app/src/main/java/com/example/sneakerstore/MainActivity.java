package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakerstore.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    ImageView arrow;
    TextView textView;
    int val;

    public static ArrayList<String> brands;
    public static HashMap<String, Integer> brandsHashMap;
    public static ArrayList<String> categories;
    public static HashMap<String, Integer> categoriesHashMap;

    public static User appUser;

    public static final String ROOT_API = "https://mappingapi-372807.as.r.appspot.com";
    public static final String ROOT_IMG = "https://ass3-android-bucket.s3.ap-southeast-1.amazonaws.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        appUser = new User(1);

        categoriesHashMap = new HashMap<>();
        categories = new ArrayList<>();
        brands = new ArrayList<>();
        brandsHashMap = new HashMap<>();

        DownloadCategory downloadCategory = new DownloadCategory();
        downloadCategory.execute(ROOT_API + "/product/category");
        DownloadBrand downloadBrand = new DownloadBrand();
        downloadBrand.execute(ROOT_API + "/product/brand");

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
                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
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

    public class DownloadBrand extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                brands.clear();
                JSONArray jsonArr = new JSONArray(s);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject brandObj = jsonArr.getJSONObject(i);
                    brands.add(brandObj.getString("NAME"));
                    brandsHashMap.put(brandObj.getString("NAME") , brandObj.getInt("ID"));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DownloadCategory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                Log.i("Re", urls[0]);
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            }catch (Exception e) {
                e.printStackTrace();

                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);
                JSONArray jsonArr = new JSONArray(s);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject categoryObj = jsonArr.getJSONObject(i);
                    categories.add(categoryObj.getString("NAME"));
                    categoriesHashMap.put(categoryObj.getString("NAME") , categoryObj.getInt("ID"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}