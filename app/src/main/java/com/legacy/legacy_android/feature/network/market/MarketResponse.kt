package com.legacy.legacy_android.feature.network.market

import com.legacy.legacy_android.feature.network.achieve.CardPack

data class MarketResponse(
    val cardpack: List<CardPack>,
    val buyCount: Int
)
