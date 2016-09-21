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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();
        setDrawerLayout();

        if (savedInstanceState == null) {
            replaceFragment(new ExpensesFragment());
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (f != null) {
                    updateToolbarTitle(f);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        switch (item.getItemId()) {
            case R.id.drawer_expenses:
                ExpensesFragment ef = new ExpensesFragment();
                replaceFragment(ef);
                break;
            case R.id.drawer_categories:
                CategoriesFragment cf = new CategoriesFragment();
                replaceFragment(cf);
                break;
            case R.id.drawer_statistics:
                StatisticsFragment statf = new StatisticsFragment();
                replaceFragment(statf);
                break;
            case R.id.drawer_settings:
                SettingsFragment setf = new SettingsFragment();
                replaceFragment(setf);
                break;
        }
        return true;
    }

    private void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        setTitle(getString(R.string.app_name));
    }



    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);

        if (! fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_container, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();
        }
    }

    private void updateToolbarTitle(Fragment fragment){
        String fragmentClassName = fragment.getClass().getName();

        if (fragmentClassName.equals(ExpensesFragment.class.getName())) {
            setTitle(getString(R.string.nav_drawer_expenses));
            navigationView.setCheckedItem(R.id.drawer_expenses);
        } else if (fragmentClassName.equals(CategoriesFragment.class.getName())) {
            setTitle(getString(R.string.nav_drawer_categories));
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (fragmentClassName.equals(StatisticsFragment.class.getName())) {
            setTitle(getString(R.string.nav_drawer_statistics));
            navigationView.setCheckedItem(R.id.drawer_statistics);
        } else if (fragmentClassName.equals(SettingsFragment.class.getName())) {
            setTitle(getString(R.string.nav_drawer_settings));
            navigationView.setCheckedItem(R.id.drawer_settings);
        }
    }

}