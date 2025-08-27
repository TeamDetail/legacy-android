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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.google.maps.android.compose.*
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.screen.LocationViewModel
import com.legacy.legacy_android.feature.screen.home.model.HintStatus
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.AdventureInfo
import com.legacy.legacy_android.res.component.adventure.LocationDialog
import com.legacy.legacy_android.res.component.adventure.MapStyle
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBarViewModel
import com.legacy.legacy_android.res.component.modal.CreditModal
import com.legacy.legacy_android.res.component.modal.QuizModal
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Green_Alternative
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.White
import kotlinx.coroutines.delay

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
    var mapLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }

    var showWarning by remember { mutableStateOf(false) }

    val ruins = viewModel.uiState.visibleRuins
    val blocks = viewModel.uiState.blocks
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

    val cameraPositionState = rememberSaveable(saver = CameraPositionState.Saver) {
        CameraPositionState(
            position = viewModel.cameraPosition
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cameraPosition = cameraPositionState.position
        }
    }

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
            viewModel.createBlock(loc.latitude, loc.longitude)
            viewModel.loadBlocks()
            println("Current location: $currentLocation")
        }
    }

    LaunchedEffect( currentLocation, allRequiredPermission) {
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
            viewModel.cameraPosition = cameraPositionState.position
            cameraPositionState.projection?.visibleRegion?.latLngBounds?.let { bounds ->
                viewModel.updateMapBounds(
                    minLat = bounds.southwest.latitude,
                    maxLat = bounds.northeast.latitude,
                    minLng = bounds.southwest.longitude,
                    maxLng = bounds.northeast.longitude
                )
                if (cameraPositionState.position.zoom >= 13.5f) {
                    showWarning = false
                    viewModel.loadRuinsMap(
                        viewModel.uiState.mapBounds.minLat,
                        viewModel.uiState.mapBounds.maxLat,
                        viewModel.uiState.mapBounds.minLng,
                        viewModel.uiState.mapBounds.maxLng
                    )
                }else{
                    showWarning = true
                }
            }
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .zIndex(99f)) {
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
        if (!mapLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1500f)
                    .background(Black.copy(alpha = 0.75f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var scale by remember { mutableStateOf(1f) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            scale = 1.2f
                            delay(300)
                            scale = 1f
                            delay(300)
                        }
                    }
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .scale(scale)
                                    .background(Primary, shape = CircleShape)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "지도를 불러오는 중...",
                        color = Label,
                        style = AppTextStyles.Caption1.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
        if (viewModel.uiState.quizStatus != QuizStatus.NONE) {
            // quizbox의 맨 뒷 배경
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
                    data = viewModel.uiState.quizData ?: emptyList(),
                    quizStatus = viewModel.uiState.quizStatus,
                    onConfirm = { viewModel.updateHintStatus(HintStatus.CREDIT) },
                    viewModel = viewModel,
                    ruinsId = viewModel.uiState.ruinsDetail?.ruinsId,
                    image = viewModel.uiState.ruinsDetail?.ruinsImage,
                    name = viewModel.uiState.ruinsDetail?.name,
                )
                if (viewModel.uiState.hintStatus == HintStatus.CREDIT) {
                    CreditModal(title = "정말 힌트를 확인하시겠습니까?", credit = 3000, onConfirm = {
                        viewModel.updateHintStatus(HintStatus.HINT)
                    }, onDismiss = { viewModel.updateHintStatus(HintStatus.NO)})
                } else if (viewModel.uiState.hintStatus == HintStatus.HINT) {
                    QuizModal(
                        title = "",
                        hint = "힌트",
                        onConfirm = { viewModel.updateHintStatus(HintStatus.NO)}
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
            onMapLoaded = {
                mapLoaded = true
            },
            onMapClick = {
                viewModel.updateSelectedId(-1)
                viewModel.fetchRuinsDetail(-1)
            },
            properties = MapProperties(
                isMyLocationEnabled = allRequiredPermission && locationPermissionState.status.isGranted,
                minZoomPreference = 8f,
                mapStyleOptions = MapStyle().mapStyle
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false,
                compassEnabled = false
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
                        viewModel.updateSelectedId(ruin.ruinsId)
                        viewModel.fetchRuinsDetail(ruin.ruinsId)
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
            if (viewModel.uiState.selectedId > -1) {
                AdventureInfo(
                    id = viewModel.uiState.ruinsDetail?.ruinsId,
                    viewModel = viewModel,
                    name = viewModel.uiState.ruinsDetail?.name ?: "이름 없음",
                    img = viewModel.uiState.ruinsDetail?.ruinsImage,
                    info = viewModel.uiState.ruinsDetail?.detailAddress,
                    tags = listOf("IT", "마이스터", "대구", "고등학교"),
                    ruinsId = viewModel.uiState.ruinsDetail?.ruinsId
                )
            }
        }

        // 내 위치 이동아이코ㅓㄴ
        if (viewModel.uiState.selectedId == -1) {
        IconButton(
            onClick = {
                currentLocation?.let {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            13.5f
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 120.dp)
                .zIndex(8f)
                .size(48.dp)
                .background(Black, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "내 위치로 이동",
                tint = White
            )
        }
        }
    }
}