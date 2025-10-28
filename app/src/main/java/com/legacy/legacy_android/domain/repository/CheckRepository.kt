package com.legacy.legacy_android.domain.repository

import android.util.Log
import com.legacy.legacy_android.feature.network.check.CheckService
import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckRepository @Inject constructor(
    private val checkService: CheckService
) {

    suspend fun checkDaily(): Result<List<DailyResponse>> {
        return try {
            val response = checkService.checkDaily()
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            Log.e("CheckRepository", "출첵 수신 실패", e)
            Result.failure(e)
        }
    }

    suspend fun getItem(id: Int): Result<List<InventoryItem>> {
        return try {
            val response = checkService.getItem(id)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            Log.e("CheckRepository", "아이템 수령 실패", e)
            Result.failure(e)
        }
    }
}
