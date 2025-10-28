package com.legacy.legacy_android.feature.network.check

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CheckService {
    @GET("/daily")
    suspend fun checkDaily(): BaseResponse<List<DailyResponse>>

    @POST("/daily/{dailyId}")
    suspend fun getItem(
        @Path("dailyId") dailyId: Int
    ): BaseResponse<List<InventoryItem>>
}