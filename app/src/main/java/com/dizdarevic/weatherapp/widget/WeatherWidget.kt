package com.dizdarevic.weatherapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.dizdarevic.weatherapp.R


class WeatherWidget : AppWidgetProvider() {

    val INTENT_STR="com.dizdarevic.weatherapp.APPWIDGET_UPDATE"


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (INTENT_STR.equals(intent!!.action)) {
            val thisAppWidget = ComponentName(context!!.packageName, javaClass.name)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
            for (appWidgetID in ids) {
                updateAppWidget(context, appWidgetManager, appWidgetID)
            }
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val updateViews = RemoteViews(context.packageName, R.layout.widget_layout)
        updateViews.setTextViewText(R.id.tvWidget, "5")
        appWidgetManager.updateAppWidget(appWidgetId, updateViews)
    }
}