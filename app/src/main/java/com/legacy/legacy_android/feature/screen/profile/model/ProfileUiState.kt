package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.card.MyCardResponse
import com.legacy.legacy_android.feature.network.ruins.id.Cards
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.InventoryResponse

data class ProfileUiState(
    val profileStatus: Int = 0,
    val titleStatus: Int = 0,
    val myCards: MyCardResponse? = null,
    val myInventory: List<InventoryItem>? = null,
    val selectedItem: InventoryItem? = null,
    val cardPackOpen: Boolean = false,
    val packOpenCount: Int = 1,
    val openCardResponse: List<Cards>? =null
    )
