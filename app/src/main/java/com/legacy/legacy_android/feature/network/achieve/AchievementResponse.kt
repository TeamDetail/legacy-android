package com.legacy.legacy_android.feature.network.achieve

import com.google.gson.annotations.SerializedName

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
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("itemName")
    val itemName: String,
    @SerializedName("itemDescription")
    val itemDescription: String,
    @SerializedName("itemCount")
    val itemCount: Int
)
data class CardPack(
    val cardpackName: String,
    val cardpackContent: String,
    val price: Int,
    val storeType: String,
    val cardpackId: Int
)
