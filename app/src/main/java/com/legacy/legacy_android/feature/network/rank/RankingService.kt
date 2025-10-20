package com.legacy.legacy_android.feature.network.rank

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RankingService {
    @GET("/ranklist/explore/{type}")
    suspend fun exploreRank(@Path ("type") type: String): BaseResponse<List<RankingResponse>>

    @GET("/ranklist/level/{type}")
    suspend fun levelRank(@Path ("type") type: String): BaseResponse<List<RankingResponse>>
}