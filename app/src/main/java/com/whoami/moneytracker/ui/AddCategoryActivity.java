package com.whoami.moneytracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.add_category_activity)
public class AddCategoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @ViewById(R.id.category_name)
    EditText categoryName;
    @ViewById(R.id.btnApply)
    Button addCategoryBtn;
    @ViewById(R.id.btnCancel)
    Button cancelBtn;

    @OptionsItem(R.id.home)
    void back() {
        onBackPressed();
        finish();
    }

    private void addCategory() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName.getText().toString());
        categoryEntity.save();
    }


    @Click(R.id.btnCancel)
    void btnCancelIsClicked() {
        back();
    }

    @Click(R.id.btnApply)
    void btnApplyIsClicked() {
        if (categoryName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.add_expense_apply_not_write, Toast.LENGTH_SHORT).show();
        } else {
            addCategoryBtn.setEnabled(true);
            Toast.makeText(getBaseContext(), R.string.apply_is_true, Toast.LENGTH_SHORT).show();
            addCategory();
            back();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
