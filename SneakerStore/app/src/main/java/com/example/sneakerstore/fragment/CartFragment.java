package com.example.sneakerstore.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sneakerstore.HomePage;
import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.OrderActivity;
import com.example.sneakerstore.ProductDetailActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.CartAdapter;
import com.example.sneakerstore.model.HttpHandler;
import com.example.sneakerstore.sneaker.CartSneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView cartRc;
    CartAdapter adapter;
    List<CartSneaker> cartItemList;
    public static TextView totalPrice;

    SeekBar cartSeekbar;
    TextView cartSlideText;
    ImageView cartArrow;
    private int val;


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

        totalPrice = view.findViewById(R.id.totalProductPrice);

        adapter.setData(cartItemList);
        cartRc.setNestedScrollingEnabled(false);
        cartRc.setAdapter(adapter);


        // slider set up
        cartSeekbar = view.findViewById(R.id.cartSeekBar);
        cartArrow = view.findViewById(R.id.cartArrow);
        cartSlideText = view.findViewById(R.id.cartSlideText);



        DownloadCartProduct downloadCartProduct = new DownloadCartProduct();
        downloadCartProduct.execute(MainActivity.ROOT_API + "/product/cart?userid=" + MainActivity.appUser.getUserId());

        //set up seek bar listener
        cartSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                val = i;
                if (i >= 15) {
                    cartArrow.setVisibility(View.INVISIBLE);
                }
                else {
                    cartArrow.setVisibility(View.VISIBLE );
                }

                if (i >= 40) {
                    cartSlideText.setVisibility(View.INVISIBLE);
                } else {
                    cartSlideText.setVisibility(View.VISIBLE);
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
                    // load order page
                    String[] arr = new String[cartItemList.size()];
                    for (int i = 0; i < cartItemList.size(); i++) {
                        arr[i] = cartItemList.get(i).toString();
                    }
                    Intent intent = new Intent(getContext(), OrderActivity.class);

                    intent.putExtra("product_in_cart", arr);
                    startActivityForResult(intent, 900);
                    Handler handler = new Handler();
                    UpdateCartData updateCartData = new UpdateCartData();
                    updateCartData.execute(MainActivity.ROOT_API + "/product/cart");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(0);
                        }
                    }, 500);
                }
            }
        });
    }

    public class UpdateCartData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartItemList.size(); i++) {
                JSONArray itemData = new JSONArray();
                try {
                    // when user move to order page => update the cart
                    itemData.put(cartItemList.get(i).getQuantity());
                    itemData.put(MainActivity.appUser.getUserId());
                    itemData.put(cartItemList.get(i).getSneakerId());
                    itemData.put(cartItemList.get(i).getSize());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(itemData);
            }

            return HttpHandler.updateCart(urls[0], jsonArray);
        }

    }

    public class DownloadCartProduct extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpHandler.getMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                int totalProductPrice = 0;
                JSONArray productArr = new JSONArray(s);
                // update user's cart
                for (int i = 0; i < productArr.length(); i++) {
                    JSONObject object = productArr.getJSONObject(i);
                    cartItemList.add(new CartSneaker(object.getInt("PRODUCT_ID"), object.getString("PICTURE"), object.getString("BRAND"), object.getString("PRODUCT_NAME"), object.getInt("QUANTITY"), object.getInt("PRICE"), object.getDouble("SIZE")));
                    totalProductPrice += object.getInt("QUANTITY") * object.getInt("PRICE");
                }
                totalPrice.setText(Integer.toString(totalProductPrice) + " $");
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 900) {
            if (resultCode == -1) {
                // go back to home page when transaction finished
                HomePage.bottomNavigation.show(1, true);
            }
        }
    }
}