package com.legacy.legacy_android.feature.screen.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.ranking.RankingBar
import com.legacy.legacy_android.res.component.ranking.RankingTable
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val statusList = listOf("친구", "전체")

    LaunchedEffect(Unit) {
        viewModel.fetchRanking()
    }

    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "랭킹", image = R.drawable.trophy)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    statusList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.rankingStatus,
                            onClick = { viewModel.changeRankingStatus(index) },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
            }
            // 여기서부터 랭킹바 Wrapper
            Row(
                horizontalArrangement = Arrangement.spacedBy(-6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .offset(0.dp, 40.dp)
                ) {
                    RankingBar(
                        rank = 2,
                        blocks = viewModel.uiState.rankingData?.get(1)?.allBlocks ?: 0,
                        name = viewModel.uiState.rankingData?.get(1)?.nickname ?: "이름 없음",
                        title = viewModel.uiState.rankingData?.get(1)?.title?.name ?: " ",
                        zIndex = 2f
                    )
                }
                RankingBar(
                    rank = 1,
                    blocks = viewModel.uiState.rankingData?.get(0)?.allBlocks ?: 0,
                    name = viewModel.uiState.rankingData?.get(0)?.nickname ?: "이름 없음",
                    title = viewModel.uiState.rankingData?.get(0)?.title?.name ?: " ",
                    zIndex = 3f
                )
                Box(
                    modifier = modifier
                        .offset(0.dp, 50.dp)
                ) {
                    RankingBar(
                        rank = 3,
                        blocks = viewModel.uiState.rankingData?.get(2)?.allBlocks ?: 0,
                        name = viewModel.uiState.rankingData?.get(2)?.nickname ?: "이름 없음",
                        title = viewModel.uiState.rankingData?.get(2)?.title?.name ?: " ",
                        zIndex = 1f
                    )
                }
            }
            Column(modifier.offset(0.dp, -120.dp)){
                RankingTable(
                    modifier,
                    rankingData = viewModel.uiState.rankingData ?: emptyList()
                )
            }
        }
    }
}