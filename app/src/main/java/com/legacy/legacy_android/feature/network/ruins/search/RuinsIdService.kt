package com.legacy.legacy_android.feature.network.ruins.search

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RuinsSearchService {
    @GET("/ruins/search")
    suspend fun getSearchRuins(
        @Query("ruinsName") ruinsName : String
    ): BaseResponse<List<RuinsIdResponse>>
}