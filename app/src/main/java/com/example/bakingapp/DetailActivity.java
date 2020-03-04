package com.example.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bakingapp.data.Recipe;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import static com.example.bakingapp.MainActivity.RECIPE;

public class DetailActivity extends AppCompatActivity {
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(RECIPE, "");
            recipe = gson.fromJson(json, Recipe.class);
            DetailFragment detailFragment = new DetailFragment(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragment)
                    .commit();
        }
    }
}
