package com.whoami.moneytracker.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.rest.RestService;
import com.whoami.moneytracker.sync.TrackerSyncAdapter;
import com.whoami.moneytracker.ui.fragments.CategoriesFragment;
import com.whoami.moneytracker.ui.fragments.CategoriesFragment_;
import com.whoami.moneytracker.ui.fragments.ExpensesFragment;
import com.whoami.moneytracker.ui.fragments.ExpensesFragment_;
import com.whoami.moneytracker.ui.fragments.SettingsFragment;
import com.whoami.moneytracker.ui.fragments.StatisticsFragment;
import com.whoami.moneytracker.ui.fragments.StatisticsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

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

    TextView nameText;
    TextView emailText;
    RestService restService = new RestService();
    Bundle saveInst;
    ImageView avatar;

    public static final String TAG = "myLog";

    @AfterViews
    void preLoad() {
        setActionBar();
        setDrawerLayout();
           }

    private void setDrawerLayout() {
        onNavigationItemSelected();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer
                , toolbar
                , R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        if (toolbarTitle != null)
            setTitle(toolbarTitle);

        View headerView = navigationView.getHeaderView(0);//тут получаем картинку в хедере навигейшн вью с позицией ноль - перва картнка, у нас одна, считается от нуля
        final ImageView avatar = (ImageView) headerView.findViewById(R.id.header_avatar);//нашли по айди
        String url = MoneyManagerApplication.getGoogleAvatar();

        Glide.with(this).load(url).asBitmap().into(new BitmapImageViewTarget(avatar) {
            @Override
            protected void setResource(Bitmap resource) {
                Context context = getApplicationContext();
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                avatar.setImageDrawable(circularBitmapDrawable);}
        });

        TextView email = (TextView) headerView.findViewById(R.id.header_email);
        email.setText(MoneyManagerApplication.getUserEmile());
        TextView name = (TextView) headerView.findViewById(R.id.header_username);
        name.setText(MoneyManagerApplication.getUserName());
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveInst = savedInstanceState;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View headerView = navigationView.getHeaderView(0);
        avatar = (ImageView) headerView.findViewById(R.id.header_avatar);
        nameText = (TextView) headerView.findViewById(R.id.header_username);
        emailText = (TextView) headerView.findViewById(R.id.header_email);

        getParam();

        if (savedInstanceState == null) {
            replaceFragment(new ExpensesFragment_());
            setTitle(getString(R.string.nav_drawer_expenses));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (f != null) {
                    updateToolbarTitle(f);
                } else finish();
            }
        });

        TrackerSyncAdapter.initializeSyncAdapter(this);
    }


    public void getParam() {
        if (!MoneyManagerApplication.getGoogleAuthToken().equals("")) {
            nameText.setText(MoneyManagerApplication.getUserName());
            emailText.setText(MoneyManagerApplication.getUserEmile());
        }
    }


    private void addCategory(String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryEntity.save();
    }

    private void replaceFragment(Fragment fragment) {
        String backStackName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_container, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void updateToolbarTitle(Fragment fragment) {
        String fragmentClassName = fragment.getClass().getName();
        if (fragmentClassName.equals(ExpensesFragment_.class.getName())) {
            setTitle(expensesTitle);
            navigationView.setCheckedItem(R.id.drawer_expenses);
        } else if (fragmentClassName.equals(CategoriesFragment_.class.getName())) {
            setTitle(categoriesTitle);
            navigationView.setCheckedItem(R.id.drawer_categories);
        } else if (fragmentClassName.equals(StatisticsFragment_.class.getName())) {
            setTitle(statisticsTitle);
            navigationView.setCheckedItem(R.id.drawer_statistics);
        } else if (fragmentClassName.equals(SettingsFragment.class.getName())) {
            setTitle(settingsTitle);
            navigationView.setCheckedItem(R.id.drawer_settings);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void onNavigationItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (drawer != null) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.drawer_expenses:
                        replaceFragment(new ExpensesFragment_());
                        break;
                    case R.id.drawer_categories:
                        replaceFragment(new CategoriesFragment_());
                        break;
                    case R.id.drawer_statistics:
                        replaceFragment(new StatisticsFragment_());
                        break;
                    case R.id.drawer_settings:
                        replaceFragment(new SettingsFragment());
                        break;
                    case R.id.drawer_exit:
                        exit();
                        break;
                }
                return true;
            }
        });

    }

    private void exit() {
        MoneyManagerApplication.saveAuthToken("");
        MoneyManagerApplication.saveGoogleAuthToken("");
        Intent intent = new Intent(this, RegistrationActivity_.class);
        startActivity(intent);
        finish();
    }

}


