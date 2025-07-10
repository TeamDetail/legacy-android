package com.legacy.legacy_android.feature.network.block.Get

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GetBlockService {
    @GET("/block/user/{id}")
    suspend fun getBlockById(
        @Path("id") id: Long
    ): BaseResponse<List<GetBlockResponse>>
}