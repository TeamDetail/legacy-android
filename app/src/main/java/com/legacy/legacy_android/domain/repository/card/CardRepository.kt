package com.legacy.legacy_android.domain.repository.card

import android.util.Log
import com.legacy.legacy_android.feature.network.card.CardService
import com.legacy.legacy_android.feature.network.card.MyCardResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(
    private val cardService: CardService
) {
    suspend fun fetchMyCard(region: String): Result<MyCardResponse?> {
        return try {
            val response = cardService.getMyCard(region)
            Log.d("CardRepository", "카드 불러오기 성공: $region ${response.data}")
            Result.success(response.data)
        } catch (e: Exception) {
            Log.e("CardRepository", "카드 불러오기 실패", e)
            Result.failure(e)
        }
    }
}