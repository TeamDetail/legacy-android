package com.legacy.legacy_android.res.component.bars.infobar

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoBarViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    var isTabClicked by mutableStateOf(false)

    fun setIsTabClicked() {
        isTabClicked = !isTabClicked
    }

    val profileFlow = userRepository.profile

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                userRepository.fetchProfile()
            } catch (e: Exception) {
                Log.e("InfoBarViewModel", "프로필 로드 실패", e)
            }
        }
    }

    fun refreshProfile() {
        viewModelScope.launch {
            try {
                userRepository.refreshProfile()
            } catch (e: Exception) {
                Log.e("InfoBarViewModel", "프로필 새로고침 실패", e)
            }
        }
    }

    fun clearProfile() {
        userRepository.clearProfile()
    }
}