package com.whoami.moneytracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.whoami.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.add_expense_activity)
public class AddExpenseActivity extends AppCompatActivity {

    @ViewById(R.id.expense)
    EditText expense;
    @ViewById(R.id.description)
    EditText description;
    @ViewById(R.id.date)
    EditText date;
    @ViewById(R.id.categories)
    Spinner categories;
    @ViewById(R.id.btnCancel)
    Button btnCancel;
    @ViewById(R.id.btnApply)
    Button btnApply;

    @Click(R.id.btnCancel)
    void btnCancelIsClicked() {
        finish();
    }

    @Click(R.id.btnApply)
    void btnApplyIsClicked() {

        if ((expense.getText().length() & date.getText().length()) > 0) {
            btnApply.setEnabled(true);
            Toast.makeText(getBaseContext(), "Apply is Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void spinnerClicked() {
        categories.setPrompt("Выберите категорию");
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);
    }
}
