package com.legacy.legacy_android.feature.network.quiz

data class PostQuizAnswerResponse(
    val blockGiven: Boolean,
    val results: List<Results>
)

data class Results(
    val quizId: Int,
    val isCorrect: Boolean
)
