package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.bars.CustomSearchBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun RuinSearchModal(
    viewModel: HomeViewModel = hiltViewModel(),
    cameraPositionState: CameraPositionState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Background_Normal, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.9f)
            .zIndex(999f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "유적지 검색", style = AppTextStyles.Heading2.bold, color = Label)
                IconButton(
                    onClick = { viewModel.updateSearchStatus(false) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = Label
                    )
                }
            }

            CustomSearchBar(
                placeholder = "유적지 이름으로 검색..",
                modifier = Modifier,
                query = viewModel.uiState.searchRuinValue,
                onSearch = { viewModel.searchRuins(viewModel.uiState.searchRuinValue.value) }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                val ruins = viewModel.uiState.createSearchRuins
                if (ruins.isNullOrEmpty() && !viewModel.uiState.isSearchLoading) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("검색 결과가 없습니다.", style = AppTextStyles.Label.Medium)
                    }
                } else if (viewModel.uiState.isSearchLoading) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("검색 중입니다...", style = AppTextStyles.Label.Medium)
                    }
                } else {
                    ruins?.forEach { ruin ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                viewModel.fetchRuinsDetail(ruin.ruinsId)
                                viewModel.updateSelectedId(ruin.ruinsId)
                                viewModel.updateSearchStatus(false)

                                val latLng = LatLng(ruin.latitude, ruin.longitude)
                                cameraPositionState.move(
                                    CameraUpdateFactory.newLatLngZoom(latLng, 14f)
                                )
                                viewModel.setMapLoaded()
                            }
                        ) {
                            Text("#${ruin.ruinsId}", color = Label_Alternative)
                            Text(ruin.name, color = Label)
                            Text(ruin.detailAddress, color = Label_Alternative)
                        }
                    }
                }
            }
        }
    }
}