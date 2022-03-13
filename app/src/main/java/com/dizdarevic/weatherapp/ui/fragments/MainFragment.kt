package com.dizdarevic.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dizdarevic.weatherapp.databinding.FragmentMainBinding
import com.dizdarevic.weatherapp.models.Weather
import com.dizdarevic.weatherapp.repository.MainRepository
import com.dizdarevic.weatherapp.repository.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    lateinit var binding:FragmentMainBinding
    private val TAG = "MainFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val repository = MainRepository(RetrofitService.getInstance())

        val response = repository.getWeather(44.0, 20.0)


        response.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                setData(response.body())
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                Log.i(TAG, "onFailure: "+t.message)
            }
        })
    }

    private fun setData(body: Weather?) {

    }
}