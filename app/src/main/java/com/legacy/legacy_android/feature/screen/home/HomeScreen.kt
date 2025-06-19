package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.legacy.legacy_android.feature.data.getMyLocation
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.ui.theme.Primary

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("MissingPermission")
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    val currentLocation = getMyLocation(viewModel.fusedLocationClient).value
    println(currentLocation)

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        16f
                    )
                )
            )
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val center = cameraPositionState.position.target
            val zoom = cameraPositionState.position.zoom

            val delta = 0.01 / zoom
            val minLat = center.latitude - delta
            val maxLat = center.latitude + delta
            val minLng = center.longitude - delta
            val maxLng = center.longitude + delta
            print("이거 ${minLng}, ${maxLat}  ${minLng}  ${maxLng}")

            viewModel.fetchRuinsMap(minLat, maxLat, minLng, maxLng)
        }
    }


    Box(modifier = modifier
        .fillMaxSize()
        .zIndex(99f)) {
        // InfoBar
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .absoluteOffset(0.dp, 30.dp)
                .zIndex(5f)) {
            InfoBar(navHostController)
        }

        // QuizBox
//        Box(
//            contentAlignment = Alignment.Center,
//                    modifier = modifier
//                .fillMaxWidth()
//                .fillMaxSize()
//                .background(color = Color(0xFF2A2B2C).copy(alpha = 0.7f))
//                .zIndex(500f)
//        ){
//            QuizBox(name = "대구소프트웨어마이스트고등학겨")
//        }
        // 구글맵
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
                minZoomPreference = 10f
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            )
        ) {
//            Polygon(
//                points = listOf(
//                    LatLng(35.8771, 128.6034),
//                    LatLng(35.8771, 128.6234),
//                    LatLng(35.8591, 128.6234),
//                    LatLng(35.8591, 128.6034),
//                ),
//                strokeColor = Primary,
//                strokeWidth = 1f,
//                fillColor = Color(0xFFA980CF).copy(alpha = 0.75f),
//            )
        }


        // NavBar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp)
                .zIndex(7f)

        ) {
            NavBar(navHostController = navHostController)
//            AdventureInfo(name = "대구소프트웨어마이스터고등학교", loc = LatLng (35.8576, 128.5747), info = "대구소프트웨어마이스터고등학교는 세상을 이롭게 하는 개발자 육성을 위한 학교입니다.", tags = listOf("IT", "마이스터", "대구", "고등학교"))
        }
    }
}

