package com.legacy.legacy_android.feature.screen.course.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse

enum class CourseStatus { ALL, CATEGORY, INFO, CREATE}

data class CourseUiState(
    val allCourse: List<AllCourseResponse> = emptyList(),
    val popularCourse: List<AllCourseResponse> = emptyList(),
    val recentCourse: List<AllCourseResponse> = emptyList(),
    val eventCourse: List<AllCourseResponse> = emptyList(),
    val courseStatus: CourseStatus = CourseStatus.CATEGORY,
    val currentCourse: AllCourseResponse? = null,
    val searchCourse: List<SearchCourseResponse> = emptyList(),
    val searchCourseName: String ="",
    val createCourseName: String = "",
    val createRuinsName: String = "",
    val createCourseHashName: String ="",
    val createCourseHashTags: List<String> = emptyList(),
    val isHashTag: MutableState<Boolean> = mutableStateOf(false),
    val createSearchRuins: List<RuinsIdResponse>? = emptyList()
)
