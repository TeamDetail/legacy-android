package com.legacy.legacy_android.feature.screen.market.model

import com.legacy.legacy_android.feature.network.achieve.CardPack

data class MarketUiState(
    val packStatus: Int = 0,
    val packs: List<CardPack>? = null,
    val isModalOpen: Boolean = false,
    val currentCardPack: CardPack? = null,
)