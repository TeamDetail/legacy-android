package com.legacy.legacy_android.feature.screen.home

import com.legacy.legacy_android.res.component.quiz.QuizBox
import androidx.compose.foundation.background
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
import com.google.maps.android.compose.*
import com.legacy.legacy_android.feature.data.LocationViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.AdventureInfo
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.ui.theme.Green_Alternative
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

    val ruins = viewModel.ruinsData
    val blocks = viewModel.blockData
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPositionState = rememberCameraPositionState()
    val userId = profileViewModel.profile?.userId
    val currentLocation by locationViewModel.locationFlow.collectAsState()
    var hasMovedToCurrentLocation by remember { mutableStateOf(false) }

    // 위치 및 데이터 요청
    LaunchedEffect(currentLocation) {
        currentLocation?.let { loc ->
            if (userId != null) {
                viewModel.fetchBlock(loc.latitude, loc.longitude, userId)
                viewModel.fetchGetBlock(userId)
                println(currentLocation)
                println(userId)
            }
        }
    }

    LaunchedEffect(currentLocation) {
        if (!hasMovedToCurrentLocation && currentLocation != null) {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(currentLocation!!.latitude, currentLocation!!.longitude),
                        16f
                    )
                )
            )
            hasMovedToCurrentLocation = true
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            cameraPositionState.projection?.visibleRegion?.latLngBounds?.let { bounds ->
                viewModel.minLat = bounds.southwest.latitude
                viewModel.maxLat = bounds.northeast.latitude
                viewModel.minLng = bounds.southwest.longitude
                viewModel.maxLng = bounds.northeast.longitude
                if (cameraPositionState.position.zoom > 13.0f) {
                    viewModel.fetchRuinsMap(
                        viewModel.minLat, viewModel.maxLat,
                        viewModel.minLng, viewModel.maxLng
                    )
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().zIndex(99f)) {
        // InfoBar
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .absoluteOffset(0.dp, 30.dp)
                .zIndex(5f)
        ) {
            InfoBar(navHostController)
        }
        // QuizBox
        // Box(
        //     contentAlignment = Alignment.Center,
        //     modifier = modifier
        //         .fillMaxWidth()
        //         .fillMaxSize()
        //         .background(color = Color(0xFF2A2B2C).copy(alpha = 0.7f))
        //         .zIndex(500f)
        // ){
        //     QuizBox(name = "대구소프트웨어마이스트고등학겨")
        // }

        // Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { viewModel.selectedId.value = -1 },
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
                Polygon(
                    tag = ruin.ruinsId,
                    points = PolygonStyle.getPolygonPointsFromLocation(
                        latitude = ruin.latitude,
                        longitude = ruin.longitude
                    ),
                    strokeColor = Primary,
                    strokeWidth = 1f,
                    fillColor = Color(0xFFA980CF).copy(alpha = 0.75f),
                    clickable = true,
                    onClick = {
                        viewModel.selectedId.value = ruin.ruinsId
                        viewModel.fetchRuinsId(ruin.ruinsId)
                    }
                )
            }
            blocks.forEach { blocks ->
                Polygon(
                    tag = blocks.blockId,
                    points = PolygonStyle.getPolygonPointsFromLocation(
                        latitude = blocks.latitude,
                        longitude = blocks.longitude
                    ),
                    strokeWidth = 1f,
                    strokeColor = Green_Alternative,
                    fillColor = Color(0xFF07C002).copy(alpha = 0.75f),
                )
            }
        }

        // NavBar + Adventure Info
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
                    info = viewModel.ruinsIdData.value?.name,
                    tags = listOf("IT", "마이스터", "대구", "고등학교"),
                    latitude = viewModel.ruinsIdData.value?.longitude,
                    longitude = viewModel.ruinsIdData.value?.latitude
                )
            }
        }
    }
}
