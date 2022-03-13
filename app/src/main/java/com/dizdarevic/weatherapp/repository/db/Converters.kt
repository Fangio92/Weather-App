package com.dizdarevic.weatherapp.repository.db

import androidx.room.TypeConverter
import com.dizdarevic.weatherapp.models.Hourly
import com.dizdarevic.weatherapp.models.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class Converters {
    @TypeConverter
    fun fromStringToList(value: String?): List<Hourly>? {
        val listType = object : TypeToken<List<Hourly?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(diaryLines: List<Hourly?>?): String? {
        val gson = Gson()
        return gson.toJson(diaryLines)
    }
}