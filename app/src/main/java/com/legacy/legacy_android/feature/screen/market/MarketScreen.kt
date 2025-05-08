package com.legacy.legacy_android.feature.screen.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset

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
import com.legacy.legacy_android.res.component.marketItem.StatusButton
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Background_Alternative
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Background_Alternative)
    ){
        InfoBar()
        Column (
            modifier = Modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier
                .height(70.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TitleBox(title = "상점", image = R.drawable.shop)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusButton(
                        value = viewModel.marketStatus,
                        setValue = { viewModel.setMarketStatus(true) },
                        text = "카드 팩"
                    )
                    StatusButton(
                        value = !viewModel.marketStatus,
                        setValue = { viewModel.setMarketStatus(false) },
                        text = "크레딧 충전"
                    )
                }
                MarketInfo(quantity = 3, magnification = 1.75, time = viewModel.timeUntilMidnight)
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // 팩 파는 구간
                    PackWrap(title = "앤더슨팩", newList = Packs.bluePackList)
                    PackWrap(title = "코팩", newList = Packs.purplePackList)
                    PackWrap(title = "여드름팩", newList = Packs.redPackList)
                }
            }
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
        }
        // NavBar
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