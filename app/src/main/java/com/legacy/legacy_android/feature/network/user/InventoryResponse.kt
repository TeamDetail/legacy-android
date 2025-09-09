package com.legacy.legacy_android.feature.network.user

data class InventoryResponse(
    val data: List<InventoryItem>
)

data class InventoryItem(
    val itemId: Int,
    val itemType: String,
    val itemName: String,
    val itemDescription: String,
    val itemCount: Int
)