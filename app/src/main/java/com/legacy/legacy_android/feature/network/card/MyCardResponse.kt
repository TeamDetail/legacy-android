package com.legacy.legacy_android.feature.network.card

import com.legacy.legacy_android.feature.network.ruins.id.Cards

data class MyCardResponse(
    val maxCount: Int,
    val cards: List<Cards>
)
