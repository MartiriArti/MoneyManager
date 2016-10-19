package com.whoami.moneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import com.whoami.moneytracker.adapters.ExpensesAdapter;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.ui.AddExpenseActivity_;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;
@EFragment
@OptionsMenu(R.menu.menu_search)
public class ExpensesFragment extends Fragment{

    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;
    @ViewById(R.id.expenses_fab)
    FloatingActionButton fab;

    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_expenses);
        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.expenses_coordinator);

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
                BackgroundExecutor.cancelAll(ConstantManager.SEARCH_QUERY_ID, true);
                queryExpenses(newText);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExpenses("");
    }

    private void initRecycleView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initFab(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.expenses_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
                startActivity(intent);
            }
        });
    }
    @Background(delay = ConstantManager.DELAY, id = ConstantManager.SEARCH_QUERY_ID)
    void queryExpenses(String query){
        loadExpenses(query);
    }

    private void loadExpenses(final String query) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<ExpenseEntity>>() {
            @Override
            public Loader<List<ExpenseEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ExpenseEntity>> loader = new AsyncTaskLoader<List<ExpenseEntity>>(getActivity()) {
                    @Override
                    public List<ExpenseEntity> loadInBackground() {
                        return ExpenseEntity.selectAll(query);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ExpenseEntity>> loader, List<ExpenseEntity> data) {
                recyclerView.setAdapter(new ExpensesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<ExpenseEntity>> loader) {

            }
        });
    }
}