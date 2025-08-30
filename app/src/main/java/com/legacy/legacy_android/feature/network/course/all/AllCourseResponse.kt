 package com.legacy.legacy_android.feature.network.course.all

data class AllCourseResponse (
    val courseId: Int,
    val courseName: String,
    val creator: String,
    val tag: List<String>,
    val ruinsId: List<Int>,
    val ruins: List<String>,
    val description: String,
    val heartCount: Int,
    val clearCount: Int,
    val thumbnail: String,
    val clearRuins: List<String>,
    val isClear: Boolean,
    val clear: Boolean,
    val eventCourse: Boolean,
    val heart: Boolean
)