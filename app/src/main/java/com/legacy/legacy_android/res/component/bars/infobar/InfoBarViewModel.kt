package com.legacy.legacy_android.res.component.bars.infobar

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.legacy.legacy_android.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoBarViewModel @Inject constructor(
    application: Application,
    private val userRepository: UserRepository,
) : AndroidViewModel(application) {

    var isTabClicked by mutableStateOf(false)


    var isMailOpen = userRepository.isMailOpen.value

    fun setIsTabClicked() {
        isTabClicked = !isTabClicked
    }
    val profileFlow = userRepository.profile

    fun fetchMail() {
        viewModelScope.launch {
            try {
//                userRepository.fetchMail()
                userRepository.isMailOpen.value = !userRepository.isMailOpen.value
            } catch (e: Exception) {
                Log.e("InfoBarViewModel", "메일 로드 실패", e)
            }
        }
    }

    private val _navigateToLogin = MutableSharedFlow<Unit>()

    fun fetchProfile() {
        if (userRepository.profile.value?.userId == null) {
            viewModelScope.launch {
                _navigateToLogin.emit(Unit)
            }
        }
        viewModelScope.launch {
            try {
                userRepository.fetchProfile()
            } catch (e: Exception) {
                Log.e("InfoBarViewModel", "프로필 로드 실패", e)
            }
        }
    }

    fun clearProfile() {
        userRepository.clearProfile()
    }
}