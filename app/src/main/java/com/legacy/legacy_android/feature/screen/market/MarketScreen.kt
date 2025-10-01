package com.legacy.legacy_android.feature.screen.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.res.component.layout.MarketLayout
import com.legacy.legacy_android.res.component.marketItem.MarketInfo
import com.legacy.legacy_android.res.component.marketItem.PackWrap
import com.legacy.legacy_android.res.component.modal.AlertModal
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary

@Composable
fun MarketScreen(
    modifier: Modifier = Modifier,
    viewModel: MarketViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchMarketData()
    }
    val packList = listOf("카드 팩", "크레딧 충전")

    Box(modifier = Modifier.fillMaxSize()) {
        MarketLayout(
            modifier = modifier,
            navHostController = navHostController,
            viewModel = viewModel
        ) {
            TitleBox(title = "상점", image = R.drawable.shop)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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

            MarketInfo(
                quantity = 4,
                magnification = 1.75,
                time = viewModel.timeUntilMidnight
            )

            PackWrap(
                newList = viewModel.uiState.packs,
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(100.dp))
        }

        if (viewModel.uiState.successBuy != null) {
            AlertModal(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp),
                isCorrect = viewModel.uiState.successBuy == true,
                incorrectMessage = "구매 실패 : 크레딧이 부족합니다!",
                correctMessage = "구매 성공!"
            )
        }
    }
}