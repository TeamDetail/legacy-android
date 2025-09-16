package com.legacy.legacy_android.feature.screen.achieve.model

import com.legacy.legacy_android.feature.network.achieve.AchievementResponse

data class AchieveUiState(
    val achieveStatus: Int = 0,
    val achieveList: List<AchievementResponse>? = null,
    val isLoading: Boolean = false
)
