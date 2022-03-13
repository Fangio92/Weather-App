package com.dizdarevic.weatherapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dizdarevic.weatherapp.models.Weather
import com.dizdarevic.weatherapp.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    val weather = MutableLiveData<Weather>()
    val errorMessage = MutableLiveData<String>()

    fun getWeather(lat:Double, lon:Double) {
        val response = repository.getWeather(lat, lon)

        response.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                weather.postValue(response.body())
            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}