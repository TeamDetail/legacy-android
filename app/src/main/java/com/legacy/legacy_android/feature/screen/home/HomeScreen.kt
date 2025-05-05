package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.android.gms.maps.CameraUpdateFactory
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.legacy.legacy_android.feature.data.getMyLocation
import com.legacy.legacy_android.res.component.bars.InfoBar
import com.legacy.legacy_android.res.component.bars.NavBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("MissingPermission")
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMoveScreen: (String) -> Unit = {},
) {
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    val currentLocation = getMyLocation().value
    println(currentLocation)

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(currentLocation) {
        if (currentLocation != null) {
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(currentLocation, 25f)
            )
            cameraPositionState.animate(cameraUpdate)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .offset(y = 50.dp)
            .zIndex(5f)) {
            InfoBar(
                name = "박재민",
                level = 99,
                money = 6506246420,
                isTabClicked = viewModel.getTabClicked(),
                setTabClicked = { viewModel.setTabClicked() }
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false ),
        )
        Box(
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .zIndex(7f)
        ) {
            NavBar()
        }
    }
}

