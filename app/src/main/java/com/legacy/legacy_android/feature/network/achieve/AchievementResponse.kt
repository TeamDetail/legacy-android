package com.legacy.legacy_android.feature.network.achieve

import com.legacy.legacy_android.feature.network.user.InventoryItem

data class AchievementResponse(
    val achievementId: Int,
    val achievementName: String,
    val achievementContent: String,
    val achievementType: String,
    val achieveUserPercent: Double,
    val currentRate: Int,
    val goalRate: Int,
    val achievementGrade: String,
    val achievementAward: List<InventoryItem>,
    val receive: Boolean
)

data class CardPack(
    val cardpackName: String,
    val cardpackContent: String,
    val price: Int,
    val storeType: String,
    val cardpackId: Int,
    val storeSubType: String,
)
