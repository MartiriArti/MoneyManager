package com.whoami.moneytracker.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.database.CategoryEntity;
import com.whoami.moneytracker.ui.utils.SelectableAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CategoriesAdapter extends SelectableAdapter<CategoriesAdapter.CardViewHolder> {

    List<CategoryEntity> categories;

    private CardViewHolder.ClickListener clickListener;

    public CategoriesAdapter(List<CategoryEntity> categories, CardViewHolder.ClickListener clickListener) {

        this.clickListener = clickListener;

        this.categories = categories;
    }

    @Override
    public CategoriesAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CardViewHolder(convertView, clickListener);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.categories_name.setText(category.name);

        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    private void removeItem(int position) {
        removeCategories(position);
        notifyItemRemoved(position);
    }

    private void removeCategories(int position) {
        if (categories.get(position) != null) {
            categories.get(position).delete();
            categories.remove(position);

        }
    }

    public void removeItems(List<Integer> positions) {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                for (int i = 0; i <= positions.size(); i++) {
                    removeItem(positions.get(0));
                    positions.remove(0);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        protected TextView categories_name;

        protected View selectedOverlay;

        private ClickListener clickListener;

        public CardViewHolder(View convertView, ClickListener clickListener) {
            super(convertView);
            categories_name = (TextView) convertView.findViewById(R.id.category_name);

            selectedOverlay = itemView.findViewById(R.id.selected_overlay);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return clickListener != null && clickListener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }

}