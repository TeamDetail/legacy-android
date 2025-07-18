package com.legacy.legacy_android.domain.repository.home

import android.util.Log
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizService
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerRequest
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerService
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerWrapper
import javax.inject.Inject
import javax.inject.Singleton

data class QuizAnswer(val quizId: Int, val answer: String)

@Singleton
class QuizRepository @Inject constructor(
    private val getQuizService: GetQuizService,
    private val postQuizAnswerService: PostQuizAnswerService
){
    suspend fun getQuizById(ruinsId:Int?): Result<List<GetQuizResponse>?>{
        return try{
            val response = getQuizService.getQuizById(ruinsId)
            Result.success(if(!response.data.isNullOrEmpty())response.data else null)
        }catch (e:Exception){
            Log.d("getQuizById", "에러 발생")
            Result.failure(e)
        }
    }
    suspend fun submitAnswer(answers: List<QuizAnswer>): Result<PostQuizAnswerWrapper>{
        return try{
            val request = answers.map {
                PostQuizAnswerRequest(quizId = it.quizId, answerOption = it.answer)
            }
            val response = postQuizAnswerService.answer(request)
            Result.success(response)
        }catch(e:Exception){
            Log.d("submitAnswer", "정답 문제 발생")
            Result.failure(e)
        }
    }
}
