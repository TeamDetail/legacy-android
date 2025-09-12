package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.bars.CustomSearchBar
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative

@Composable
fun RuinSearchModal(
    viewModel: HomeViewModel = hiltViewModel(),
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "유적지 검색", style = AppTextStyles.Heading2.bold, color = Label)
                IconButton(
                    onClick = {
                        viewModel.updateSearchStatus(false)
                    },
                    modifier = Modifier
                        .zIndex(50f)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = Label
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
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
                    if (viewModel.uiState.createSearchRuins == null && !viewModel.uiState.isSearchLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(text = "검색 결과가 없습니다.", style = AppTextStyles.Label.Medium)
                        }
                    }else if (viewModel.uiState.isSearchLoading){
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(text = "검색 중입니다...", style = AppTextStyles.Label.Medium)
                        }
                    }
                    else {
                        viewModel.uiState.createSearchRuins?.forEach { it ->
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "#${it.ruinsId}",
                                    color = Label_Alternative,
                                    style = AppTextStyles.Caption1.Medium
                                )
                                Text(
                                    text = it.name,
                                    color = Label,
                                    style = AppTextStyles.Heading1.bold
                                )
                                Text(
                                    text = it.detailAddress,
                                    color = Label_Alternative,
                                    style = AppTextStyles.Caption2.regular
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}