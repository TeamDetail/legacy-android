package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun RankingTable(
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Background_Netural, shape = RoundedCornerShape(12.dp))
            .offset(0.dp, -144.dp))
    {
        Column (
            modifier = modifier.padding(vertical = 12.dp)
        ){
            RankingRowBar(rank = 4, blocks = 50, level = 99, name = "김건오", title = "팀원들");
            RankingRowBar(rank = 5, blocks = 50, level = 99, name = "김시원", title = "팀원들");
            RankingRowBar(rank = 6, blocks = 50, level = 99, name = "강건", title = "팀원들");
            RankingRowBar(rank = 7, blocks = 50, level = 99, name = "안현우", title = "팀원들");
            RankingRowBar(rank = 8, blocks = 50, level = 99, name = "나르샤 선생님", title = "박재민");
        }
    }
}