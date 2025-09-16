package com.legacy.legacy_android.feature.screen.achieve

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.achieve.AchieveRepository
import com.legacy.legacy_android.feature.network.achieve.AchievementResponse
import com.legacy.legacy_android.feature.screen.achieve.model.AchieveUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class AchieveViewModel @Inject constructor(
    private val achieveRepository: AchieveRepository,
): ViewModel() {
    var uiState by mutableStateOf(AchieveUiState())
        private set

    fun changeAchieveStatus(status: Int) {
        uiState = uiState.copy(achieveStatus = status)
    }

    fun fetchAllAchieveList() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            achieveRepository.fetchAllAchievement()
                .onSuccess { achieve ->
                    uiState = uiState.copy(achieveList = achieve)
                }
                .onFailure { achieve ->
                    println("전체 달성 목록 불러오기 실패")
                }
            uiState = uiState.copy(isLoading = false)
        }
    }

    fun fetchAchieveListByType(type: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            achieveRepository.fetchAchievementById(type)
                .onSuccess { achieve ->
                    uiState = uiState.copy(achieveList = achieve)
                }
                .onFailure { achieve ->
                    println("전체 달성 목록 불러오기 실패")
                }
            uiState = uiState.copy(isLoading = false)
        }
    }

    fun updateCurrentAchieve(achieve: AchievementResponse?){
        uiState = uiState.copy(currentAchieve = achieve)
    }
}