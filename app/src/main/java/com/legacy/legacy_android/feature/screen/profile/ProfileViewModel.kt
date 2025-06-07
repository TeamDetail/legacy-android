package com.legacy.legacy_android.feature.screen.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.feature.screen.profile.model.ProfilePendingUiState
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow() // 외부에서도 읽을 수 있게

    fun getMe(){
        _state.update {
            it.copy(profileUiState = ProfilePendingUiState.Loading)
        }
        viewModelScope.launch {
        }
    }
}