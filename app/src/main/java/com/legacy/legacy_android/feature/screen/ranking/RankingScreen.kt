package com.legacy.legacy_android.feature.screen.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import com.legacy.legacy_android.ui.theme.Red_Netural

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val statusList = listOf("탐험", "숙련")
    val friendList = listOf("전체", "친구")

    LaunchedEffect(Unit) {
        viewModel.fetchRanking()
    }

    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
    ) {
        TitleBox(title = "랭킹", image = R.drawable.trophy)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    friendList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.friendStatus,
                            onClick = { viewModel.changeFriendStatus(index) },
                            text = item,
                            id = index,
                            selectedColor = Red_Netural,
                            nonSelectedColor = Line_Netural
                        )
                    }
                }
            }
            // 여기서부터 랭킹바 Wrapper
            Row(
                horizontalArrangement = Arrangement.spacedBy((-6).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val rank1 = viewModel.uiState.rankingData?.getOrNull(0)
                val rank2 = viewModel.uiState.rankingData?.getOrNull(1)
                val rank3 = viewModel.uiState.rankingData?.getOrNull(2)

                Box(
                    modifier = modifier.offset(0.dp, 40.dp)
                ) {
                    RankingBar(
                        rank = 2,
                        blocks = if(viewModel.uiState.rankingStatus == 0) rank2?.allBlocks ?: 0 else rank2?.level,
                        name = rank2?.nickname ?: "이름 없음",
                        title = rank2?.title,
                        zIndex = 2f,
                        img = rank2?.imageUrl,
                        viewModel = viewModel
                    )
                }
                RankingBar(
                    rank = 1,
                    blocks = if(viewModel.uiState.rankingStatus == 0) rank1?.allBlocks ?: 0 else rank1?.level,
                    name = rank1?.nickname ?: "이름 없음",
                    title = rank1?.title,
                    zIndex = 3f,
                    img = rank1?.imageUrl,
                    viewModel = viewModel
                )

                Box(
                    modifier = modifier.offset(0.dp, 50.dp)
                ) {
                    RankingBar(
                        rank = 3,
                        blocks = if(viewModel.uiState.rankingStatus == 0) rank3?.allBlocks ?: 0 else rank3?.level,
                        name = rank3?.nickname ?: "이름 없음",
                        zIndex = 1f,
                        title = rank3?.title,
                        img = rank3?.imageUrl,
                        viewModel = viewModel
                    )
                }
            }
            Column(modifier.offset(0.dp, (-120).dp)) {
                RankingTable(
                    modifier,
                    rankingData = viewModel.uiState.rankingData ?: emptyList()
                )
            }
        }
    }
}