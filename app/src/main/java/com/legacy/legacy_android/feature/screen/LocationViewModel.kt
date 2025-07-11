package com.legacy.legacy_android.feature.data

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

@HiltViewModel
class LocationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val _locationFlow = MutableStateFlow<LatLng?>(null)
    val locationFlow: StateFlow<LatLng?> = _locationFlow.asStateFlow()

    private val _isLocationPermissionGranted = MutableStateFlow(false)
    val isLocationPermissionGranted: StateFlow<Boolean> = _isLocationPermissionGranted.asStateFlow()

    private val _isLocationServiceRunning = MutableStateFlow(false)
    val isLocationServiceRunning: StateFlow<Boolean> = _isLocationServiceRunning.asStateFlow()

    private var locationCallback: LocationCallback? = null

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        3000L
    ).apply {
        setMinUpdateIntervalMillis(1000L)
        setMaxUpdateDelayMillis(10000L)
        setWaitForAccurateLocation(false)
    }.build()

    init {
        checkLocationPermission()
        if (_isLocationPermissionGranted.value) {
            startLocationUpdates()
        }
    }

    /**
     * 위치 권한 확인
     */
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
                    _locationFlow.value = LatLng(location.latitude, location.longitude)
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

        } catch (e: SecurityException) {
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
        } catch (e: SecurityException) {
            // 권한이 없는 경우
            _isLocationPermissionGranted.value = false
        }
    }
    fun updatePermissionStatus() {
        val wasGranted = _isLocationPermissionGranted.value
        checkLocationPermission()

        // 권한이 새로 승인된 경우 위치 서비스 시작
        if (!wasGranted && _isLocationPermissionGranted.value) {
            startLocationUpdates()
        }
        // 권한이 거부된 경우 위치 서비스 중지
        else if (wasGranted && !_isLocationPermissionGranted.value) {
            stopLocationUpdates()
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
        } catch (e: SecurityException) {
            _isLocationPermissionGranted.value = false
            callback(null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }
}