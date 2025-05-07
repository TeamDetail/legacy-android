package com.legacy.legacy_android.feature.data

import android.Manifest
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun getMyLocation(): State<LatLng?> {
    val context = LocalContext.current
    //외부에서 주입받아 쓰셈 직접 생성하면 오류발생 가능성 30000%
    val fusedLocationClient by remember {
        mutableStateOf(
            LocationServices.getFusedLocationProviderClient(context)
        )
    }

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationState = remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    locationState.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    return locationState
}
