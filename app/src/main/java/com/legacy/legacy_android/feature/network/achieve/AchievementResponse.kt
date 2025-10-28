package com.legacy.legacy_android.feature.network.achieve

import com.google.gson.annotations.SerializedName
import com.legacy.legacy_android.feature.network.user.InventoryItem

data class AchievementResponse(
    @SerializedName("achievementId")
    val achievementId: Int,

    @SerializedName("achievementName")
    val achievementName: String,

    @SerializedName("achievementContent")
    val achievementContent: String,

    @SerializedName("achievementType")
    val achievementType: String,

    @SerializedName("achieveUserPercent")
    val achieveUserPercent: Double,

    @SerializedName("currentRate")
    val currentRate: Int,

    @SerializedName("achievementGradeText")
    val achievementGradeText: String,

    @SerializedName("goalRate")
    val goalRate: Int,

    @SerializedName("achievementGrade")
    val achievementGrade: String,

    @SerializedName("achievementAward")
    val achievementAward: List<InventoryItem>,

    @SerializedName("receive")
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
