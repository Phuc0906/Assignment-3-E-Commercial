package com.example.sneakerstore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.ProductListAdapter;
import com.example.sneakerstore.banner.Banner;
import com.example.sneakerstore.banner.BannerAdapter;
import com.example.sneakerstore.logo.Logo;
import com.example.sneakerstore.logo.LogoAdapter;
import com.example.sneakerstore.model.ProductExplore;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class ExploreFragment extends Fragment {

    RecyclerView itemListView;
    ProductListAdapter adapter;
    BannerAdapter bannerAdapter;
    ViewPager2 viewPager2;
    CircleIndicator3 indicator3;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == getBannerList().size() - 1) {
                viewPager2.setCurrentItem(0);
            }
            else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        // initialize
        itemListView = view.findViewById(R.id.productList);
        viewPager2 = view.findViewById(R.id.viewPager);
        indicator3 = view.findViewById(R.id.indicator);

        // set up for recyclerView
        adapter = new ProductListAdapter(getContext());
        adapter.setData(getProductList());
        itemListView.setLayoutManager(gridLayoutManager);
        itemListView.setAdapter(adapter);

        //set up for Viewpaper
        bannerAdapter = new BannerAdapter(getBannerList());
        viewPager2.setAdapter(bannerAdapter);
        indicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
        return view;
    }



    private List<Banner> getBannerList() {
        List<Banner> list = new ArrayList<>();
        list.add(new Banner(R.drawable.banner1));
        list.add(new Banner(R.drawable.banner2));
        list.add(new Banner(R.drawable.banner3));
        list.add(new Banner(R.drawable.banner4));
        return list;
    }

    private List<ProductExplore> getProductList() {
        List<ProductExplore> list = new ArrayList<>();
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        list.add(new ProductExplore("Nike", "Air Max 1", R.drawable.air_max));
        list.add(new ProductExplore("Nike", "Zoom Fly", R.drawable.zoom_fly));
        list.add(new ProductExplore("Adidas", "Super Star", R.drawable.superstar));
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}