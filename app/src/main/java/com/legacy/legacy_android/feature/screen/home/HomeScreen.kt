package com.legacy.legacy_android.feature.screen.home

import android.Manifest
import com.legacy.legacy_android.res.component.quiz.QuizBox
import androidx.compose.foundation.background
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.legacy.legacy_android.feature.data.LocationViewModel
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.AdventureInfo
import com.legacy.legacy_android.res.component.adventure.LocationDialog
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.modal.CreditModal
import com.legacy.legacy_android.res.component.modal.QuizModal
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Green_Alternative
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural

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

    val quizList = viewModel.quizIdData.value
    val quizIndex = viewModel.quizStatus.value

    val quizTitle = if (!quizList.isNullOrEmpty() && quizIndex in quizList.indices) {
        quizList[viewModel.quizStatus.value].quizProblem
    } else {
        null
    }


    val mapStyle = remember {
        MapStyleOptions(
            """
        [ 
          {
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#1d2c4d"
              }
            ]
          },
          {
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#8ec3b9"
              }
            ]
          },
          {
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#1a3646"
              }
            ]
          },
          {
            "featureType": "administrative",
            "elementType": "geometry",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "administrative.country",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "color": "#4b6878"
              }
            ]
          },
          {
            "featureType": "administrative.land_parcel",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "administrative.land_parcel",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#64779e"
              }
            ]
          },
          {
            "featureType": "administrative.locality",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "administrative.province",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "color": "#4b6878"
              }
            ]
          },
          {
            "featureType": "landscape.man_made",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "color": "#334e87"
              }
            ]
          },
          {
            "featureType": "landscape.natural",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#023e58"
              }
            ]
          },
          {
            "featureType": "landscape.natural",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "poi",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#283d6a"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "labels.text",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#6f9ba5"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#1d2c4d"
              }
            ]
          },
          {
            "featureType": "poi.park",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "color": "#023e58"
              }
            ]
          },
          {
            "featureType": "poi.park",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#3C7680"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#304a7d"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "labels.icon",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#98a5be"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#1d2c4d"
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#2c6675"
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "color": "#255763"
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#b0d5ce"
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#023e58"
              }
            ]
          },
          {
            "featureType": "road.local",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "transit",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "transit",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#98a5be"
              }
            ]
          },
          {
            "featureType": "transit",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#1d2c4d"
              }
            ]
          },
          {
            "featureType": "transit.line",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "color": "#283d6a"
              }
            ]
          },
          {
            "featureType": "transit.station",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#3a4762"
              }
            ]
          },
          {
            "featureType": "water",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#0e1626"
              }
            ]
          },
          {
            "featureType": "water",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#4e6d70"
              }
            ]
          }
        ]
        """.trimIndent()
        )
    }



    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }

    var showWarning by remember { mutableStateOf(false) }


    val ruins = viewModel.visibleRuins
    val blocks = viewModel.blockData
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)

    val allRequiredPermission = permissionState.allPermissionsGranted

    val cameraPositionState = rememberCameraPositionState()
    val currentLocation by locationViewModel.locationFlow.collectAsState()
    var hasMovedToCurrentLocation by remember { mutableStateOf(false) }

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            locationViewModel.startLocationUpdates()
        } else {
            if (permissionState.shouldShowRationale) {
                showPermissionDialog = true
            } else if (permissionState.revokedPermissions.isNotEmpty()) {
                showPermissionDialog = true
            }
        }
    }

    if (!allRequiredPermission && showPermissionDialog) {
        LocationDialog(permissionState = permissionState) {
            showPermissionDialog = it
        }
    }

    LaunchedEffect(currentLocation) {
        if (allRequiredPermission && currentLocation != null) {
            val loc = currentLocation!!
            viewModel.fetchBlock(loc.latitude, loc.longitude)
            viewModel.fetchGetBlock()
            println("Current location: $currentLocation")
        }
    }

    LaunchedEffect(currentLocation, allRequiredPermission) {
        if (allRequiredPermission && !hasMovedToCurrentLocation && currentLocation != null) {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(currentLocation!!.latitude, currentLocation!!.longitude),
                        14f
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
                if (cameraPositionState.position.zoom >= 14.0f) {
                    showWarning = false
                    viewModel.fetchRuinsMap(
                        viewModel.minLat, viewModel.maxLat,
                        viewModel.minLng, viewModel.maxLng
                    )
                }else{
                    showWarning = true
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
                .absoluteOffset(0.dp, 10.dp)
                .zIndex(5f)
        ) {
            InfoBar(navHostController)
        }

//         QuizBox
        if (viewModel.quizIdData.value != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(color = Color(0xFF2A2B2C).copy(alpha = 0.75f))
                    .zIndex(500f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
            ) {
                QuizBox(
                    data = viewModel.quizIdData.value ?: emptyList(),
                    quizStatus = viewModel.quizStatus,
                    onConfirm = { viewModel.hintStatus.value = HintStatus.CREDIT },
                    viewModel = viewModel,
                    ruinsId = viewModel.ruinsIdData.value?.ruinsId,
                    image = viewModel.ruinsIdData.value?.ruinsImage,
                    name = viewModel.ruinsIdData.value?.name,
                )
                if (viewModel.hintStatus.value == HintStatus.CREDIT) {
                    CreditModal(title = "정말 힌트를 확인하시겠습니까?", credit = 3000, onConfirm = {
                        viewModel.hintStatus.value =
                            HintStatus.HINT
                    }, onDismiss = { viewModel.hintStatus.value = HintStatus.NO })
                } else if (viewModel.hintStatus.value == HintStatus.HINT) {
                    QuizModal(
                        title = quizTitle,
                        questionNumber = viewModel.quizStatus.value + 1,
                        hint = "힌트",
                        onConfirm = { viewModel.hintStatus.value = HintStatus.NO }
                    )
                }
            }
        }

        // 경고 띄우기
        AnimatedVisibility(
            visible = showWarning,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 120.dp)
                .zIndex(50f)
        ) {
            Box(
                modifier = Modifier
                    .background(Red_Netural.copy(alpha = 0.75f), shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "info",
                        tint = Color.White
                    )
                    Text(
                        text = "데이터를 불러오려면\n지도를 더 확대시켜주세요.",
                        style = AppTextStyles.Caption2.Bold,
                        color = Color.White
                    )
                }
            }
        }


        // Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                viewModel.selectedId.value = -1
                viewModel.ruinsIdData = mutableStateOf(null)
            },
            properties = MapProperties(
                isMyLocationEnabled = allRequiredPermission && locationPermissionState.status.isGranted,
                minZoomPreference = 8f,
                mapStyleOptions = mapStyle
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            )
        ) {
            ruins.forEach { ruin ->
                Polygon(
                    zIndex = 55f,
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
                    zIndex = 54f,
                    tag = blocks.blockId,
                    points = PolygonStyle.getPolygonPointsFromLocation(
                        latitude = blocks.latitude,
                        longitude = blocks.longitude
                    ),
                    strokeWidth = 1f,
                    strokeColor = Green_Alternative,
                    fillColor = Color(0xFF07C002).copy(alpha = 0.40f),
                )
            }
        }

        // NavBar + Adventure Info
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp, horizontal = 12.dp)
                .zIndex(7f)
        ) {
            NavBar(navHostController = navHostController)
            if (viewModel.selectedId.value > -1) {
                AdventureInfo(
                    viewModel = viewModel,
                    name = viewModel.ruinsIdData.value?.name ?: "이름 없음",
                    img = viewModel.ruinsIdData.value?.ruinsImage,
                    info = viewModel.ruinsIdData.value?.name,
                    tags = listOf("IT", "마이스터", "대구", "고등학교"),
                    latitude = viewModel.ruinsIdData.value?.longitude,
                    longitude = viewModel.ruinsIdData.value?.latitude,
                    ruinsId = viewModel.ruinsIdData.value?.ruinsId
                )
            }
        }

        // 내 위치 이동아이코ㅓㄴ
        if (viewModel.selectedId.value ==  -1) {
        IconButton(
            onClick = {
                currentLocation?.let {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            16f
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 120.dp)
                .zIndex(8f)
                .size(48.dp)
                .background(Color.White, shape = androidx.compose.foundation.shape.CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "내 위치로 이동",
                tint = Color.Black
            )
        }
        }
    }
}