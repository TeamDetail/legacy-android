package com.legacy.legacy_android.res.component.modal.check

import com.legacy.legacy_android.feature.network.user.InventoryItem

data class CheckUiState(
    val checkStatus: Int = 0,
    val selectedCheck: List<InventoryItem?> = emptyList(),
    val getItems: List<InventoryItem?> = emptyList()
)
