package com.dizdarevic.weatherapp.models

import java.text.SimpleDateFormat
import java.util.*

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<WeatherXXX>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
){

    fun getHour():String{
        val timeD = Date(dt*1000)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(timeD)
    }
}