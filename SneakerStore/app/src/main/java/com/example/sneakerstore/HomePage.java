package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.sneakerstore.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

    static List<Sneaker> sneakerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);

        bottomNavigation = findViewById(R.id.bottomNav);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.explore_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.cart_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.user_icon));

        sneakerList = new ArrayList<>();

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;

                if (item.getId() == 1) {
                    fragment = new HomeFragment();
                }else if (item.getId() == 2) {
                    fragment = new CartFragment();
                }else if (item.getId() == 3) {
                    fragment = new CartFragment();
                }else {
                    fragment = new PersonalFragment();
                }

                loadFragment(fragment);
            }
        });

        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You click " + item.getId(), Toast.LENGTH_LONG);
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You click " + item.getId(), Toast.LENGTH_LONG);
            }
        });



    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, null).commit();
    }
}