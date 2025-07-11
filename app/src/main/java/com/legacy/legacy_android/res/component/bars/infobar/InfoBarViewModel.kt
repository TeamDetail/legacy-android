package com.legacy.legacy_android.res.component.bars.infobar

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.user.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoBarViewModel @Inject constructor(
    application: Application,
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository

) : AndroidViewModel(application) {
    var isTabClicked by mutableStateOf(false)
    fun setIsTabClicked(){
        isTabClicked = !isTabClicked;
    }

    val profileFlow = userRepository.profile

    fun fetchProfile() {
        viewModelScope.launch {
            userRepository.fetchProfile()
        }
    }
}