package com.legacy.legacy_android.domain.repository

import android.util.Log
import com.legacy.legacy_android.feature.network.ruins.id.Cards
import com.legacy.legacy_android.feature.network.user.CardOpenRequest
import com.legacy.legacy_android.feature.network.user.GetMeService
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.Title
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
        } catch (error: Exception) {
            Log.e("UserRepository", "프로필 로드 실패", error)
            _profile.value = null
            hasLoaded = false
        }
    }

    suspend fun getInventory(): Result<List<InventoryItem>?>{
        return try {
            val response = getMeService.getInventory()
            Result.success(response.data)
        } catch (error: Exception) {
            Log.e("UserRepository", "인벤토리 로드 실패", error)
            Result.failure(error)
        }
    }

    suspend fun openCardPack(id: Int, count:Int): Result<List<Cards>?>{
        return try{
            val response = getMeService.cardOpen(CardOpenRequest(id, count))
            Result.success(response.data)
        }catch (error: Exception){
            Log.e("UserRepository", "카드 오픈 실패", error)
            Result.failure(error)
        }
    }

    fun clearProfile() {
        Log.d("UserRepository", "프로필 데이터 초기화")
        _profile.value = null
        hasLoaded = false
    }
    suspend fun getTitles(): Result<List<Title>?>{
        return try {
            val response = getMeService.getTitles()
            Result.success(response.data)
        } catch (error: Exception) {
            Log.e("UserRepository", "타이틀 로드 실패", error)
            Result.failure(error)
        }
    }
}
