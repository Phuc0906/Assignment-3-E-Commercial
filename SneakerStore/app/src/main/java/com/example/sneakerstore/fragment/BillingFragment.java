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

import com.example.sneakerstore.MainActivity;
import com.example.sneakerstore.R;
import com.example.sneakerstore.adapter.BillingAdapter;
import com.example.sneakerstore.model.Billing;
import com.example.sneakerstore.model.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BillingFragment extends Fragment {

    RecyclerView billingList;
    List<Billing> list;
    BillingAdapter adapter;

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

        list = new ArrayList<>();

        adapter = new BillingAdapter(getContext());
        adapter.setData(list);
        billingList.setAdapter(adapter);

        new DownloadBrand().execute(MainActivity.ROOT_API + "/product/billing");

    }

    public class DownloadBrand extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return HttpHandler.getMethod(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);

                JSONArray jsonArr = new JSONArray(s);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject object = jsonArr.getJSONObject(i);
                    list.add(new Billing(object.getInt("BUYID"), object.getString("NAME"), object.getDouble("TOTAL_PRICE"), (object.getInt("STATUS") == 1) ? true : false, object.getString("ADDRESS"), (object.getInt("PAYMENT") == 1) ? true : false));
                    System.out.println("TOTAL: " + object.getDouble("TOTAL_PRICE"));
                    System.out.println("PAYMENT: " + object.getInt("PAYMENT"));

                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}