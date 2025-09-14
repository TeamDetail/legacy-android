package com.legacy.legacy_android.domain.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.card.CardService
import com.legacy.legacy_android.feature.network.card.MyCardResponse
import com.legacy.legacy_android.feature.network.user.GetMeService
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.InventoryResponse
import com.legacy.legacy_android.feature.network.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val getMeService: GetMeService,
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

    suspend fun getInventory(): Result<List<InventoryItem>?>{
        return try {
            val response = getMeService.getInventory()
            Log.d("UserRepository", "인벤토리 로드 성공: ${response.data}")
            Result.success(response.data)
        } catch (error: Exception) {
            Log.e("UserRepository", "인벤토리 로드 실패", error)
            Result.failure(error)
        }
    }

    fun clearProfile() {
        Log.d("UserRepository", "프로필 데이터 초기화")
        _profile.value = null
        hasLoaded = false
    }
}
