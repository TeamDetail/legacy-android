package com.legacy.legacy_android.feature.network.quiz.postQuizAnswer

data class PostQuizAnswerRequest(
    val quizId: Int,
    val answerOption: String
)