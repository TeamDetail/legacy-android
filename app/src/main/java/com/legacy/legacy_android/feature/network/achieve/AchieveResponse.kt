package com.legacy.legacy_android.feature.network.achieve

data class AchieveResponse(
    val achievementId: Int,
    val achievementName: String,
    val achievementContent: String,
    val goalText: String,
    val isReceive: Boolean,
    val currentRate: Int,
    val goalRate: Int,
    val achievementAward: AchievementAward
)

data class AchievementAward(
    val cardpack: List<CardPack>,
    val creadit: Int?,
    val title: String?,
    val card: String?
)

data class CardPack(
    val cardpackName: String,
    val cardpackContent: String,
    val price: Int,
    val storeType: String,
    val cardpackId: Int
)
