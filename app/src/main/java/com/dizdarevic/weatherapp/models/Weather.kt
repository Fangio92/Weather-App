package com.dizdarevic.weatherapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dizdarevic.weatherapp.repository.db.Converters


@Entity
@TypeConverters(Converters::class)

data class Weather(
    @PrimaryKey
    val id: Int = 1,
    @TypeConverters(Converters::class)
    val hourly: List<Hourly>,
    ){
@Ignore
val current: Current?=null
}