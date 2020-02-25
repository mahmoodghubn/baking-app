package com.example.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.bakingapp.data.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import static com.example.bakingapp.MainActivity.RECIPE;
import static com.example.bakingapp.MainActivity.RECIPE_BUNDLE;

public class DetailActivity extends AppCompatActivity {
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getIntentExtras();
            DetailFragment detailFragment = new DetailFragment(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }

    }

    private void getIntentExtras() {
        if (getIntent().getExtras() != null) {
            Bundle ingredientsBundle = getIntent().getExtras().getBundle(RECIPE_BUNDLE);
            if (ingredientsBundle != null) {
                Parcelable ingredientsParcelableArray = ingredientsBundle.getParcelable(RECIPE);
                if (ingredientsParcelableArray != null) {
                    recipe = (Recipe) ingredientsParcelableArray;
                }
            }
        }
    }


}
