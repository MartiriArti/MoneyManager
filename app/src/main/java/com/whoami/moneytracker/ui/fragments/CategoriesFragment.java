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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.activeandroid.query.Select;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.CategoriesAdapter;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.database.ExpenseEntity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment
@OptionsMenu(R.menu.menu_search)
public class CategoriesFragment extends Fragment {

    private static final String SEARCH_QUERY_ID = "search_query_id";
    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;
    private FloatingActionButton fab;

    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_categories);

        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.categories_coordinator);

        initRecycleView(rootView);
        initFab(rootView);
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
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY_ID, true);
                queryExpenses(newText);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExpenses("");
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

    @Background(delay = 600, id = SEARCH_QUERY_ID)
    void queryExpenses(String query) {
        loadExpenses(query);
    }

    private void loadExpenses(final String query) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<CategoryEntity>>() {
            @Override
            public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
                    @Override
                    public List<CategoryEntity> loadInBackground() {
                        return getDataList(query);
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

    private List<CategoryEntity> getDataList(String filter){
        return new Select()
                .from(CategoryEntity.class)
                .where("Name LIKE ?", new String[]{'%' + filter + '%'})
                .execute();
    }
}