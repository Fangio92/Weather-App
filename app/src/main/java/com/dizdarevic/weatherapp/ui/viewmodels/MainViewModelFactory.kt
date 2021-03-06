package com.dizdarevic.weatherapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dizdarevic.weatherapp.repository.MainRepository

class MainViewModelFactory(
    private val context: Context,
    private val repository: MainRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(context, this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}