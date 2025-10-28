package com.legacy.legacy_android.res.component.modal.check

import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem

data class CheckUiState(
    val checkStatus: Int = 0,
    val getItems: List<InventoryItem>? = emptyList(),
    val check: List<DailyResponse>? = null,
    val received: Boolean? = null,
    val currentCheck: List<InventoryItem>? = null,
    val currentDay: Int? =null
)
