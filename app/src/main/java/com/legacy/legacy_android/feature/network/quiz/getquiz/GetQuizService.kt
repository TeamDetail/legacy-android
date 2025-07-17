package com.legacy.legacy_android.feature.network.quiz.getquiz

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GetQuizService {
    @GET("/quiz/{ruinsId}")
    suspend fun getQuizById(
        @Path("ruinsId") ruinsId : Int?
    ): BaseResponse<List<GetQuizResponse>>
}