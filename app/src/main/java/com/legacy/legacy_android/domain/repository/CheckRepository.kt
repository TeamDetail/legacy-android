package com.legacy.legacy_android.domain.repository

import com.legacy.legacy_android.feature.network.check.CheckService
import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckRepository @Inject constructor(
    private val checkService: CheckService
) {
    suspend fun checkDaily(): Result<DailyResponse?> {
        return try {
            val response = checkService.checkDaily()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getItems(): Result<List<InventoryItem>?> {
        return try {
            val response = checkService.getItems()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
