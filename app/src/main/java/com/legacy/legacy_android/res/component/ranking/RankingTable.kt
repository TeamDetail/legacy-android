package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.network.rank.RankingResponse
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun RankingTable(
    modifier: Modifier,
    rankingData: List<RankingResponse>
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Background_Netural, shape = RoundedCornerShape(12.dp))
    )
            {

        Column (
            modifier = modifier.padding(vertical = 12.dp)
        ) {
            rankingData.forEachIndexed { index, item ->
                if (index > 2) {
                    RankingRowBar(
                        rank = index,
                        blocks = item.allBlocks,
                        level = item.level,
                        name = item.nickname,
                        title = item.title.name
                    )
                }
            }
        }
    }
}