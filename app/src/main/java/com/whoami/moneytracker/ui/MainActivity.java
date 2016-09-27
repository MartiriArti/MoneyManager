package com.whoami.moneytracker.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.ui.fragments.CategoriesFragment;
import com.whoami.moneytracker.ui.fragments.ExpensesFragment;
import com.whoami.moneytracker.ui.fragments.SettingsFragment;
import com.whoami.moneytracker.ui.fragments.StatisticsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    @StringRes(R.string.nav_drawer_expenses)
    String expensesTitle;

    @StringRes(R.string.nav_drawer_categories)
    String categoriesTitle;

    @StringRes(R.string.nav_drawer_statistics)
    String statisticsTitle;

    @StringRes(R.string.nav_drawer_settings)
    String settingsTitle;

    @InstanceState
    String toolbarTitle;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ExpensesFragment());
        }

    }


    @AfterViews
    void setupViews() {
        setActionBar();
        setDrawerLayout();
        setFragmentManager();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onBackStackChanged() {
        Fragment f = fragmentManager
                .findFragmentById(R.id.main_container);

        if (f != null) {
            setToolbarTitle(f.getClass().getName());
        }
    }

    private void onNavigationItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.drawer_expenses:
                        replaceFragment(new ExpensesFragment());
                        break;
                    case R.id.drawer_categories:
                        replaceFragment(new CategoriesFragment());
                        break;
                    case R.id.drawer_statistics:
                        replaceFragment(new StatisticsFragment());
                        break;
                    case R.id.drawer_settings:
                        replaceFragment(new SettingsFragment());
                        break;
                    case R.id.drawer_exit:
                        break;
                }
                return true;
            }
        });
    }

    private void setToolbarTitle(String backStackEntryName) {
        if (backStackEntryName.equals(ExpensesFragment.class.getName())) {
            setTitle(expensesTitle);
            toolbarTitle = expensesTitle;
            navigationView.setCheckedItem(R.id.drawer_expenses);
        } else if (backStackEntryName.equals(CategoriesFragment.class.getName())) {
            setTitle(categoriesTitle);
            toolbarTitle = categoriesTitle;
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (backStackEntryName.equals(SettingsFragment.class.getName())) {
            setTitle(settingsTitle);
            toolbarTitle = settingsTitle;
            navigationView.setCheckedItem(R.id.drawer_settings);
        } else {
            setTitle(statisticsTitle);
            toolbarTitle = statisticsTitle;
            navigationView.setCheckedItem(R.id.drawer_statistics);
        }
    }

    private void setDrawerLayout() {
        onNavigationItemSelected();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout
                , toolbar
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        if (toolbarTitle != null)
            setTitle(toolbarTitle);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        boolean isFragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        if (!isFragmentPopped && fragmentManager.findFragmentByTag(backStackName) == null) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, backStackName);
            transaction.addToBackStack(backStackName);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

        }
    }

    private void setFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
    }

}
