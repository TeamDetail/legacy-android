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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.legacy.legacy_android.feature.screen.LocationViewModel
import com.legacy.legacy_android.feature.screen.home.model.HintStatus
import com.legacy.legacy_android.feature.screen.home.model.QuizStatus
import com.legacy.legacy_android.feature.screen.profile.ProfileViewModel
import com.legacy.legacy_android.res.component.adventure.AdventureInfo
import com.legacy.legacy_android.res.component.adventure.CommentModal
import com.legacy.legacy_android.res.component.adventure.LocationDialog
import com.legacy.legacy_android.res.component.adventure.MapStyle
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.modal.CreditModal
import com.legacy.legacy_android.res.component.modal.mail.MailModal
import com.legacy.legacy_android.res.component.modal.QuizModal
import com.legacy.legacy_android.res.component.modal.RateModal
import com.legacy.legacy_android.res.component.modal.RuinSearchModal
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Green_Alternative
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import kotlinx.coroutines.delay


val RUIN_STROKE_COLOR = Primary
val RUIN_FILL_COLOR = Color(0xFFA980CF).copy(alpha = 0.75f)
val NORMAL_BLOCK_STROKE_COLOR = Green_Alternative
val NORMAL_BLOCK_FILL_COLOR = Color(0xFF07C002).copy(alpha = 0.4f)
val SPECIAL_BLOCK_STROKE_COLOR = Yellow_Netural
val SPECIAL_BLOCK_FILL_COLOR = Color(0xFFEDB900)

object PolygonCache {
    private val cache = mutableMapOf<String, List<LatLng>>()

    fun getPoints(lat: Double, lng: Double): List<LatLng> {
        val key = "${lat}_${lng}"
        return cache.getOrPut(key) {
            PolygonStyle.getPolygonPointsFromLocation(lat, lng)
        }
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    return kotlin.math.sqrt(
        (lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)
    )
}

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

    val mapLoaded = viewModel.isMapLoaded
    LaunchedEffect(Unit) {
        profileViewModel.fetchProfile()
    }
    var showWarning by remember { mutableStateOf(false) }

    val ruins = viewModel.uiState.visibleRuins
    val blocks = viewModel.uiState.blocks
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var showPermissionDialog by remember { mutableStateOf(false) }

    val permissions = remember {
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)

    val allRequiredPermission = permissionState.allPermissionsGranted

