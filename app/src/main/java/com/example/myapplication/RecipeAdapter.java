package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentRecipeViewBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(int recipeId);
    }

    private List<RecipeMatcher.Result> mValues = new ArrayList<>();
    private final OnRecipeClickListener mListener;

    // Stores who to tell about recipe clicks.
    public RecipeAdapter(OnRecipeClickListener listener) {
        mListener = listener;
    }

    // Creates a new row view.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentRecipeViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // Fills a row with the recipe name and what is missing.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RecipeMatcher.Result result = mValues.get(position);
        holder.mNameView.setText(result.recipe.name);
        holder.itemView.setOnClickListener(v -> mListener.onRecipeClick(result.recipe.id));
        if (result.missing.isEmpty()) {
            holder.mNoteView.setText(R.string.recipes_have_everything);
        } else {
            holder.mNoteView.setText(holder.itemView.getContext()
                    .getString(R.string.recipes_missing, String.join(", ", result.missing)));
        }
    }

    // Replaces the list data and refreshes.
    public void setItems(List<RecipeMatcher.Result> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    // Returns how many rows there are.
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mNoteView;

        // Finds the views inside a row.
        public ViewHolder(FragmentRecipeViewBinding binding) {
            super(binding.getRoot());
            mNameView = binding.recipeName;
            mNoteView = binding.recipeNote;
        }
    }
}
