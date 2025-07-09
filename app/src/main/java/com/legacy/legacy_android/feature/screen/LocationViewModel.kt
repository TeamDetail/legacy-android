package com.legacy.legacy_android.feature.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val _locationFlow = MutableStateFlow<LatLng?>(null)
    val locationFlow: StateFlow<LatLng?> = _locationFlow

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let {
                _locationFlow.value = LatLng(it.latitude, it.longitude)
            }
        }
    }

    init {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000
        ).build()

        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
        }
    }

    override fun onCleared() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
