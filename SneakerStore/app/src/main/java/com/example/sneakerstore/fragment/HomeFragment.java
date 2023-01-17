package com.example.sneakerstore.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.logo.Logo;
import com.example.sneakerstore.logo.LogoAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.sneaker.Category;
import com.example.sneakerstore.adapter.CategoryAdapter;
import com.example.sneakerstore.sneaker.Sneaker;
import com.github.ybq.android.spinkit.style.CubeGrid;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    EditText searchView;
    ProgressBar progressBar;
    RecyclerView rcList;
    CategoryAdapter adapter;
    ViewPager2 viewPager2;
    LogoAdapter logoAdapter;
    ImageView img;
    Handler handler = new Handler(Looper.getMainLooper());
    final int startPosition = 1;
    List<Sneaker> sneakerList;
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

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(new CubeGrid());
        searchView = view.findViewById(R.id.edt_search);
        rcList = view.findViewById(R.id.recyclerList);
        adapter = new CategoryAdapter(getContext());
        viewPager2 = view.findViewById(R.id.viewPager2);
        logoAdapter = new LogoAdapter(getLogoList(), getContext());

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        sneakerList = new ArrayList<>();

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setAdapter(logoAdapter);

        img = view.findViewById(R.id.imageView4);

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



        DownloadLatestProduct downloadLatestProduct = new DownloadLatestProduct();
        downloadLatestProduct.execute(MainActivity.ROOT_API + "/product/latest");

        List<Category> list = new ArrayList<>();
        list.add(new Category("Latest", sneakerList));
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        rcList.setAdapter(adapter);

    }

    private List<Logo> getLogoList() {
        List<Logo> photoList = new ArrayList<>();
        photoList.add(new Logo(R.drawable.adidas_logo, "Adidas"));
        photoList.add(new Logo(R.drawable.nike_logo, "Nike"));
        photoList.add(new Logo(R.drawable.underarmour, "Under Armour"));
        photoList.add(new Logo(R.drawable.puma_logo, "Puma"));
        photoList.add(new Logo(R.drawable.adidas_logo, "Adidas"));
        photoList.add(new Logo(R.drawable.nike_logo, "Nike"));
        photoList.add(new Logo(R.drawable.underarmour, "Under Armour"));
        photoList.add(new Logo(R.drawable.puma_logo, "Puma"));
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

    public class DownloadLatestProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpHandler.getMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                System.out.println(s);
                sneakerList.clear();
                JSONArray jsonArr = new JSONArray(s);
                System.out.println(jsonArr);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject sneakerObj = jsonArr.getJSONObject(i);
                    sneakerList.add(new Sneaker(sneakerObj.getInt("ID") ,MainActivity.ROOT_IMG + sneakerObj.getString("PICTURE"), sneakerObj.getString("brand"), sneakerObj.getString("NAME")));
                }
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
                rcList.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}