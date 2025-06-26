package com.legacy.legacy_android.feature.screen.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.feature.network.user.GetMeService
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.feature.network.user.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMeService: GetMeService
) : ViewModel() {
    var profile by mutableStateOf<UserData?>(null)
        private set

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = getMeService.getMe()
                profile = response.data
            } catch (error: Error) {
                Log.e("프로필 뷰에서 에러발생", error.toString())
            }
        }
    }
}