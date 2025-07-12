package com.legacy.legacy_android.feature.network.quiz.getquiz

data class GetQuizResponse(
    val quizId: Int,
    val quizProblem: String,
    val optionValue: List<String>
    )
