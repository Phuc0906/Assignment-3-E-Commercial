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

import com.example.sneakerstore.sneaker.Category;
import com.example.sneakerstore.sneaker.CategoryAdapter;
import com.example.sneakerstore.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcList;
    CategoryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcList = view.findViewById(R.id.recyclerList);
        adapter = new CategoryAdapter(getContext());

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

        return list;
    }
}