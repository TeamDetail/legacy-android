package com.legacy.legacy_android.domain.repository.rank

import com.legacy.legacy_android.feature.network.rank.RankingResponse
import com.legacy.legacy_android.feature.network.rank.RankingService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RankingRepository @Inject constructor(
    private val rankingService: RankingService
){
    suspend fun fetchRanking(): Result<List<RankingResponse>?> {
        return try{
            val response = rankingService.rank()
            Result.success(if(!response.data.isNullOrEmpty())response.data else null)
        }catch(e: Exception){
            Result.failure(e)
        }
    }
}