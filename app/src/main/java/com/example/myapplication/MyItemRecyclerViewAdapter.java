package com.example.myapplication;

import android.content.res.ColorStateList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.data.PantryItem;
import com.example.myapplication.databinding.FragmentIngredientListBinding;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private static final int EXPIRY_WARNING_DAYS = 7;

    private List<PantryItem> mValues = new ArrayList<>();
    private boolean highlightExpiring = true;
    private String expiryCutoff = "";
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

        boolean expiringSoon = highlightExpiring && item.expiryDate != null
                && item.expiryDate.compareTo(expiryCutoff) <= 0;
        if (expiringSoon) {
            holder.mCard.setCardBackgroundColor(
                    ContextCompat.getColor(holder.mCard.getContext(), R.color.expiry_warning_background));
        } else {
            holder.mCard.setCardBackgroundColor(holder.mDefaultCardColor);
        }

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

    // Turns the red highlight for items close to expiry on or off.
    public void setHighlightExpiring(boolean highlight) {
        highlightExpiring = highlight;
    }

    // Replaces the list data and refreshes.
    public void setItems(List<PantryItem> item)
    {
        mValues = item;
        Calendar cutoff = Calendar.getInstance();
        cutoff.add(Calendar.DAY_OF_YEAR, EXPIRY_WARNING_DAYS);
        expiryCutoff = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cutoff.getTime());
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
        public final MaterialCardView mCard;
        public final ColorStateList mDefaultCardColor;
        public PantryItem mItem;

        // Finds the views inside a row.
        public ViewHolder(FragmentIngredientListBinding binding) {
            super(binding.getRoot());
            mNameView = binding.itemName;
            mQuantityView = binding.itemQuantity;
            mExpiryView = binding.itemExpiry;
            mMenuButton = binding.itemMenu;
            mCard = binding.getRoot();
            mDefaultCardColor = mCard.getCardBackgroundColor();
        }

        // Describes the row as text.
        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
