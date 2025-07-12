package com.legacy.legacy_android.feature.screen.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
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
import com.legacy.legacy_android.ui.theme.Line_Natural
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "랭킹", image = R.drawable.trophy)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = modifier
                    .fillMaxWidth()
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.friendmode.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.friendStatus,
                            onClick = { viewModel.friendStatus = index },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Natural
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(30.dp))
            // 여기서부터 랭킹바 Wrapper
            Row(
                horizontalArrangement = Arrangement.spacedBy(-6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = modifier
                        .offset(0.dp, 40.dp)
                ) {
                    RankingBar(grade = 2, rank = 999, name = "김은찬", title = "자본주의", zIndex = 2f)
                }
                RankingBar(grade = 1, rank = 999, name = "김은찬", title = "자본주의", zIndex = 3f)
                Box(
                    modifier = modifier
                        .offset(0.dp, 50.dp)
                ) {
                    RankingBar(grade = 3, rank = 999, name = "김은찬", title = "자본주의", zIndex = 1f)
                }
            }
            RankingTable(modifier = modifier)
        }
    }
}