package com.legacy.legacy_android.feature.network.market

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MarketService {
    @GET("/store")
    suspend fun getMarket() : BaseResponse<MarketResponse>
    @PATCH("/store/cardBuy/{cardpackId}")
    suspend fun buyCardPack(id: Int): BaseResponse<Nothing>
}