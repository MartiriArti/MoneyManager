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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    RestService restService;

    TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        syncCategories();
        syncExpenses();
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
        final int SYNC_INTERVAL = 20;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);
        syncImmediately(context);
    }

    private void syncCategories() {

        restService = new RestService();
        List<CategoryEntity> categoryEntityList = CategoryEntity.selectAll("");
        List<CategoryModel> categoryModelsList = new ArrayList<>();

        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryEntity categoryEntity = CategoryEntity.selectById(i);
            CategoryModel categoryModel = new CategoryModel();
 //           String ctaName = categoryEntity.getName();
            categoryModel.setId(i);
  //          categoryModel.setTitle(ctaName);
            categoryModelsList.add(categoryModel);
        }
        String gson = new Gson().toJson(categoryModelsList);

        try {
            UserSyncCategoriesModel userSyncCategoriesModel = restService.userSyncCategoriesModel(gson,
                    MoneyManagerApplication.getAuthToken(),
                    MoneyManagerApplication.getGoogleAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void syncExpenses() {
        restService = new RestService();
        List<ExpenseEntity> expensesEntityList = ExpenseEntity.selectAll("");
        List<ExpensesModel> expensesModelsList = new ArrayList<>();
        for (int i = 0; i < expensesEntityList.size(); i++) {
            ExpenseEntity expenseEntity = ExpenseEntity.selectById(i);
//            String sum = expenseEntity.getSum();
 //           String description = expenseEntity.getName();
 //           String date = expenseEntity.getDate();
//
 //           ExpensesModel expensesModel = new ExpensesModel();
 //           expensesModel.setId(i);
  //          expensesModel.setSum(sum);
  //          expensesModel.setComment(description);
 //           expensesModel.setTrDate(date);
            CategoryEntity categoryEntity = expenseEntity.getCategory();
            int categoryId = (int) (long) categoryEntity.getId();
 //           expensesModel.setCategoryId(categoryId);
//            expensesModelsList.add(expensesModel);
        }
        String gson = new Gson().toJson(expensesModelsList);
        try {
            UserSyncExpensesModel userSyncExpensesModel = restService.userSyncExpensesModel(gson,
                    MoneyManagerApplication.getAuthToken(),
                    MoneyManagerApplication.getGoogleAuthToken());
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
