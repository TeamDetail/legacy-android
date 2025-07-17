package com.legacy.legacy_android.feature.network.quiz.postQuizAnswer

data class PostQuizAnswerWrapper(
    val data: PostQuizAnswerResponse,
    val status: Int
)

data class PostQuizAnswerResponse(
    val blockGiven: Boolean,
    val results: List<Results>
)

data class Results(
    val quizId: Int,
    val isCorrect: Boolean
)
