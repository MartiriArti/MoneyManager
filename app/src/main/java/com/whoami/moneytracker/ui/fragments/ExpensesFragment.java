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
import com.whoami.moneytracker.adapters.ExpensesAdapter;
import com.whoami.moneytracker.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment{

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
        return rootView;
    }

    private void initRecycleView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getExpenses());
        recyclerView.setAdapter(expensesAdapter);
    }

    private void initFab(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.expenses_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, R.string.expenses_snackbar_message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Кино", "150"));
        expenses.add(new Expense("Кафе", "140"));
        expenses.add(new Expense("Одежда", "750"));
        expenses.add(new Expense("ЖКХ", "833"));
        expenses.add(new Expense("Мак", "140"));
        expenses.add(new Expense("Учебники", "200"));
        expenses.add(new Expense("Кафе", "120"));
        expenses.add(new Expense("Кино", "150"));
        expenses.add(new Expense("Кафе", "140"));
        expenses.add(new Expense("Одежда", "750"));
        expenses.add(new Expense("ЖКХ", "833"));
        expenses.add(new Expense("Мак", "140"));
        expenses.add(new Expense("Учебники", "200"));
        expenses.add(new Expense("Кафе", "120"));
        expenses.add(new Expense("Кино", "150"));
        expenses.add(new Expense("Кафе", "140"));
        expenses.add(new Expense("Одежда", "750"));
        expenses.add(new Expense("ЖКХ", "833"));
        expenses.add(new Expense("Мак", "140"));
        expenses.add(new Expense("Учебники", "200"));
        expenses.add(new Expense("Кафе", "120"));
        return expenses;
    }
}