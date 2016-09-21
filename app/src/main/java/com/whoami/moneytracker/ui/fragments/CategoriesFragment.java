package com.whoami.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.CategoriesAdapter;
import com.whoami.moneytracker.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_categories);

        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.categories_coordinator);

        initRecycleView(rootView);

        return rootView;
    }

    private void initRecycleView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getCategories());
        recyclerView.setAdapter(categoriesAdapter);
    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Учеба"));
        categories.add(new Category("Лекарства"));
        categories.add(new Category("Еда"));
        categories.add(new Category("ЖКХ"));
        categories.add(new Category("Кафе"));
        categories.add(new Category("Развлечения"));
        categories.add(new Category("Отдых"));
        return categories;
    }
}