package com.whoami.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.ExpenseEntity;
import com.whoami.moneytracker.ui.utils.SelectableAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.CardViewHolder> {

    List<ExpenseEntity> expenses;

    private CardViewHolder.ClickListener clickListener;

    public ExpensesAdapter(List<ExpenseEntity> expenses, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;

        this.expenses = expenses;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        ExpenseEntity expense = expenses.get(position);
        holder.name_text.setText(expense.name);
        holder.sum_text.setText(expense.price);

        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }

    public void removeItems(List<Integer> positions) {

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            removeItem(positions.get(0));
            positions.remove(0);
        }

    }

    public void removeItem(int position) {
        if (expenses.get(position) != null) {
            expenses.get(position).delete();
            expenses.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        protected TextView name_text;
        protected TextView sum_text;

        protected View selectedOverlay;

        private ClickListener clickListener;

        public CardViewHolder(View convertView, ClickListener clickListener) {
            super(convertView);
            name_text = (TextView) convertView.findViewById(R.id.expense_description);
            sum_text =  (TextView) convertView.findViewById(R.id.expense_price);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(clickListener != null)
            {
                clickListener.onItemClicked(getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View v) {
            return clickListener != null && clickListener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener
        {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }
}