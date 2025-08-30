package com.legacy.legacy_android.domain.repository.home

import android.util.Log
import com.legacy.legacy_android.feature.network.ruins.RuinsMapRequest
import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdService
import com.legacy.legacy_android.feature.network.ruins.search.RuinsSearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuinsRepository @Inject constructor(
    private val ruinsMapService: RuinsMapService,
    private val ruinsIdService: RuinsIdService,
    private val ruinsSearchService: RuinsSearchService
) {
    suspend fun getRuinsByBounds(
        minLat: Double, maxLat: Double,
        minLng: Double, maxLng: Double
    ): Result<List<RuinsMapResponse>> {
        return try {
            val request = RuinsMapRequest(minLat, maxLat, minLng, maxLng)
            val response = ruinsMapService.ruinsMap(
                minLat = request.minLat,
                maxLat = request.maxLat,
                minLng = request.minLng,
                maxLng = request.maxLng
            )
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            Log.e("RuinsRepository", "ruins repository 오류 ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getRuinsById(id: Int): Result<RuinsIdResponse?> {
        return try {
            val response = ruinsIdService.getRuinsById(id)
            Result.success(response.data)
        } catch (e: Exception) {
            Log.e("RuinsRepository", "getById 오류 ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getSearchRuins(name: String): Result<List<RuinsIdResponse>?>{
        return try{
            val response = ruinsSearchService.getSearchRuins(name)
            if (response.data != null){
                println("search ruins성공")
            Result.success(response.data)
            }
            else {
                Result.failure(NullPointerException("데이터 빔"))
            }
        } catch (e: Exception) {
            Log.e("RuinsRepository", "getSearchNameRuins ${name} 오류 ${e.message}")
            Result.failure(e)
        }
    }
}
