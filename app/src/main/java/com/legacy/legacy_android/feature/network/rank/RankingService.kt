package com.legacy.legacy_android.feature.network.rank

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface RankingService {
    @GET("/ranklist/explore/all")
    suspend fun rank(): BaseResponse<List<RankingResponse>>
}