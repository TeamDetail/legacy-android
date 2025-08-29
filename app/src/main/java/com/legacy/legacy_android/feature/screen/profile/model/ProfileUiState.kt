package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.ruinsId.Cards

data class ProfileUiState(
    val profileStatus: Int = 0,
    val titleStatus: Int = 0,
    val myCards: List<Cards> = emptyList(),
    )
