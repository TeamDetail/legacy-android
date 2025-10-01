package com.legacy.legacy_android.domain.repository.market

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.network.market.MarketResponse
import com.legacy.legacy_android.feature.network.market.MarketService
import javax.inject.Singleton
import android.util.Log
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.user.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class MarketRepository(
    private val marketService: MarketService,
    private val userRepository: UserRepository
) {
    val profile: StateFlow<UserData?>
        get() = userRepository.profile

    suspend fun getMarketData(): Result<List<CardPack>?> {
        return try {
            val response: BaseResponse<MarketResponse> = marketService.getMarket()
            val packs: List<CardPack>? = response.data?.cardpack
            Result.success(packs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun buyCardPack(id: Int, price: Int): Result<BaseResponse<Nothing>> {
        return try {
            val currentProfile = profile.value
            if (currentProfile == null) {
                return Result.failure(Exception("프로필 정보를 불러올 수 없습니다"))
            }
            if (currentProfile.credit >= price) {
                val response: BaseResponse<Nothing> = marketService.buyCardPack(id)
                Result.success(response)
            } else {
                Result.failure(Exception("크레딧이 부족합니다"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}