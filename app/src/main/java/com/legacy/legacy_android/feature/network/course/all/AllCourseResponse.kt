package com.legacy.legacy_android.feature.network.course.all

data class AllCourseResponse (
    val courseId: Int,
    val courseName: String,
    val creator: String,
    val tag: List<String>,
    val ruinsID: List<Int>,
    val description: String,
    val heartCount: Int,
    val clearCount: Int,
    val thumbnail: String,
    val clearRuins: List<String>,
    val clear: Boolean,
    val eventCourse: Boolean,
    val heart: Boolean
)