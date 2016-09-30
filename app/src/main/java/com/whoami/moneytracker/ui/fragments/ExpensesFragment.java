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
import android.view.View;
import android.view.ViewGroup;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.ExpensesAdapter;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.ui.AddExpenseActivity_;

import java.util.List;

public class ExpensesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ExpenseEntity>> {

    private final int LOADER_ID = 1;

    private RecyclerView recyclerView;
    private CoordinatorLayout rootLayout;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_expenses);
        rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.expenses_coordinator);

        initRecycleView(rootView);
        initFab(rootView);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        return rootView;
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

    @Override
    public Loader<List<ExpenseEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<ExpenseEntity>> loader = new AsyncTaskLoader<List<ExpenseEntity>>(getActivity()) {
            @Override
            public List<ExpenseEntity> loadInBackground() {
                return ExpenseEntity.selectAll();
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

}