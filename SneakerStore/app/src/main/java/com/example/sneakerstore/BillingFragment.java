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

import com.example.sneakerstore.adapter.BillingAdapter;
import com.example.sneakerstore.model.Billing;

import java.util.ArrayList;
import java.util.List;

public class BillingFragment extends Fragment {

    RecyclerView billingList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_billing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        billingList = view.findViewById(R.id.billingList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        billingList.setLayoutManager(linearLayoutManager);

        List<Billing> list = new ArrayList<>();
        list.add(new Billing(1, "Phuc Hoang", 10000000, true));
        list.add(new Billing(2, "Phuc Hoang", 12000000, false));
        list.add(new Billing(3, "Phuc Hoang", 13000000, true));
        list.add(new Billing(4, "Phuc Hoang", 14000000, false));

        BillingAdapter adapter = new BillingAdapter(getContext());
        adapter.setData(list);
        billingList.setAdapter(adapter);
    }
}