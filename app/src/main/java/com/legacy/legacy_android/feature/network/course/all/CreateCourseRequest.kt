package com.legacy.legacy_android.feature.network.course.all

data class CreateCourseRequest(
    val name: String,
    val tag: List<String>,
    val description: String,
    val ruinsId: List<Int>
)
