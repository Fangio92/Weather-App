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
    private val TAG = "MainFragment"
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

        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher. You can use either a val, as shown in this snippet,
        // or a lateinit var in your onAttach() or onCreate() method.
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    loadData()
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                loadData()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.

                //showInContextUI(...)
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
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
                        val list=it.hourly.sortedBy {
                            it.dt
                        }.filterIndexed { index, hourly ->
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