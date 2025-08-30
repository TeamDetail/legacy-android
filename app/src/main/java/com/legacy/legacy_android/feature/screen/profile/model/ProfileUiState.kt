package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.card.MyCardResponse

data class ProfileUiState(
    val profileStatus: Int = 0,
    val titleStatus: Int = 0,
    val myCards: MyCardResponse? = null,
    )
