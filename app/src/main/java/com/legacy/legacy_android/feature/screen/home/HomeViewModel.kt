package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application){
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    private val _currentLocation = mutableStateOf<LatLng?>(null)
    private val _isTabClicked = mutableStateOf<Boolean>(false)
    val currentLocation: State<LatLng?> = _currentLocation

    fun setTabClicked(): Boolean {
        _isTabClicked.value = !_isTabClicked.value
        return _isTabClicked.value
    }

    fun getTabClicked(): Boolean{
        return _isTabClicked.value
    }

    init {
        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location -> location?.let {
                _currentLocation.value = LatLng(it.latitude, it.longitude)
        }}
    }
}