package com.legacy.legacy_android.feature.screen.course.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse

enum class CourseStatus { ALL, CATEGORY, INFO, CREATE}

data class CourseUiState(
    // dropdown value
    val statusSelected: String = "",
    val newList: String = "",
    val eventList: String = "",
    // normal value
    val allCourse: List<SearchCourseResponse> = emptyList(),
    val popularCourse: List<SearchCourseResponse> = emptyList(),
    val recentCourse: List<SearchCourseResponse> = emptyList(),
    val eventCourse: List<SearchCourseResponse> = emptyList(),
    val courseStatus: CourseStatus = CourseStatus.CATEGORY,
    val currentCourse: AllCourseResponse? = null,
    val searchCourse: List<SearchCourseResponse> = emptyList(),
    val searchCourseName: String ="",
    // create value
    val createCourseName: String = "",
    val createRuinsName: String = "",
    val createCourseHashName: String ="",
    val createCourseHashTags: List<String> = emptyList(),
    val isHashTag: MutableState<Boolean> = mutableStateOf(false),
    val createSearchRuins: List<RuinsIdResponse>? = emptyList(),
    val createSelectedRuins: List<RuinsIdResponse>? = emptyList(),
    val createCourseDescription: String = ""
)
