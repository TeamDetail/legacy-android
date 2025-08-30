package com.legacy.legacy_android.feature.network.course.search

data class SearchCourseResponse(
    val courseId: Int,
    val courseName: String,
    val creator: String,
    val tag: List<String>,
    val description: String,
    val heartCount: Int,
    val clearCount: Int,
    val eventId: Int,
    val thumbnail: String,
    val clearRuinsCount: Int,
    val maxRuinsCount: Int,
    val clear: Boolean,
    val heart: Boolean
)