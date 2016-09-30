package com.whoami.moneytracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;

import java.util.List;

public class MySpinnerAdapter extends ArrayAdapter<CategoryEntity> implements SpinnerAdapter {

    List<CategoryEntity> categories;

    public MySpinnerAdapter(Context ctx, List<CategoryEntity> listCategory) {
        super(ctx, 0, listCategory);
        this.categories = listCategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryEntity categoryEntity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_txt, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.spinner_txt_view);

        name.setText(categoryEntity.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CategoryEntity categoryEntity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_txt, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.spinner_txt_view);

        name.setText(categoryEntity.name);

        return convertView;
    }
}
