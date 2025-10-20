package com.legacy.legacy_android.res.component.modal.check

import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem

data class CheckUiState(
    val checkStatus: Int = 0,
    val selectedCheck: DailyResponse? = null,
    val getItems: List<InventoryItem>? = emptyList(),
    val check: List<DailyResponse>? = null
)
