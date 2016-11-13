package com.whoami.moneytracker.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.ExpensesAdapter;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.ui.AddExpenseActivity_;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment(R.layout.expenses_fragment)
@OptionsMenu(R.menu.menu_search)
public class ExpensesFragment extends Fragment {

    private ExpensesAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    @ViewById(R.id.list_of_expenses)
    RecyclerView expensesRecycleView;

    @ViewById(R.id.expenses_fab)
    FloatingActionButton floatingActionButton;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @ViewById(R.id.swipe_layout_expenses)
    SwipeRefreshLayout expenseSwipeRefreshLayout;

    @Click(R.id.expenses_fab)
    void fabClicked() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CategoryEntity.selectAll("").size() != 0) {
                    AddExpenseActivity_.intent(getActivity()).start()
                            .withAnimation(R.anim.enter_pull_in, R.anim.exit_fade_out);
                } else Snackbar.make(view, R.string.no_added_category, Snackbar.LENGTH_LONG).show();
            }

        });
    }


    @AfterViews
    void ready() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecycleView.setLayoutManager(linearLayoutManager);

        if (floatingActionButton.isPressed()) {
            fabClicked();
        }
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));

        loadData("");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData("");
        expenseSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light);
        expenseSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData("");
                    }
                }, ConstantManager.DELAY_SWIPETOREFRESH);

            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll("1", true);
                delayedQuery(newText);
                return false;
            }
        });
    }

    @Background(delay = ConstantManager.DELAY, id = ConstantManager.SEARCH_QUERY_ID)
    void delayedQuery(String filter) {
        loadData(filter);
    }

    private void loadData(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<ExpenseEntity>>() {
            @Override
            public Loader<List<ExpenseEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ExpenseEntity>> loader = new AsyncTaskLoader<List<ExpenseEntity>>(getActivity()) {
                    @Override
                    public List<ExpenseEntity> loadInBackground() {
                        return ExpenseEntity.selectAll(filter);
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ExpenseEntity>> loader, List<ExpenseEntity> data) {
                expenseSwipeRefreshLayout.setRefreshing(false);
                adapter = new ExpensesAdapter(data, new ExpensesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {

                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }

                        toggleSelection(position);

                        return true;
                    }
                });
                expensesRecycleView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ExpenseEntity>> loader) {

            }
        });
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }
}
