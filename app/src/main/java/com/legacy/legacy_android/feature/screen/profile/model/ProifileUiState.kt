package com.legacy.legacy_android.feature.screen.profile.model

import com.legacy.legacy_android.feature.network.user.UserResponse

data class ProfileUiState(
    val profileUiState: ProfilePendingUiState = ProfilePendingUiState.Default,
)

sealed interface ProfilePendingUiState {
    data class Success(
        val myData: UserResponse
    ): ProfilePendingUiState
    data object Loading : ProfilePendingUiState
    data object Error : ProfilePendingUiState
    data object Default : ProfilePendingUiState
}