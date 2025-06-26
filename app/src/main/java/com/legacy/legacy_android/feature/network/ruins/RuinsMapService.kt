package com.legacy.legacy_android.feature.network.ruins

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RuinsMapService {
    @GET("/ruins/map")
    suspend fun ruinsMap(
        @Query("minLat") minLat: Double,
        @Query("maxLat") maxLat: Double,
        @Query("minLng") minLng: Double,
        @Query("maxLng") maxLng: Double
    ): BaseResponse<List<RuinsMapResponse>>
}
