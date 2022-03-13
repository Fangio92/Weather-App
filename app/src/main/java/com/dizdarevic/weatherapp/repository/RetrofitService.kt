package com.dizdarevic.weatherapp.repository

import com.dizdarevic.weatherapp.Constants.API
import com.dizdarevic.weatherapp.models.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {
    @GET("onecall?lat={lat}&lon={lon}&appid=b5f4716fc3368b6b42a9cbc6360911ce&units=metric")
    fun getUsers(@Path("lat") lat:Double, @Path("lon") lon:Double): Call<Weather>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}