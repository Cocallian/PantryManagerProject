package com.example.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.data.PantryItem;
import com.example.myapplication.databinding.FragmentIngredientListBinding;

import java.util.ArrayList;
import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private List<PantryItem> mValues = new ArrayList<>();
    private final OnItemActionListener mListener;

    // Stores who to tell about clicks.
    public MyItemRecyclerViewAdapter(OnItemActionListener listener) {
        mListener = listener;
    }

    // Creates a new row view.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentIngredientListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    // Fills a row with item details.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PantryItem item = mValues.get(position);
        holder.mItem = item;
        holder.mNameView.setText(item.name);

        String quantity = (item.quantity == Math.floor(item.quantity))
                ? String.valueOf((long) item.quantity)
                : String.valueOf(item.quantity);
        holder.mQuantityView.setText(quantity + " " + item.unit);

        holder.mExpiryView.setText(item.expiryDate != null ? item.expiryDate : "-");

        holder.mMenuButton.setOnClickListener(v -> showMenu(v, item));
    }

    // Shows the Edit and Delete popup.
    private void showMenu(View anchor, PantryItem item) {
        PopupMenu popup = new PopupMenu(anchor.getContext(), anchor);
        popup.getMenuInflater().inflate(R.menu.pantry_item_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_edit) {
                mListener.onEdit(item);
                return true;
            } else if (menuItem.getItemId() == R.id.action_delete) {
                mListener.onDelete(item);
                return true;
            }
            return false;
        });
        popup.show();
    }

    // Replaces the list data and refreshes.
    public void setItems(List<PantryItem> item)
    {
        mValues = item;
        notifyDataSetChanged();
    }

    // Returns how many rows there are.
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mQuantityView;
        public final TextView mExpiryView;
        public final View mMenuButton;
        public PantryItem mItem;

        // Finds the views inside a row.
        public ViewHolder(FragmentIngredientListBinding binding) {
            super(binding.getRoot());
            mNameView = binding.itemName;
            mQuantityView = binding.itemQuantity;
            mExpiryView = binding.itemExpiry;
            mMenuButton = binding.itemMenu;
        }

        // Describes the row as text.
        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
