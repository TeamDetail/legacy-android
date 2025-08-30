package com.legacy.legacy_android.feature.network.market

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MarketService {
    @GET("/store")
    suspend fun getMarket() : BaseResponse<MarketResponse>
    @PATCH("/store/cardBuy/{cardpackId}")
    suspend fun buyCardPack(
        @Path("cardpackId") cardpackId: Int
    ): BaseResponse<Nothing>
}