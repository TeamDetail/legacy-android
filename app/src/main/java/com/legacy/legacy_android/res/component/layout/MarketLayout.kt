package com.legacy.legacy_android.res.component.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.screen.market.MarketViewModel
import com.legacy.legacy_android.res.component.modal.MarketModal

@Composable
fun MarketLayout(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: MarketViewModel,
    content: @Composable () -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        CommonScreenLayout(modifier, navHostController) {
            content()
        }
        // 모달 공간
        if (viewModel.uiState.isModalOpen) {
            MarketModal(
                credit = viewModel.uiState.currentCardPack?.price,
                onConfirm = {
                    viewModel.uiState.currentCardPack?.cardpackId?.let { id ->
                        viewModel.buyCardPack(id)
                        viewModel.setModal()
                    }
                },
                onCancel = {viewModel.setModal()},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
