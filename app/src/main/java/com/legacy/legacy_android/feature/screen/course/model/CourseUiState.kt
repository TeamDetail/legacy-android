package com.legacy.legacy_android.feature.screen.course.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse


data class CourseUiState(
    // dropdown value
    val selectedEvent: String = "전체",
    val selectedNew: String = "최신",
    val selectedStatus: String = "전체",
    // normal value
    val allCourse: List<SearchCourseResponse> = emptyList(),
    val popularCourse: List<SearchCourseResponse> = emptyList(),
    val recentCourse: List<SearchCourseResponse> = emptyList(),
    val eventCourse: List<SearchCourseResponse> = emptyList(),
    val currentCourse: AllCourseResponse? = null,
    val searchCourse: List<SearchCourseResponse> = emptyList(),
    val searchCourseName: String = "",
    // create value
    val createCourseName: String = "",
    val createRuinsName: String = "",
    val createCourseHashName: String = "",
    val createCourseHashTags: List<String> = emptyList(),
    val isHashTag: MutableState<Boolean> = mutableStateOf(false),
    val createSearchRuins: List<RuinsIdResponse>? = emptyList(),
    val createSelectedRuins: List<RuinsIdResponse>? = emptyList(),
    val createCourseDescription: String = "",
    val isCreateLoading: Boolean = false,
    val isLoading: Boolean = false
) {
    val displayedCourses: List<SearchCourseResponse>
        get() {
            val sourceCourses = searchCourse.ifEmpty { allCourse }

            return sourceCourses
                .filter { course ->
                    val eventMatch = when (selectedEvent) {
                        "전체" -> true
                        "이벤트" -> course.eventId != 0
                        "일반" -> course.eventId == 0
                        else -> true
                    }

                    val statusMatch = when (selectedStatus) {
                        "전체" -> true
                        "클리어" -> course.clear
                        "미클리어" -> !course.clear
                        else -> true
                    }

                    eventMatch && statusMatch
                }
                .let { filtered ->
                    when (selectedNew) {
                        "최신" -> filtered.sortedByDescending { it.courseId }
                        "인기순" -> filtered.sortedByDescending { it.heartCount }
                        "클리어순" -> filtered.sortedByDescending { it.clearCount }
                        else -> filtered
                    }
                }
        }
}
