package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
@SuppressLint("MissingPermission")
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMoveScreen: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val latLngState = remember { mutableStateOf<LatLng?>(null) }

    // 위치 가져오기
    LaunchedEffect(Unit) {
        val location = getMyLocation(context)
        if (location != null) {
            latLngState.value = location
        }
    }

    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    // 카메라 위치 상태 기억하기
    val cameraPositionState = rememberCameraPositionState()

    // 위치가 업데이트되면 카메라 이동
    LaunchedEffect(latLngState.value) {
        latLngState.value?.let { latLng ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            properties = properties
        )
    }
}
