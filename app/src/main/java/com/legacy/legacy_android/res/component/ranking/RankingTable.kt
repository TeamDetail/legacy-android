package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.network.rank.RankingResponse
import com.legacy.legacy_android.feature.screen.ranking.RankingViewModel
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun RankingTable(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
    rankingData: List<RankingResponse> = emptyList()
) {
    if (rankingData.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Background_Netural, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            rankingData.forEachIndexed { index, item ->
                if (index > 2) {
                    RankingRowBar(
                        rank = index + 1,
                        blocks = if(viewModel.uiState.rankingStatus == 0) item.allBlocks else item.level,
                        level = item.level,
                        name = item.nickname.ifEmpty { "이름 없음" },
                        title = item.title,
                        img = item.imageUrl,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
