package com.dizdarevic.weatherapp.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dizdarevic.weatherapp.R
import com.dizdarevic.weatherapp.databinding.FragmentMainBinding
import com.dizdarevic.weatherapp.repository.MainRepository
import com.dizdarevic.weatherapp.repository.RetrofitService.Companion.getInstance
import com.dizdarevic.weatherapp.ui.adapters.RVWeatherAdapter
import com.dizdarevic.weatherapp.ui.viewmodels.MainViewModel
import com.dizdarevic.weatherapp.ui.viewmodels.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainFragment : Fragment() {
    lateinit var binding:FragmentMainBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val adapter= RVWeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    loadData()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.permit), Toast.LENGTH_LONG).show()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadData()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(requireContext(), getString(R.string.permit), Toast.LENGTH_LONG).show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun loadData() {
        binding.rvWeather.adapter = adapter
        binding.rvWeather.layoutManager= LinearLayoutManager(requireContext())
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let {
                    val viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(requireContext(), MainRepository(getInstance()))).get(
                        MainViewModel::class.java)
                    viewModel.getWeather(it.latitude, it.latitude)
                    viewModel.weather.observe(viewLifecycleOwner, Observer {
                        val list=it?.hourly?.sortedBy {
                            it.dt
                        }?.filterIndexed { index, hourly ->
                            index<25
                        }
                        adapter.setWeather(list)
                    })

                    viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    })
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}