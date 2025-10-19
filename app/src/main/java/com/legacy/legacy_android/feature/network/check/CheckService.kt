package com.legacy.legacy_android.feature.network.check

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.user.InventoryItem
import retrofit2.http.GET
import retrofit2.http.POST

interface CheckService {
    @POST("/daily")
    suspend fun getItems(): BaseResponse<List<InventoryItem>>

    @GET("/daily")
    suspend fun checkDaily(): BaseResponse<DailyResponse>
}