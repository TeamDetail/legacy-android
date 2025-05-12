package com.legacy.legacy_android.feature.screen.market

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.bars.infobar.InfoBar
import com.legacy.legacy_android.res.component.bars.NavBar
import com.legacy.legacy_android.res.component.marketItem.MarketInfo
import com.legacy.legacy_android.res.component.marketItem.PackWrap
import com.legacy.legacy_android.res.component.marketItem.Packs
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Line_Natural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.Red_Normal
import kotlinx.coroutines.delay

@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                 viewModel: MarketViewModel = hiltViewModel(),
                 navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        while (true) {
            viewModel.updateTimeLeft()
            delay(1000)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background_Alternative)
    ){
        InfoBar()
        Column (
            modifier = modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier
                .height(70.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleBox(title = "상점", image = R.drawable.shop)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.packList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.packStatus,
                            onClick = { viewModel.packStatus = index },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Natural
                        )
                    }
                }
                MarketInfo(quantity = 3, magnification = 1.75, time = viewModel.timeUntilMidnight)
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // 팩 파는 구간
                    PackWrap(newList = Packs.bluePackList)
                    PackWrap(newList = Packs.purplePackList)
                    PackWrap(newList = Packs.redPackList)
                }
            }
            Spacer(
                modifier = modifier
                    .height(100.dp)
            )
        }
        // NavBar
        Box(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .zIndex(7f)
        ) {
            NavBar(navHostController = navHostController)
        }
    }
}