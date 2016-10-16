package com.whoami.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.CategoriesAdapter;
import com.whoami.moneytracker.database.CategoryEntity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment
@OptionsMenu(R.menu.menu_search)
public class CategoriesFragment extends Fragment {
    private static final int ID = 1;
    private static final String SEARCH_QUERY_ID = "search_query_id";
    private RecyclerView recyclerView;
    FloatingActionButton fab;
    SearchView searchView;

    @Override
    public void onStart() {
        super.onStart();
        categoryQuery("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories_fragment, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.categories_fab);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                categoryQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY_ID, true);
                categoryQuery(s);
                return false;
            }
        });
    }

    @Background(delay = 1000, id = SEARCH_QUERY_ID)
    void categoryQuery(String query) {
        loadCategory(query);
    }

    private void loadCategory(final String query) {
        getLoaderManager().restartLoader(ID, null, new LoaderManager.LoaderCallbacks<List<CategoryEntity>>() {
            @Override
            public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
                    @Override
                    public List<CategoryEntity> loadInBackground() {
                        return CategoryEntity.selectAll(query);
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
        });
    }
}