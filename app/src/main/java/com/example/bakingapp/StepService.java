package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.bakingapp.data.Recipe;
import com.google.gson.Gson;

import static com.example.bakingapp.DetailFragment.STEP;
import static com.example.bakingapp.MainActivity.RECIPE;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class StepService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_SHOW_STEP = "com.example.bakingapp.action.SHOW_STEP";

    public StepService() {
        super("StepService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionShowStep(Context context) {
        Intent intent = new Intent(context, StepService.class);
        intent.setAction(ACTION_SHOW_STEP);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_STEP.equals(action)) {
                handleActionShowStep();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionShowStep() {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(RECIPE, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        int stepPosition = sharedPreferences.getInt(STEP, 0);
        if (stepPosition < recipe.getSteps().size()) {
            String step = recipe.getSteps().get(stepPosition).getRecipeDescription();
            if (stepPosition < recipe.getSteps().size()) {
                editor.putInt(STEP, ++stepPosition);
                editor.commit();
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StepWidgetProvider.class));
            StepWidgetProvider.updatePlantWidgets(this, appWidgetManager, step, appWidgetIds);
        }
    }
}
