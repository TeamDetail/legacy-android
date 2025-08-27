package com.legacy.legacy_android.feature.network.market

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface MarketService {
    @GET("/store")
    suspend fun getMarket() : BaseResponse<MarketResponse>
}