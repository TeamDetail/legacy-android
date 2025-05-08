package com.legacy.legacy_android.feature.screen.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.ranking.RankingBar
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Line_Natural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background_Alternative)
    ) {
        InfoBar()
        Column(
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = Modifier
                    .height(70.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            TitleBox(title = "랭킹", image = R.drawable.trophy)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.gamemode.forEachIndexed { index, item ->
                            StatusButton(
                                selectedValue = viewModel.gameStatus,
                                onClick = { viewModel.gameStatus = index },
                                text = item,
                                id = index,
                                selectedColor = Primary,
                                nonSelectedColor = Line_Natural
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.friendmode.forEachIndexed { index, item ->
                            StatusButton(
                                selectedValue = viewModel.friendStatus,
                                onClick = { viewModel.friendStatus = index },
                                text = item,
                                id = index,
                                selectedColor = Red_Normal,
                                nonSelectedColor = Line_Natural
                            )
                        }
                    }
                }
                // 여기서부터 랭킹바 Wrapper
                Row {
                    RankingBar(rank = 1, stair = 999, name = "김은찬", title = "자본주의")
                    RankingBar(rank = 2, stair = 999, name = "김은찬", title = "자본주의")
                    RankingBar(rank = 3, stair = 999, name = "김은찬", title = "자본주의")
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .zIndex(7f)
        ) {
            NavBar(navHostController = navHostController)
        }
    }
}
