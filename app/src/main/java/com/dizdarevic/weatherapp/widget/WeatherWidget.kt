package com.dizdarevic.weatherapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.dizdarevic.weatherapp.R


class WeatherWidget : AppWidgetProvider() {
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent!!.action)) {
            val thisAppWidget = ComponentName(context!!.packageName, javaClass.name)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
            for (appWidgetID in ids) {
                updateAppWidget(context, appWidgetManager, appWidgetID, intent)
            }
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        intent: Intent
    ) {

        val updateViews = RemoteViews(context.packageName, R.layout.widget_layout)
        updateViews.setTextViewText(R.id.tvWidget, intent.getStringExtra("temp"))
        appWidgetManager.updateAppWidget(appWidgetId, updateViews)
    }
}