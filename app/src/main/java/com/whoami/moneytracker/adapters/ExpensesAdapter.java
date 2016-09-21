package com.whoami.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.models.Expense;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>{

    private List<Expense> expenseList;

    public ExpensesAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpensesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.description.setText(expense.description);
        holder.price.setText(expense.price);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpensesHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public TextView price;

        public ExpensesHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.expense_description);
            price = (TextView) itemView.findViewById(R.id.expense_price);
        }
    }
}