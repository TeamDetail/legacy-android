package com.legacy.legacy_android.res.component.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.ui.theme.Background_Netural

@Composable
fun RankingTable(){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .width(400.dp)
            .padding(vertical = 12.dp)
            .background(Background_Netural)
    )
    {
        RankingRowBar(grade = 1, rank = 50, level = 99, title = "자본주의", name = "박재민");
    }
}