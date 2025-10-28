package com.legacy.legacy_android.domain.repository.rank

import com.legacy.legacy_android.feature.network.rank.RankingResponse
import com.legacy.legacy_android.feature.network.rank.RankingService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RankingRepository @Inject constructor(
    private val rankingService: RankingService
){
    suspend fun fetchExploreRanking(type: String): Result<List<RankingResponse>?> {
        return try{
            val response = rankingService.exploreRank(type = type)
            Result.success(if(!response.data.isNullOrEmpty())response.data else null)
        }catch(e: Exception){
            println("실패")
            Result.failure(e)
        }
    }

    suspend fun fetchLevelRanking(type: String): Result<List<RankingResponse>?> {
        return try{
            val response = rankingService.levelRank(type = type)
            Result.success(if(!response.data.isNullOrEmpty())response.data else null)
        }catch(e: Exception){
            println("실패")
            Result.failure(e)
        }
    }
}