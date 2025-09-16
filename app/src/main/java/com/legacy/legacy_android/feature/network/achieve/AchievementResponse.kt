package com.legacy.legacy_android.feature.network.achieve
data class AchievementResponse(
    val achievementId: Int,
    val achievementName: String,
    val achievementContent: String,
    val achievementType: String,
    val achieveUserPercent: Double,
    val currentRate: Int,
    val goalRate: Int,
    val achievementGrade: String,
    val achievementAward: List<AchievementAward>,
    val receive: Boolean
)

data class AchievementAward(
    val itemId: Int,
    val itemType: String,
    val itemName: String,
    val itemDescription: String,
    val itemCount: Int
)

data class CardPack(
    val cardpackName: String,
    val cardpackContent: String,
    val price: Int,
    val storeType: String,
    val cardpackId: Int
)
