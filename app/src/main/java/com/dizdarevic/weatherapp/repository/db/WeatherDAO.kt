package com.dizdarevic.weatherapp.repository.db

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dizdarevic.weatherapp.models.Weather

@Dao
interface WeatherDAO {
    @Query("SELECT * FROM Weather")
    fun get(): Weather

    @Insert(onConflict = REPLACE)
    fun insert(weather: Weather)
}