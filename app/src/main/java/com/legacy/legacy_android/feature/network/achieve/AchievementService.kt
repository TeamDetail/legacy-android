package com.legacy.legacy_android.feature.network.achieve

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AchievementService {
    @GET("/achievement/all")
    suspend fun getAchievements(): BaseResponse<List<AchievementResponse>>
    @GET("/achievement/{type}")
    suspend fun getAchievementsByType(@Path("type") type: String): BaseResponse<List<AchievementResponse>>
}