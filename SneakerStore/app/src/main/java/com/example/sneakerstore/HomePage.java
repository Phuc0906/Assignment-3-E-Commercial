package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomePage extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

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

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;

                if (item.getId() == 1) {
                    fragment = new HomeFragment();
                }else if (item.getId() == 2) {
                    fragment = new CartFragment();
                }else if (item.getId() == 3) {
                    fragment = new HomeFragment();
                }else {
                    fragment = new HomeFragment();
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

        bottomNavigation.setCount(3, "10");


    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, null).commit();
    }
}