package com.legacy.legacy_android.feature.screen.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.user.UserData
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    val profileFlow = userRepository.profile

    var profile by mutableStateOf<UserData?>(null)
        private set

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun changeProfileStatus(status: Int){
        uiState = uiState.copy(profileStatus = status)
    }

    fun fetchProfile(force: Boolean = false) {
        viewModelScope.launch {
            userRepository.fetchProfile(force)
        }
    }

    fun clearProfile() {
        userRepository.clearProfile()
    }
}
