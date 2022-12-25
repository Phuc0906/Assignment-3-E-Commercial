package com.example.sneakerstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sneakerstore.logo.Logo;
import com.example.sneakerstore.logo.LogoAdapter;
import com.example.sneakerstore.sneaker.Category;
import com.example.sneakerstore.sneaker.CategoryAdapter;
import com.example.sneakerstore.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcList;
    CategoryAdapter adapter;
    ViewPager2 viewPager2;
    LogoAdapter logoAdapter;
    Handler handler = new Handler(Looper.getMainLooper());
    final int startPosition = 1;

    Runnable lRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == getLogoList().size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        rcList = view.findViewById(R.id.recyclerList);
        adapter = new CategoryAdapter(getContext());
        viewPager2 = view.findViewById(R.id.viewPager2);
        logoAdapter = new LogoAdapter(getLogoList());

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setAdapter(logoAdapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(lRunnable);
                handler.postDelayed(lRunnable, 5000);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcList.setLayoutManager(linearLayoutManager);

        adapter.setData(getCategoryList());
        rcList.setAdapter(adapter);

    }

    private List<Category> getCategoryList() {
        List<Category> list = new ArrayList<>();
        List<Sneaker> sneakerList = new ArrayList<>();

        sneakerList.add(new Sneaker(R.drawable.air_max, "Nike", "Air max 1"));
        sneakerList.add(new Sneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5"));
        sneakerList.add(new Sneaker(R.drawable.air_max, "Nike", "Air max 1"));
        sneakerList.add(new Sneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5"));
        list.add(new Category("Newest Nike Shoes", sneakerList));

        List<Sneaker> sneakerList1 = new ArrayList<>();
        sneakerList1.add(new Sneaker(R.drawable.air_max, "Nike", "Air max 1"));
        sneakerList1.add(new Sneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5"));
        list.add(new Category("Popular", sneakerList1));

        List<Sneaker> sneakerList2 = new ArrayList<>();
        sneakerList2.add(new Sneaker(R.drawable.air_max, "Nike", "Air max 1"));
        sneakerList2.add(new Sneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5"));


        list.add(new Category("Daily Life", sneakerList2));
        return list;
    }

    private List<Logo> getLogoList() {
        List<Logo> photoList = new ArrayList<>();
        photoList.add(new Logo(R.drawable.adidas_logo));
        photoList.add(new Logo(R.drawable.nike_logo));
        photoList.add(new Logo(R.drawable.asics_logo));
        photoList.add(new Logo(R.drawable.puma_logo));
        photoList.add(new Logo(R.drawable.adidas_logo));
        photoList.add(new Logo(R.drawable.nike_logo));
        photoList.add(new Logo(R.drawable.asics_logo));
        photoList.add(new Logo(R.drawable.puma_logo));
        return photoList;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(lRunnable, 5000);

        Runnable setDefault = new Runnable() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(startPosition, false);
            }
        };

        Handler h = new Handler();
        h.post(setDefault);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(lRunnable);
    }
}