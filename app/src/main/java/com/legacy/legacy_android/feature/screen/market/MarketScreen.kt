package com.legacy.legacy_android.feature.screen.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.marketItem.MarketInfo
import com.legacy.legacy_android.res.component.marketItem.PackWrap
import com.legacy.legacy_android.res.component.marketItem.Packs
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun MarketScreen(modifier: Modifier = Modifier,
                 viewModel: MarketViewModel = hiltViewModel(),
                 navHostController: NavHostController
) {
    val packList = listOf("카드 팩", "크레딧 충전")

    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "상점", image = R.drawable.shop)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    packList.forEachIndexed { index, item ->
                        StatusButton(
                            selectedValue = viewModel.uiState.packStatus,
                            onClick = { viewModel.changePackStatus(index) },
                            text = item,
                            id = index,
                            selectedColor = Primary,
                            nonSelectedColor = Line_Netural
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