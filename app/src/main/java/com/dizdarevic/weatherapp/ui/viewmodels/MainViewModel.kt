package com.dizdarevic.weatherapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dizdarevic.weatherapp.models.Weather
import com.dizdarevic.weatherapp.repository.MainRepository
import com.dizdarevic.weatherapp.repository.db.WeatherDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MainViewModel constructor(private val context: Context, private val repository: MainRepository) : ViewModel() {
    val weather = MutableLiveData<Weather>()
    val errorMessage = MutableLiveData<String>()

    fun getWeather(lat:Double, lon:Double) {
        val response = repository.getWeather(lat, lon)

        response.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                response.body()?.let {
                    weather.postValue(it)
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            val weatherDAO = WeatherDatabase(context).WeatherDAO()
                            weatherDAO.insert(it)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                errorMessage.postValue(t.message)
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val weatherDAO = WeatherDatabase(context).WeatherDAO()
                        weather.postValue(weatherDAO.get())
                    }
                }
            }
        })
    }

}