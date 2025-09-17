package com.legacy.legacy_android.feature.network.achieve

data class AchievementAwardResponse(
    val awardExp: Int,
    val awardCredit: Int,
    val awardItem: List<AchievementAward>
)