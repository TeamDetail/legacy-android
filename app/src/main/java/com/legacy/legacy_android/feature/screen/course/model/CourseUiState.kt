package com.legacy.legacy_android.feature.screen.course.model

import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse


data class CourseUiState(
    val allCourse: List<AllCourseResponse> = emptyList(),
    val popularCourse: List<AllCourseResponse> = emptyList(),
    val recentCourse: List<AllCourseResponse> = emptyList(),
    val eventCourse: List<AllCourseResponse> = emptyList()
)
