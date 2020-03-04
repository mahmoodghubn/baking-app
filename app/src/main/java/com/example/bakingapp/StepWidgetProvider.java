package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class StepWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,String step,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.step_widget_provider);
        Intent intent = new Intent (context,VideoActivity.class);
        intent.putExtra("widget",true);
        PendingIntent pendingIntent =  PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        views.setTextViewText(R.id.appwidget_text, step);

        Intent StepIntent = new Intent(context, StepService.class);
        StepIntent.setAction(StepService.ACTION_SHOW_STEP);
        PendingIntent StepPendingIntent = PendingIntent.getService(context, 0, StepIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_water_button, StepPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        StepService.startActionShowStep(context);

    }
    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          String step, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, step, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {


        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

