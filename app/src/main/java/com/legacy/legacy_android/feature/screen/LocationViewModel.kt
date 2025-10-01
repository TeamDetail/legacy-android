package com.legacy.legacy_android.feature.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class LocationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val _locationFlow = MutableStateFlow<LatLng?>(null)
    val locationFlow: StateFlow<LatLng?> = _locationFlow.asStateFlow()

    private val _isLocationPermissionGranted = MutableStateFlow(false)

    private val _isLocationServiceRunning = MutableStateFlow(false)

    private var locationCallback: LocationCallback? = null

    private val locationRequest = LocationRequest.create().apply {
        interval = 15000L
        fastestInterval = 10000L
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 30000L
    }

    init {
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        _isLocationPermissionGranted.value = hasFineLocationPermission || hasCoarseLocationPermission
    }

    fun startLocationUpdates() {
        checkLocationPermission()

        if (!_isLocationPermissionGranted.value) {
            return
        }

        if (_isLocationServiceRunning.value) {
            return
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    val newLocation = LatLng(location.latitude, location.longitude)
                    _locationFlow.value = newLocation
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                super.onLocationAvailability(locationAvailability)
                if (!locationAvailability.isLocationAvailable) {
                    _isLocationServiceRunning.value = false
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback!!,
                Looper.getMainLooper()
            )
            _isLocationServiceRunning.value = true
            getLastKnownLocation()

        } catch (_: SecurityException) {
            _isLocationPermissionGranted.value = false
            _isLocationServiceRunning.value = false
        }
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
            locationCallback = null
        }
        _isLocationServiceRunning.value = false
    }

    private fun getLastKnownLocation() {
        if (!_isLocationPermissionGranted.value) {
            return
        }

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    _locationFlow.value = LatLng(it.latitude, it.longitude)
                }
            }
        } catch (_: SecurityException) {
            _isLocationPermissionGranted.value = false
        }
    }

    fun getCurrentLocation(callback: (LatLng?) -> Unit) {
        checkLocationPermission()

        if (!_isLocationPermissionGranted.value) {
            callback(null)
            return
        }

        try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location: Location? ->
                location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    _locationFlow.value = latLng
                    callback(latLng)
                } ?: callback(null)
            }.addOnFailureListener {
                callback(null)
            }
        } catch (_: SecurityException) {
            _isLocationPermissionGranted.value = false
            callback(null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }
}