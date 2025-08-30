package com.legacy.legacy_android.domain.repository.market

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.network.market.MarketResponse
import com.legacy.legacy_android.feature.network.market.MarketService
import javax.inject.Singleton
import android.util.Log
@Singleton
class MarketRepository(
    private val marketService: MarketService
) {
    suspend fun getMarketData(): Result<List<CardPack>?> {
        return try {
            val response: BaseResponse<MarketResponse> = marketService.getMarket()

            val packs: List<CardPack>? = response.data?.cardPack

            Result.success(packs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun buyCardPack(id: Int): Result<BaseResponse<Nothing>> {
        return try {
            val response: BaseResponse<Nothing> = marketService.buyCardPack(id)
            Log.d("Market", "카드팩 구매 성공: $response")
            Result.success(response)
        } catch (e: Exception) {
            Log.e("Market", "카드팩 구매 실패 ${id.toString()}", e)
            Result.failure(e)
        }
    }

}