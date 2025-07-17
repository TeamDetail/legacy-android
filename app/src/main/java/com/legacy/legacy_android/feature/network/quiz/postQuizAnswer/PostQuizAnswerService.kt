package com.legacy.legacy_android.feature.network.quiz.postQuizAnswer
import retrofit2.http.Body
import retrofit2.http.POST

interface PostQuizAnswerService {
    @POST("/quiz/check")
    suspend fun answer(
        @Body request: List<PostQuizAnswerRequest>
    ): PostQuizAnswerWrapper
}
