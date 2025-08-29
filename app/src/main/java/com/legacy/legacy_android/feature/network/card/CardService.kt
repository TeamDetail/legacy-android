package com.legacy.legacy_android.feature.network.card

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CardService {
    @GET("/card/collection/{region}")
    suspend fun getMyCard(@Path("region") region: String): BaseResponse<MyCardResponse>
}