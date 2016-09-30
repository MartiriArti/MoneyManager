package com.whoami.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.CategoriesAdapter;
import com.whoami.moneytracker.database.CategoryEntity;

import java.util.List;


public class CategoriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CategoryEntity>> {

    private final int LOADER_ID = 1;
    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_categories);

        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.categories_coordinator);

        getLoaderManager().restartLoader(LOADER_ID, null, this);
        initRecycleView(rootView);
        initFab(rootView);
        return rootView;
    }

    private void initRecycleView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initFab(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.categories_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, R.string.categories_snackbar_message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
            @Override
            public List<CategoryEntity> loadInBackground() {
                return CategoryEntity.selectAll();
            }
        };
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<CategoryEntity>> loader, List<CategoryEntity> data) {
        recyclerView.setAdapter(new CategoriesAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<CategoryEntity>> loader) {

    }

}