package com.legacy.legacy_android.domain.repository.achieve

import android.util.Log
import com.legacy.legacy_android.feature.network.achieve.AchievementAwardResponse
import com.legacy.legacy_android.feature.network.achieve.AchievementResponse
import com.legacy.legacy_android.feature.network.achieve.AchievementService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchieveRepository @Inject constructor(
    private val achievementService: AchievementService
){
    suspend fun fetchAllAchievement(): Result<List<AchievementResponse>> {
        return try {
            val response = achievementService.getAchievements()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("도전과제 전체 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("AchieveRepository", "도전과제를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }

    suspend fun fetchAchievementById(type: String): Result<List<AchievementResponse>>{
        return try{
            val response = achievementService.getAchievementsByType(type)
            val data = response.data
            if(data != null){
                Result.success(data)
            }else{
                Result.failure(NullPointerException("도전과제 데이터가 null입니다."))
            }
        }catch (e: Exception){
            Log.e("AchieveRepository", "도전과제를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }

    suspend fun claimAward(): Result<AchievementAwardResponse>{
        return try{
            val response = achievementService.getAwardAchievement()
            val data = response.data
            if(data != null){
                Result.success(data[0])
            }else{
                Result.failure(NullPointerException("도전과제 데이터가 null입니다."))
            }
        }catch (e: Exception){
            Log.e("AchieveRepository", "도전과제를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }
}