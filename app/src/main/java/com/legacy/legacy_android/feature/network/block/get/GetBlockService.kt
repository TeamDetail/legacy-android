package com.legacy.legacy_android.feature.network.block.get

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
import retrofit2.http.GET

interface GetBlockService {
    @GET("/block/user/me")
    suspend fun getBlock(): BaseResponse<List<GetBlockResponse>>
}