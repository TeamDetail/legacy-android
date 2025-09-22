package com.legacy.legacy_android.feature.network.achieve

import com.google.gson.annotations.SerializedName

data class AchievementAwardResponse(
    @SerializedName("awardExp")
    val awardExp: Int,
    @SerializedName("awardCredit")
    val awardCredit: Int,
    @SerializedName("achievementAward")
    val achievementAward: List<AchievementAward>
)