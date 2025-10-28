package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.card.MyCardResponse
import com.legacy.legacy_android.feature.network.ruins.id.Cards
import com.legacy.legacy_android.feature.network.ruins.id.CreditPack
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.Title

data class ProfileUiState(
    val profileStatus: Int = 0,
    val titleStatus: Int = 0,
    val myCards: List<MyCardResponse>? = null,
    val myInventory: List<InventoryItem>? = null,
    val selectedItem: InventoryItem? = null,
    val cardPackOpen: Boolean = false,
    val creditPackOpen: Boolean = false,
    val packOpenCount: Int = 1,
    val openCardResponse: List<Cards>? =null,
    val openCreditResponse: CreditPack? = null,
    val statusList: List<String> = listOf("경기", "강원", "경북", "경남", "전북", "전남", "충북", "충남", "제주"),
    val titleList: List<Title>? = emptyList(),
    val changeStatus: String = ""
    )
