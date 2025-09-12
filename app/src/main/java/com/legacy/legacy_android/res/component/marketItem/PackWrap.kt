package com.legacy.legacy_android.res.component.marketItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.screen.market.MarketViewModel

@Composable
fun PackWrap(
    newList: List<CardPack>?,
    viewModel: MarketViewModel){
    val list = newList ?: emptyList()
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8 .dp),

    ) {
        list.forEach{ pack ->
            Pack(cardPack = pack, viewModel = viewModel)
        }
    }
}