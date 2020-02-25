package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.bakingapp.data.Recipe;

import static com.example.bakingapp.MainActivity.RECIPE;
import static com.example.bakingapp.MainActivity.RECIPE_BUNDLE;

public class VideoActivity extends AppCompatActivity {
    Recipe recipe;
    int position;
    String stepDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (savedInstanceState == null) {
            stepDescription = getIntent().getStringExtra("discretion");
            position = getIntent().getIntExtra("position", 0);
            getIntentExtras();
            VideoFragment videoFragment = new VideoFragment(position, recipe,stepDescription);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.video_fragment_container, videoFragment).commit();
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
