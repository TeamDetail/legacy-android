package com.legacy.legacy_android.feature.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.legacy.legacy_android.feature.data.LocationViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.AdventureInfo
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.ui.theme.Primary

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@SuppressLint("MissingPermission")
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }

    fun getRectanglePoints(
        topLeft: LatLng,
        latPerPixel: Double,
        lonPerPixel: Double,
        strokeWidthLat: Double,
        strokeWidthLng: Double
    ): List<LatLng> {
        return listOf(
            LatLng(topLeft.latitude - strokeWidthLat, topLeft.longitude + strokeWidthLng),
            LatLng(topLeft.latitude - strokeWidthLat, topLeft.longitude + lonPerPixel - strokeWidthLng),
            LatLng(topLeft.latitude - latPerPixel + strokeWidthLat, topLeft.longitude + lonPerPixel - strokeWidthLng),
            LatLng(topLeft.latitude - latPerPixel + strokeWidthLat, topLeft.longitude + strokeWidthLng)
        )
    }

    // 가로
    val latPerPixel = 0.000724
    // 세로
    val lonPerPixel = 0.000909;

    // 가로 경계 두께
    val defaultStrokeWidthToLatitude = 0.00000905;
    // 세로 경계 두께
    val defaultStrokeWidthToLongitude = 0.0000113625;
    val ruins = viewModel.ruinsData
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)


    val cameraPositionState = rememberCameraPositionState()

    val userId = profileViewModel.profile?.userId

    val currentLocation by locationViewModel.locationFlow.collectAsState()

    LaunchedEffect(currentLocation) {
        println("위치 움직임")
    }

    LaunchedEffect(currentLocation, userId) {
        if (currentLocation != null && userId != null) {
            viewModel.fetchBlock(
                latitude = currentLocation?.latitude,
                longitude = currentLocation?.longitude,
                userId = userId
            )
        }
    }

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
            val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
            bounds?.let {
                val southwest = it.southwest
                val northeast = it.northeast

                viewModel.minLat = southwest.latitude
                viewModel.maxLat = northeast.latitude
                viewModel.minLng = southwest.longitude
                viewModel.maxLng = northeast.longitude
            if (cameraPositionState.position.zoom > 13.0f) {
                viewModel.fetchRuinsMap(
                    minLat = viewModel.minLat,
                    maxLat = viewModel.maxLat,
                    minLng = viewModel.minLng,
                    maxLng = viewModel.maxLng
                )
            }
            }
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

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                viewModel.selectedId.value = -1
            },
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionState.status.isGranted,
                minZoomPreference = 10f
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            ),
        ) {
            ruins.forEach { ruin ->
                val topLeft = LatLng(ruin.latitude, ruin.longitude)
                val polygonPoints = getRectanglePoints(
                    topLeft = topLeft,
                    latPerPixel = latPerPixel,
                    lonPerPixel = lonPerPixel,
                    strokeWidthLat = defaultStrokeWidthToLatitude,
                    strokeWidthLng = defaultStrokeWidthToLongitude,
                )

                Polygon(
                    tag=ruin.ruinsId,
                    points = polygonPoints,
                    strokeColor = Primary,
                    strokeWidth = 1f,
                    fillColor = Color(0xFFA980CF).copy(alpha = 0.75f),
                    clickable = true,
                    onClick = {viewModel.selectedId.value = ruin.ruinsId
                    viewModel.fetchRuinsId(ruin.ruinsId)},
                )
            }
        }


        // NavBar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp)
                .zIndex(7f)

        ) {
            NavBar(navHostController = navHostController)
            if (viewModel.selectedId.value > -1) {
                AdventureInfo(
                    name = viewModel.ruinsIdData.value?.name ?: "이름 없음",
                    img = viewModel.ruinsIdData.value?.ruinsImage,
                    loc = LatLng (35.8576, 128.5747),
                    info = viewModel.ruinsIdData.value?.name, // 여기 나중에 설명 들어가야함
                    tags = listOf("IT", "마이스터", "대구", "고등학교")
                )
            }
        }
    }
}