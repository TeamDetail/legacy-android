package com.legacy.legacy_android.feature.network.check

import com.legacy.legacy_android.feature.network.user.InventoryItem

data class DailyResponse(
    val id: Int,
    val name: String,
    val startAt: String,
    val endAt: String,
    val awards: List<List<InventoryItem>>,
    val checkCount: Int,
    val check: Boolean
)