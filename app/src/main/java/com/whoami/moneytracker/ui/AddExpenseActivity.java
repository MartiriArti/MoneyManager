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
    Spinner spinnerCategories;
    @ViewById(R.id.btnCancel)
    Button btnCancel;
    @ViewById(R.id.btnApply)
    Button btnApply;
    
    String string;
    @AfterViews
    void load(){
        date.setText(R.string.date);
    }
    @Click(R.id.btnCancel)
    void btnCancelIsClicked() {
        finish();
    }

    @Click(R.id.btnApply)
    void btnApplyIsClicked() {

        if (((expense.getText().length() > 0) & (date.getText().length()) > 0) & (string.equals(R.string.select_categories))){
            btnApply.setEnabled(true);
            Toast.makeText(getBaseContext(), R.string.apply_is_true, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.add_expense_apply_not_write, Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void spinnerClicked() {
     string = String.valueOf(spinnerCategories.getSelectedItem());
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
    }

}
