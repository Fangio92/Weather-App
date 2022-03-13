package com.dizdarevic.weatherapp.models

data class Weather(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Int,
    val lon: Int,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)