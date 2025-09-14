package com.legacy.legacy_android.feature.network.mail

data class MailResponse(
    val mailTitle: String,
    val mailContent: String,
    val sendAt: String,
    val itemData: List<ItemData>
)

data class ItemData(
    val itemId: Int,
    val itemType: String,
    val itemName: String,
    val itemDescription: String,
    val itemCount: Int
)
