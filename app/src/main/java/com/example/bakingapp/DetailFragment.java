package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;

import static com.example.bakingapp.MainActivity.RECIPE;
import static com.example.bakingapp.MainActivity.RECIPE_BUNDLE;
import static com.example.bakingapp.MainActivity.SHARED_ELEMENT_TRANSITION_EXTRA;

public class DetailFragment extends Fragment implements IngredientAdapter.IngredientAdapterOnClickHandler, StepAdapter.StepAdapterOnClickHandler {
    private Recipe recipe;
    ImageView recipeImage;
    Toolbar toolbar;

    public DetailFragment() {

    }

    DetailFragment(Recipe recipe) {
        this.recipe = recipe;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Load the saved state (the list of images and list index) if there is one
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable("recipe");
        }

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        recipeImage = rootView.findViewById(R.id.recipe_image);
        String recipeName = recipe.getName().toLowerCase().replace(" ", "");
        recipeImage.setImageResource(getStringIdentifier(getContext(), recipeName));
        ViewCompat.setTransitionName(recipeImage, SHARED_ELEMENT_TRANSITION_EXTRA);

        toolbar = rootView.findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(recipe.getName());
        }
        IngredientAdapter ingredientAdapter;
        RecyclerView ingredientRecyclerView;
        RecyclerView.LayoutManager ingredientLayoutManager;

        ingredientRecyclerView = rootView.findViewById(R.id.ingredient_recycler_view);
        ingredientLayoutManager = new LinearLayoutManager(getContext());
        ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientAdapter = new IngredientAdapter(this);
        ingredientAdapter.setIngredientData(recipe.getIngredients());
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        StepAdapter stepAdapter;
        RecyclerView stepRecyclerView;
        RecyclerView.LayoutManager stepLayoutManager;

        stepRecyclerView = rootView.findViewById(R.id.step_recycler_view);
        stepLayoutManager = new LinearLayoutManager(getContext());
        stepRecyclerView.setLayoutManager(stepLayoutManager);
        stepRecyclerView.setHasFixedSize(true);
        stepAdapter = new StepAdapter(this);
        stepAdapter.setStepData(recipe.getSteps());
        stepRecyclerView.setAdapter(stepAdapter);
        return rootView;
    }

    @Override
    public void onClickIngredient(Ingredient recipeData) {

    }

    @Override
    public void onClickStep(Step stepData, int stepPosition) {
        if (stepData.getVideoURL().equals("")) {
            Toast.makeText(getContext(), "no video", Toast.LENGTH_LONG).show();

        } else {
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra("discretion", stepData.getRecipeDescription());
            intent.putExtra("position", stepPosition);
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE, recipe);
            intent.putExtra(RECIPE_BUNDLE, bundle);
            DetailFragment.this.startActivity(intent);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable("recipe", recipe);
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
