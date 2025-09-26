package com.legacy.legacy_android.feature.network.quiz

data class PostQuizAnswerRequest(
    val quizId: Int,
    val answerOption: String
)