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
            Log.d("UserRepository", "프로필 로드 성공: ${response.data?.nickname}")
        } catch (error: Exception) {
            Log.e("UserRepository", "프로필 로드 실패", error)
            _profile.value = null
            hasLoaded = false
        }
    }

    fun clearProfile() {
        Log.d("UserRepository", "프로필 데이터 초기화")
        _profile.value = null
        hasLoaded = false
    }

    suspend fun refreshProfile() {
        Log.d("UserRepository", "프로필 강제 새로고침")
        clearProfile()
        fetchProfile(force = true)
    }

    fun getCurrentProfile(): UserData? {
        return _profile.value
    }
}
