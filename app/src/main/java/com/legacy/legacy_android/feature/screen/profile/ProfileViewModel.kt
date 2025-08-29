package com.legacy.legacy_android.feature.screen.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.domain.repository.card.CardRepository
import com.legacy.legacy_android.feature.network.user.UserData
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository
) : ViewModel() {
    val profileFlow = userRepository.profile

    var profile by mutableStateOf<UserData?>(null)
        private set

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun changeProfileStatus(status: Int){
        uiState = uiState.copy(profileStatus = status)
    }

    fun changeTitleStatus(status: Int){
        uiState = uiState.copy(titleStatus = status)
    }

    fun fetchProfile(force: Boolean = false) {
        viewModelScope.launch {
            userRepository.fetchProfile(force)
        }
    }

    fun clearProfile() {
        userRepository.clearProfile()
    }

    fun fetchMyCollection(region: String){
        viewModelScope.launch {
            val result = cardRepository.fetchMyCard(region)
            result.onSuccess {
                Log.d("CardRepository", "카드 불러오기 성공: $region $it")
                uiState = uiState.copy(
                    myCards = it?.cards ?: emptyList()
                )
            }.onFailure { e ->
                Log.e("CardRepository", "카드 불러오기 실패", e)
            }
        }
    }
}
