package com.example.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.data.PantryItem;
import com.example.myapplication.databinding.FragmentIngredientListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PantryItem}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<PantryItem> mValues = new ArrayList<>();

    public MyItemRecyclerViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentIngredientListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PantryItem item = mValues.get(position);
        holder.mNameView.setText(item.name);

        // Show whole numbers without ".0" (2.0 -> "2", 0.5 -> "0.5")
        String quantity = (item.quantity == Math.floor(item.quantity))
                ? String.valueOf((long) item.quantity)
                : String.valueOf(item.quantity);
        holder.mQuantityView.setText(quantity + " " + item.unit);

        holder.mExpiryView.setText(item.expiryDate != null ? item.expiryDate : "-");
    }

    public void setItems(List<PantryItem> item)
    {
        mValues = item;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mQuantityView;
        public final TextView mExpiryView;
        public PantryItem mItem;

        public ViewHolder(FragmentIngredientListBinding binding) {
            super(binding.getRoot());
            mNameView = binding.itemName;
            mQuantityView = binding.itemQuantity;
            mExpiryView = binding.itemExpiry;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}