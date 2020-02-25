package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.network.JSONInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    public static String SHARED_ELEMENT_TRANSITION_EXTRA = "sharedElementTransition";

    RecipeAdapter mAdapter;
    RecyclerView recyclerView;
    public static final String RECIPE_BUNDLE = "recipeBundle";
    public static final String RECIPE = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.ingredient_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JSONInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONInterface JSONInterface = retrofit.create(JSONInterface.class);
        Call<ArrayList<Recipe>> call = JSONInterface.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                ArrayList<Recipe> recipes = response.body();
                mAdapter.setRecipeData(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClickRecipe(Recipe recipeData, View view) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE, recipeData);
        intent.putExtra(RECIPE_BUNDLE, bundle);


        @SuppressWarnings("unchecked")
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,
                view.findViewById(R.id.recipe_image_view),
                SHARED_ELEMENT_TRANSITION_EXTRA);

        startActivity(intent, optionsCompat.toBundle());


    }
}
