package com.legacy.legacy_android.feature.network.block.Get

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface GetBlockService {
    @GET("/block/user/me")
    suspend fun getBlock(): BaseResponse<List<GetBlockResponse>>
}