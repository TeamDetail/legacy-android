package com.legacy.legacy_android.feature.network.quiz

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizService {
    @POST("/quiz/check")
    suspend fun getQuizAnswer(
        @Body request: List<PostQuizAnswerRequest>
    ): BaseResponse<PostQuizAnswerResponse>

    @GET("/quiz/{ruinsId}")
    suspend fun getQuizById(
        @Path("ruinsId") ruinsId : Int?
    ): BaseResponse<List<GetQuizResponse>>

    @GET("/quiz/hint/{quizId}")
    suspend fun getQuizHint(
        @Path("quizId") quizId : Int?
    ): BaseResponse<String>
}