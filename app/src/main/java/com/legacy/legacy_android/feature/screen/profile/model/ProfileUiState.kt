package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.card.MyCardResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.InventoryResponse

data class ProfileUiState(
    val profileStatus: Int = 0,
    val titleStatus: Int = 0,
    val myCards: MyCardResponse? = null,
    val myInventory: List<InventoryItem>? = null,
    val selectedItem: InventoryItem? = null
    )
