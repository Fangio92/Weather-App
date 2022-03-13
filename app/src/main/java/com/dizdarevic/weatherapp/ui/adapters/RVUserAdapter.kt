package com.dizdarevic.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dizdarevic.weatherapp.R
import com.dizdarevic.weatherapp.databinding.AdapterUserBinding
import com.dizdarevic.weatherapp.models.Weather


class RVUserAdapter:RecyclerView.Adapter<MainViewHolder>() {
    private var userList: List<Weather>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterUserBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        
    }

    override fun getItemCount(): Int {

        return 0
    }

}
class MainViewHolder(val binding: AdapterUserBinding) : RecyclerView.ViewHolder(binding.root) {

}