package com.legacy.legacy_android.feature.screen.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.legacy.legacy_android.feature.network.ruins.RuinsMapRequest
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ruinsMapService: RuinsMapService
): ViewModel() {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun fetchRuinsMap(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        viewModelScope.launch {
            try {
                val request = RuinsMapRequest(minLat, maxLat, minLng, maxLng)
                val response = ruinsMapService.ruinsmap(request)
                Log.d("RuinsMap", "성공: $response")
            } catch (e: Exception) {
                Log.e("RuinsMap", "에러: ${e.message}")
            }
        }
    }
}
