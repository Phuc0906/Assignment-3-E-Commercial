package com.example.sneakerstore.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.CartAdapter;
import com.example.sneakerstore.sneaker.CartSneaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView cartRc;
    CartAdapter adapter;
    List<CartSneaker> cartItemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartRc = view.findViewById(R.id.cartList);
        adapter = new CartAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cartRc.setLayoutManager(linearLayoutManager);

        cartItemList = new ArrayList<>();



        adapter.setData(cartItemList);
        cartRc.setNestedScrollingEnabled(false);
        cartRc.setAdapter(adapter);

        DownloadCartProduct downloadCartProduct = new DownloadCartProduct();
        downloadCartProduct.execute(MainActivity.ROOT_API + "/product/cart?userid=" + MainActivity.appUser.getUserId());

    }

    public class DownloadCartProduct extends AsyncTask<String, Void, String> {

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

                System.out.println("IN");
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                System.out.println(result);

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
                JSONArray productArr = new JSONArray(s);
                for (int i = 0; i < productArr.length(); i++) {
                    JSONObject object = productArr.getJSONObject(i);
                    cartItemList.add(new CartSneaker(object.getInt("PRODUCT_ID"), object.getString("PICTURE"), object.getString("BRAND"), object.getString("PRODUCT_NAME"), object.getInt("QUANTITY"), object.getInt("PRICE")));
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}