package com.example.bakingapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bakingapp.data.Recipe;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context context;
    private ArrayList<Recipe> mRecipesData = new ArrayList<>();

    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClickRecipe(Recipe recipeDate, View view);
    }

    RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mRecipeImageView;

        RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeImageView = view.findViewById(R.id.recipe_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe oneRecipe = mRecipesData.get(adapterPosition);
            mClickHandler.onClickRecipe(oneRecipe, v);
        }
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeAdapterViewHolder filmAdapterViewHolder, final int position) {
        String recipeName = mRecipesData.get(position).getName().toLowerCase().replace(" ", "");
        filmAdapterViewHolder.mRecipeImageView.setImageResource(getStringIdentifier(context, recipeName));

    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipesData) return 0;
        return mRecipesData.size();
    }

    void setRecipeData(ArrayList<Recipe> data) {

        if (data == null) {
            mRecipesData.clear();
        } else {
            mRecipesData = data;
        }
        notifyDataSetChanged();
    }

}
