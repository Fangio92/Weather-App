package com.dizdarevic.weatherapp.repository

import com.dizdarevic.weatherapp.models.Weather
import retrofit2.Call

class MainRepository(
    private val retrofitService: RetrofitService
) {
    fun getWeather(lat:Double, lon:Double): Call<Weather> {
        return retrofitService.getUsers(lat,lon)
    }
}