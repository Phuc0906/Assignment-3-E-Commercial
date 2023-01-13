package com.example.sneakerstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.sneakerstore.fragment.BillingFragment;
import com.example.sneakerstore.fragment.PersonalFragment;

public class AdminActivity extends AppCompatActivity {



    MeowBottomNavigation adminBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminBottomNav = findViewById(R.id.adminBottomNav);

        adminBottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.explore_icon));
        adminBottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.pngegg));
        adminBottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.user_icon));

        adminBottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;

                if (item.getId() == 1) {
                    fragment = new ProductManagementFragment();
                }else if (item.getId() == 2) {
                    fragment = new BillingFragment();
                }else {
                    fragment = new PersonalFragment();
                }

                loadFragment(fragment);
            }
        });

        adminBottomNav.show(1, true);

        adminBottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You click " + item.getId(), Toast.LENGTH_LONG);
            }
        });

        adminBottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You click " + item.getId(), Toast.LENGTH_LONG);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, fragment, null).commit();
    }
}