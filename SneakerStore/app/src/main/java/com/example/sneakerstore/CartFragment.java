package com.example.sneakerstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sneakerstore.sneaker.CartAdapter;
import com.example.sneakerstore.sneaker.CartSneaker;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView cartRc;
    CartAdapter adapter;


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

        List<CartSneaker> cartItemList = new ArrayList<>();
        cartItemList.add(new CartSneaker(R.drawable.air_max, "Nike", "Air max 1", 0, 3000000));
        cartItemList.add(new CartSneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5", 0, 3000000));
        cartItemList.add(new CartSneaker(R.drawable.air_max, "Nike", "Air max 1", 0, 3000000));
        cartItemList.add(new CartSneaker(R.drawable.zoom_fly, "Nike", "Zoom fly 5", 0, 3000000));

        adapter.setData(cartItemList);
        cartRc.setAdapter(adapter);

    }
}