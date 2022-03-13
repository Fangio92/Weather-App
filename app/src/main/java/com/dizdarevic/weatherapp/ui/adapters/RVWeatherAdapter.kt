package com.dizdarevic.weatherapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dizdarevic.weatherapp.databinding.AdapterWeatherBinding
import com.dizdarevic.weatherapp.models.Hourly


class RVWeatherAdapter:RecyclerView.Adapter<MainViewHolder>() {
    private var hourly: List<Hourly>?=null

    private val TAG = "RVWeatherAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterWeatherBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val hour= hourly?.get(position)

        holder.binding.tvHour.text= hour?.getHour()
        holder.binding.tvHumidity.text=hour?.humidity.toString()
        holder.binding.tvWind.text=hour?.wind_speed.toString()
        holder.binding.tvTemp.text=hour?.temp.toString()

        Log.i(TAG, "onBindViewHolder: "+hour?.dt)
    }

    override fun getItemCount(): Int {
        return hourly?.size?: 0
    }

    fun setWeather(weather: List<Hourly>) {
        this.hourly=weather
        notifyDataSetChanged()
    }

}
class MainViewHolder(val binding: AdapterWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

}