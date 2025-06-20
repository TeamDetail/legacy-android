package com.legacy.legacy_android.feature.screen.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.feature.network.user.GetMeResponse
import com.legacy.legacy_android.feature.network.user.GetMeService
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMeService: GetMeService
) : ViewModel() {
    var profile by mutableStateOf<GetMeResponse?>(null)
        private set

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = getMeService.profile()
                profile = response.data
                Log.d("Profile", "성공했습니다: $response")
            } catch (e: Exception) {
                Log.d("Profile", "에러: ${e.message}")
            }
        }
    }
}