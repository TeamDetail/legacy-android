package com.legacy.legacy_android.feature.network.ruins

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RuinsMapService {
    @POST("/ruins/map")
    suspend fun ruinsmap(@Body loc: RuinsMapRequest) : BaseResponse<RuinsMapRequest>
}