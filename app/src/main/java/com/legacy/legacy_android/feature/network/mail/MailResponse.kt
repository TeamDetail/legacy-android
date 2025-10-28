package com.legacy.legacy_android.feature.network.mail

import com.legacy.legacy_android.feature.network.user.InventoryItem

data class MailResponse(
    val mailTitle: String,
    val mailContent: String,
    val sendAt: String,
    val itemData: List<InventoryItem>
)

