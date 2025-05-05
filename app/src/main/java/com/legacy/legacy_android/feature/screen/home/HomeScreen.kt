package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("MissingPermission")
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMoveScreen: (String) -> Unit = {},
) {
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val currentLocation = viewModel.currentLocation.value

    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
    }

    val cameraPositionState = rememberCameraPositionState {
        if (currentLocation != null) {
            position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted
            ),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
        )
    }
}
