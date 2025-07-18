package com.legacy.legacy_android.domain.repository

import android.util.Log
import com.legacy.legacy_android.feature.network.user.GetMeService
import com.legacy.legacy_android.feature.network.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val getMeService: GetMeService
) {
    private val _profile = MutableStateFlow<UserData?>(null)
    val profile: StateFlow<UserData?> = _profile.asStateFlow()

    private var hasLoaded = false

    suspend fun fetchProfile(force: Boolean = false) {
        if (hasLoaded && !force) return

        try {
            val response = getMeService.getMe()
            _profile.value = response.data
            hasLoaded = true
        } catch (error: Exception) {
            Log.e("UserRepository", "프로필 로드 실패", error)
        }
    }

    fun clearProfile() {
        _profile.value = null
        hasLoaded = false
    }
}
