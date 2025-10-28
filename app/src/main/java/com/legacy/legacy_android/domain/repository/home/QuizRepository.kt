package com.legacy.legacy_android.domain.repository.home

import android.util.Log
import com.legacy.legacy_android.feature.network.quiz.GetQuizResponse
import com.legacy.legacy_android.feature.network.quiz.PostQuizAnswerRequest
import com.legacy.legacy_android.feature.network.quiz.PostQuizAnswerResponse
import com.legacy.legacy_android.feature.network.quiz.QuizService
import javax.inject.Inject
import javax.inject.Singleton

data class QuizAnswer(val quizId: Int, val answerOption: String)

@Singleton
class QuizRepository @Inject constructor(
    private val quizService: QuizService,
){
    suspend fun getQuizById(ruinsId:Int?): Result<List<GetQuizResponse>?>{
        return try{
            val response = quizService.getQuizById(ruinsId)
            Result.success(if(!response.data.isNullOrEmpty())response.data else null)
        }catch (e:Exception){
            Log.d("getQuizById", "에러 발생")
            Result.failure(e)
        }
    }
    suspend fun submitAnswer(answers: List<QuizAnswer>): Result<PostQuizAnswerResponse?>{
        return try{
            val request = answers.map {
                PostQuizAnswerRequest(quizId = it.quizId, answerOption = it.answerOption)
            }
            val response = quizService.getQuizAnswer(request)
            Result.success(response.data)
        }catch(e:Exception){
            Log.d("submitAnswer", "정답 문제 발생")
            Log.d("submitAnswer", e.toString())
            Result.failure(e)
        }
    }

    suspend fun getQuiz(quizId:Int?): Result<String?>{
        return try{
            val response = quizService.getQuizHint(quizId)
            Result.success(response.data)
        }catch(e:Exception){
            Log.d("getQuiz", "힌트 문제 발생")
            Log.d("getQuiz", e.toString())
            Result.failure(e)
        }
    }
}
