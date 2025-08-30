package com.legacy.legacy_android.feature.network.ruins.id

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RuinsIdService {
    @GET("/ruins/{id}")
    suspend fun getRuinsById(
        @Path("id") id: Int
    ): BaseResponse<RuinsIdResponse>
}