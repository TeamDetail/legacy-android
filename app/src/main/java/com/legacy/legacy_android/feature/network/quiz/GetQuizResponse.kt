package com.legacy.legacy_android.feature.network.quiz

data class GetQuizResponse(
    val quizId: Int,
    val quizProblem: String,
    val ruinsName: String,
    val optionValue: List<String>
    )