    val cameraPositionState = rememberCameraPositionState {
        position = viewModel.cameraPosition
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
        }
    }

    LaunchedEffect(currentLocation, allRequiredPermission) {
        if (allRequiredPermission && !hasMovedToCurrentLocation && currentLocation != null) {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(currentLocation!!.latitude, currentLocation!!.longitude),
                        18f
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
                if (cameraPositionState.position.zoom >= 14f) {
                    showWarning = false
                    viewModel.loadRuinsMap(
                        viewModel.uiState.mapBounds.minLat,
                        viewModel.uiState.mapBounds.maxLat,
                        viewModel.uiState.mapBounds.minLng,
                        viewModel.uiState.mapBounds.maxLng
                    )
                } else {
                    showWarning = true
                }
            }
        }
    }

    LaunchedEffect(ruins) {
        val overlapping = mutableListOf<Pair<Int, Int>>()

        ruins.forEachIndexed { i, ruin1 ->
            ruins.forEachIndexed { j, ruin2 ->
                if (i < j) {
                    val distance = calculateDistance(
                        ruin1.latitude, ruin1.longitude,
                        ruin2.latitude, ruin2.longitude
                    )

                    if (distance < 0.001) {
                        overlapping.add(Pair(ruin1.ruinsId, ruin2.ruinsId))
                        println("겹침 발견: ${ruin1.ruinsId} <-> ${ruin2.ruinsId}, 거리: ${(distance * 111000).toInt()}m")
                    }
                }
            }
        }

        if (overlapping.isNotEmpty()) {
            println("총 ${overlapping.size}개의 겹침 발견")
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (viewModel.uiState.isMailOpen) {
            MailModal(onMailClick = { show -> viewModel.updateIsMailOpen(false) })
        }
        if (viewModel.uiState.isCommentModalOpen) {
            RateModal(viewModel)
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .absoluteOffset(0.dp, 10.dp)
                .zIndex(5f)
        ) {
            InfoBar(
                navHostController,
                onMailClick = { show -> viewModel.updateIsMailOpen(true) })
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
                    var scale by remember { mutableFloatStateOf(1f) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            scale = 1.2f
                            delay(300)
                            scale = 1f
                            delay(300)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
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
                    viewModel = viewModel,
                    image = viewModel.uiState.selectedRuinsDetail?.ruinsImage,
                    name = viewModel.uiState.selectedRuinsDetail?.name,
                )
                if (viewModel.uiState.hintStatus == HintStatus.CREDIT) {
                    CreditModal(title = "정말 힌트를 확인하시겠습니까?", credit = 300, onConfirm = {
                        viewModel.loadHint(quizId = viewModel.uiState.quizData!![0].quizId)
                    }, onDismiss = { viewModel.updateHintStatus(HintStatus.NO) })
                } else if (viewModel.uiState.hintStatus == HintStatus.HINT) {
                    QuizModal(
                        title = "",
                        hint = "힌트",
                        onConfirm = { viewModel.updateHintStatus(HintStatus.NO) }
                    )
                }
            }
        }

        IconButton(
            onClick = {
                currentLocation?.let {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            18f
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(50f)
                .padding(start = 12.dp, top = 120.dp)
                .size(48.dp)
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "내 위치로 이동",
                tint = White
            )
        }

        IconButton(
            onClick = {
                viewModel.updateSearchStatus(true)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(50f)
                .padding(start = 72.dp, top = 120.dp)
                .size(48.dp)
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "내 위치로 이동",
                tint = White
            )
        }

        AnimatedVisibility(
            visible = showWarning,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 180.dp)
                .zIndex(50f)
        ) {
            Box(
                modifier = Modifier
                    .background(Red_Netural.copy(alpha = 0.75f), shape = RoundedCornerShape(12.dp))
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

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = { viewModel.setMapLoaded() },
            onMapClick = {
                viewModel.updateSelectedId(emptyList())
                viewModel.fetchRuinsDetail(emptyList())
                viewModel.initRuinsDetail()
                viewModel.updateIsCommenting(false)
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
            val zoom = cameraPositionState.position.zoom
            if (zoom >= 14f) {
                val mapBounds = viewModel.uiState.mapBounds

                val visibleRuins = remember(ruins, mapBounds) {
                    ruins.filter { ruin ->
                        ruin.latitude in mapBounds.minLat..mapBounds.maxLat &&
                                ruin.longitude in mapBounds.minLng..mapBounds.maxLng
                    }
                }

                val groupedRuins = remember(visibleRuins) {
                    visibleRuins.groupBy { "${it.latitude}_${it.longitude}" }
                }

                val visibleBlocks = remember(blocks, mapBounds) {
                    blocks.filter { block ->
                        block.latitude in mapBounds.minLat..mapBounds.maxLat &&
                                block.longitude in mapBounds.minLng..mapBounds.maxLng
                    }
                }

                groupedRuins.forEach { (location, ruinsAtLocation) ->
                    key(location) {
                        val mainRuin = ruinsAtLocation.first()
                        Polygon(
                            zIndex = 55f,
                            tag = mainRuin.ruinsId,
                            points = remember(mainRuin.ruinsId) {
                                PolygonCache.getPoints(mainRuin.latitude, mainRuin.longitude)
                            },
                            strokeColor = RUIN_STROKE_COLOR,
                            strokeWidth = if (ruinsAtLocation.size > 1) 2f else 1f,
                            fillColor = RUIN_FILL_COLOR,
                            clickable = true,
                            onClick = {
                                viewModel.initRuinsDetail()
                                val ids = ruinsAtLocation.map { it.ruinsId }
                                viewModel.updateSelectedId(ids)
                                viewModel.fetchRuinsDetail(ids)
                            }
                        )
                    }
                }

                visibleBlocks.forEachIndexed { index, block ->
                    key(block.blockId) {
                        Polygon(
                            zIndex = (56f + index * 0.01f),
                            tag = block.blockId,
                            points = remember(block.blockId) {
                                PolygonCache.getPoints(block.latitude, block.longitude)
                            },
                            strokeWidth = 1f,
                            strokeColor = if (block.blockType == "NORMAL") NORMAL_BLOCK_STROKE_COLOR else SPECIAL_BLOCK_STROKE_COLOR,
                            fillColor = if (block.blockType == "NORMAL") NORMAL_BLOCK_FILL_COLOR else SPECIAL_BLOCK_FILL_COLOR
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp, horizontal = 12.dp)
                .zIndex(7f)
        ) {
            if (viewModel.uiState.selectedId.isNotEmpty() && !viewModel.uiState.isCommenting) {
                AdventureInfo(
                    data = viewModel.uiState.ruinsDetail,
                    viewModel
                )
            } else if (viewModel.uiState.selectedId.isNotEmpty() && viewModel.uiState.isCommenting) {
                CommentModal(viewModel)
            } else {
                NavBar(navHostController = navHostController)
            }
        }

        if (viewModel.uiState.isSearchRuinOpen) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF2A2B2C).copy(alpha = 0.75f))
                    .zIndex(500f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
            ) {
                RuinSearchModal(viewModel, cameraPositionState)
            }
        }
    }
}