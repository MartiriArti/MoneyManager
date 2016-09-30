package com.whoami.moneytracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.adapters.MySpinnerAdapter;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.database.ExpenseEntity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.add_expense_activity)
public class AddExpenseActivity extends AppCompatActivity {

    @ViewById(R.id.expense)
    EditText expense;
    @ViewById(R.id.description)
    EditText description;
    @ViewById(R.id.date)
    EditText date;
    @ViewById(R.id.categories)
    Spinner spinnerCategories;
    @ViewById(R.id.btnCancel)
    Button btnCancel;
    @ViewById(R.id.btnApply)
    Button btnApply;
    @ViewById
    Toolbar toolbar;

    SimpleDateFormat sdf;
    String currentDateandTime;

    @AfterViews
    void load() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Добавить трату");
        }

        sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateandTime = sdf.format(new Date());
        date.setText(currentDateandTime);

        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, getDataList());
        Spinner spinner = (Spinner) findViewById(R.id.categories);
        spinner.setAdapter(mySpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OptionsItem(R.id.home)
    void back() {
        onBackPressed();
        finish();
    }

    @Click(R.id.btnCancel)
    void btnCancelIsClicked() {
        back();
    }

    @Click(R.id.btnApply)
    void btnApplyIsClicked() {
        if ((expense.getText().toString().equals("") & (description.getText().toString().equals("")))) {
            Toast.makeText(getApplicationContext(), R.string.add_expense_apply_not_write, Toast.LENGTH_SHORT).show();
        } else {
            btnApply.setEnabled(true);
            Toast.makeText(getBaseContext(), R.string.apply_is_true, Toast.LENGTH_SHORT).show();
            saveExpense();
            back();
        }
    }

    private void saveExpense() {
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setName(description.getText().toString());
        expenseEntity.setPrice(expense.getText().toString());
        expenseEntity.setDate(currentDateandTime);
        CategoryEntity category = (CategoryEntity) spinnerCategories.getSelectedItem();
        expenseEntity.setCategory(category);
        expenseEntity.save();
        finish();
    }

    private List<CategoryEntity> getDataList() {
        return new Select().from(CategoryEntity.class).execute();
    }


}
