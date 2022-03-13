package com.dizdarevic.weatherapp.repository.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dizdarevic.weatherapp.models.Weather

@Database(entities = [Weather::class],
    version = 1, exportSchema = true)
@TypeConverters(Converters::class)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun WeatherDAO():WeatherDAO

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WeatherDatabase::class.java, "database.db")
            .build()

    }
}