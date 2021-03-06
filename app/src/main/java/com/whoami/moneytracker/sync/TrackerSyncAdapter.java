package com.whoami.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import com.google.gson.Gson;
import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.rest.RestService;
import com.whoami.moneytracker.sync.models.CategoryModel;
import com.whoami.moneytracker.sync.models.ExpensesModel;
import com.whoami.moneytracker.sync.models.UserSyncCategoriesModel;
import com.whoami.moneytracker.sync.models.UserSyncExpensesModel;
import com.whoami.moneytracker.ui.utils.NotifyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    RestService restService;

    private boolean categorySynchronized = false;
    private boolean expensesSynchronized = false;

    TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        syncCategories();
        syncExpenses();

        if(categorySynchronized || expensesSynchronized)
            NotifyUtil.updateNotifications(getContext());

    }

    private static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    private static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        final int SYNC_INTERVAL = 60 * 60 * 24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);
        syncImmediately(context);
    }

    private void syncCategories() {
        categorySynchronized = true;
        RestService restService = new RestService();

        try {
            String googleToken = MoneyManagerApplication.getGoogleAuthToken();
            String token = MoneyManagerApplication.getAuthToken();
            List<CategoryModel> categoriesModelList = new ArrayList<>();

            for (int i = 1; i <= CategoryEntity.selectAll("").size(); i++) {
                CategoryEntity categoryEntity = CategoryEntity.selectById(i);
                String name = categoryEntity.getName();
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(i);
                categoryModel.setTitle(name);

                categoriesModelList.add(categoryModel);
            }

            String data = new Gson().toJson(categoriesModelList);
            UserSyncCategoriesModel categoriesSyncModel = restService.userSyncCategoriesModel(data, token, googleToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void syncExpenses() {
        expensesSynchronized = true;
         NotifyUtil.updateNotifications(getContext());
        RestService restService = new RestService();
        try {
            String googleToken = MoneyManagerApplication.getGoogleAuthToken();
            String token = MoneyManagerApplication.getAuthToken();
            List<ExpensesModel> expensesModelList = new ArrayList<>();

            for (int i = 1; i <= ExpenseEntity.selectAll("").size(); i++) {
                ExpenseEntity expenseEntity = ExpenseEntity.selectById(i);
                String description = expenseEntity.getName();
                String price = expenseEntity.getSum();
                String date = expenseEntity.getDate();

                CategoryEntity categoryEntity = expenseEntity.getCategory();
                int categoryId = (int) (long) categoryEntity.getId();

                ExpensesModel expenseModel = new ExpensesModel();
                expenseModel.setId(i);
                expenseModel.setComment(description);
                expenseModel.setSum(price);
                expenseModel.setTrDate(date);
                expenseModel.setCategoryId(categoryId);

                expensesModelList.add(expenseModel);
            }
            String data = new Gson().toJson(expensesModelList);
            UserSyncExpensesModel expensesSyncModel = restService.userSyncExpensesModel(data, token, googleToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder().syncPeriodic(syncInterval, flexTime).setSyncAdapter(account, authority).setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
