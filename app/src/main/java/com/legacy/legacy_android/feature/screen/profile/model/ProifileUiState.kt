package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.user.GetMeResponse

data class ProfileUiState(
    val profileUiState: ProfilePendingUiState = ProfilePendingUiState.Default,
)

sealed interface ProfilePendingUiState {
    data class Success(
        val myData: GetMeResponse
    ): ProfilePendingUiState
    data object Loading : ProfilePendingUiState
    data object Error : ProfilePendingUiState
    data object Default : ProfilePendingUiState
}