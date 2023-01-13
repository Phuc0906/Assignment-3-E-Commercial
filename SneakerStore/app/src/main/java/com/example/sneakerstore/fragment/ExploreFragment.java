package com.example.sneakerstore.fragment;

import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.ProductAdapter;
import com.example.sneakerstore.banner.Banner;
import com.example.sneakerstore.banner.BannerAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;

public class ExploreFragment extends Fragment {
    ProgressBar progressBar;
    EditText searchView;
    RecyclerView itemListView;
    ProductAdapter adapter;
    BannerAdapter bannerAdapter;
    ViewPager2 viewPager2;
    CircleIndicator3 indicator3;
    private static List<Product> productList = new ArrayList<>();


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
        new readJSON().execute();
        // initialize
        itemListView = view.findViewById(R.id.productList);
        viewPager2 = view.findViewById(R.id.viewPager);
        indicator3 = view.findViewById(R.id.indicator);
        searchView = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.progressBar);
        // set up for recyclerView
        adapter = new ProductAdapter(getContext(), 0);
        adapter.setData(productList);
        itemListView.setLayoutManager(gridLayoutManager);
        itemListView.setAdapter(adapter);

        //set up for viewPaper
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

    public class readJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
           return HttpHandler.getMethod(MainActivity.ROOT_API + "/product/latest");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONArray jsonArray =new JSONArray(s);
                    System.out.println(jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        productList.add(new Product(object.getInt("ID"),
                                object.getString("NAME"),
                                object.getString("DESCRIPTION"),
                                object.getDouble("PRICE"),
                                object.getString("category"),
                                object.getString("brand"),
                                object.getString("PICTURE")));
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    searchView.setVisibility(View.VISIBLE);
                    viewPager2.setVisibility(View.VISIBLE);
                    itemListView.setVisibility(View.VISIBLE);
                    indicator3.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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