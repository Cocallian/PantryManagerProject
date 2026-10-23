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

    public RecipeAdapter(OnRecipeClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentRecipeViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

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

    public void setItems(List<RecipeMatcher.Result> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mNoteView;

        public ViewHolder(FragmentRecipeViewBinding binding) {
            super(binding.getRoot());
            mNameView = binding.recipeName;
            mNoteView = binding.recipeNote;
        }
    }
}
