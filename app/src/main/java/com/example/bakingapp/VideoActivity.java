package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bakingapp.data.Recipe;
import com.google.gson.Gson;

import static com.example.bakingapp.DetailFragment.STEP;
import static com.example.bakingapp.MainActivity.RECIPE;

public class VideoActivity extends AppCompatActivity {
    Recipe recipe;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (savedInstanceState == null) {
            SharedPreferences sharedPreferences;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(RECIPE, "");
            recipe = gson.fromJson(json, Recipe.class);
            boolean widget = getIntent().getBooleanExtra("widget",false);
            if (!widget) {
                position = getIntent().getIntExtra("position", 0);
            }else {
                position = sharedPreferences.getInt(STEP,0);
                if (position!=0)
                    position--;
            }
            VideoFragment videoFragment = new VideoFragment(position, recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.video_fragment_container, videoFragment).commit();
        }
    }
}
