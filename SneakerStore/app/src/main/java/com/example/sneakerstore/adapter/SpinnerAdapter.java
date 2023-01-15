package com.example.sneakerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sneakerstore.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.spinner_select_tv);

        String s = this.getItem(position);
        if (s != null) {
            tvSelected.setText(s);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView tv = convertView.findViewById(R.id.spinner_select_tv);

        String s = this.getItem(position);
        if (s != null) {
            tv.setText(s);
        }
        return convertView;
    }
}
