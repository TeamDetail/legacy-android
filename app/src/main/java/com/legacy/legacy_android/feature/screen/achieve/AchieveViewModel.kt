package com.legacy.legacy_android.feature.screen.achieve

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.legacy.legacy_android.feature.screen.achieve.model.AchieveUiState
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AchieveViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application){
    var uiState by mutableStateOf(AchieveUiState())
        private set

    fun changeAchieveStatus(status: Int){
        uiState = uiState.copy(achieveStatus = status)
    }
}