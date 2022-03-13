package com.dizdarevic.weatherapp.models

import java.text.SimpleDateFormat
import java.util.*

data class Hourly(
    val dt: Long,
    val humidity: Int,
    val temp: Double,
    val wind_speed: Double
){

    fun getHour():String{
        val timeD = Date(dt*1000)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(timeD)
    }
}