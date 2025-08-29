package com.legacy.legacy_android.feature.network.card

import com.legacy.legacy_android.feature.network.ruinsId.Cards

data class MyCardResponse(
    val maxCount: Int,
    val cards: List<Cards>
)
