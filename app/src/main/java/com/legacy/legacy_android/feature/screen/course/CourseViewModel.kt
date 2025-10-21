package com.legacy.legacy_android.feature.screen.course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.course.CourseRepository
import com.legacy.legacy_android.domain.repository.home.RuinsRepository
import com.legacy.legacy_android.feature.network.course.all.CreateCourseRequest
import com.legacy.legacy_android.feature.network.course.all.PatchHeartRequest
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.course.model.CourseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val ruinsRepository: RuinsRepository,
) : ViewModel() {

    var uiState by mutableStateOf(CourseUiState())
        private set

    var navigateBack by mutableStateOf(false)
        private set

    val statusList = listOf("전체", "미완료", "완료")
    val newList = listOf("최신", "인기", "클리어 인원")
    val eventList = listOf("전체", "일반", "이벤트")

    fun setSelectedEventList(status: String) {
        uiState = uiState.copy(selectedEvent = status)
    }

    fun setSelectedNewList(status: String) {
        uiState = uiState.copy(selectedNew = status)
    }

    fun setSelectedStatusList(status: String) {
        uiState = uiState.copy(selectedStatus = status)
    }

    fun setCreateDescription(description: String) {
        uiState = uiState.copy(createCourseDescription = description)
    }

    fun getFilteredCourses(): List<SearchCourseResponse> {
        val courses = uiState.searchCourse.ifEmpty { uiState.allCourse }
        return courses
            .filter { course ->
                when (uiState.selectedEvent) {
                    "전체" -> true
                    "이벤트" -> course.eventId != 0
                    "일반" -> course.eventId == 0
                    else -> true
                }
            }
            .filter { course ->
                when (uiState.selectedStatus) {
                    "전체" -> true
                    "완료" -> course.clear
                    "미완료" -> !course.clear
                    else -> true
                }
            }
            .let { filteredList ->
                when (uiState.selectedNew) {
                    "최신" -> filteredList
                    "인기" -> filteredList.sortedByDescending { it.heartCount }
                    "클리어 인원" -> filteredList.sortedByDescending { it.clearCount }
                    else -> filteredList
                }
            }
    }

    fun filterRuinElem(item: RuinsIdResponse) {
        uiState = uiState.copy(
            createSelectedRuins = uiState.createSelectedRuins
                ?.filter { it.ruinsId != item.ruinsId }
        )
    }

    private inline fun launchWithLoading(
        crossinline block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                block()
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun loadAllCourses() = launchWithLoading {
        courseRepository.getAllCourse()
            .onSuccess { courses ->
                uiState = uiState.copy(allCourse = courses)
            }
            .onFailure { e ->
                println("모든 코스 불러오기 실패: ${e.message}")
            }
    }

    fun loadPopularCourses() = launchWithLoading {
        courseRepository.getPopularCourse()
            .onSuccess { courses ->
                uiState = uiState.copy(popularCourse = courses)
            }
            .onFailure { e ->
                println("인기 코스 불러오기 실패: ${e.message}")
            }
    }

    fun loadRecentCourses() = launchWithLoading {
        courseRepository.getRecentCourse()
            .onSuccess { courses ->
                uiState = uiState.copy(recentCourse = courses)
            }
            .onFailure { e ->
                println("최신 코스 불러오기 실패: ${e.message}")
            }
    }

    fun loadEventCourses() = launchWithLoading {
        courseRepository.getEventCourse()
            .onSuccess { courses ->
                uiState = uiState.copy(eventCourse = courses)
            }
            .onFailure { e ->
                println("이벤트 코스 불러오기 실패: ${e.message}")
            }
    }

    fun searchCourses(name: String) = launchWithLoading {
        courseRepository.getSearchCourse(name)
            .onSuccess { courses ->
                uiState = uiState.copy(searchCourse = courses)
            }
            .onFailure { e ->
                println("코스 검색 실패: ${e.message}")
            }
    }

    fun setCurrentCourse(course: SearchCourseResponse) {
        viewModelScope.launch {
            courseRepository.getCourseById(course.courseId)
                .onSuccess { detail ->
                    uiState = uiState.copy(currentCourse = detail)
                }
                .onFailure { e ->
                    println("코스 상세 불러오기 실패: ${e.message}")
                }
        }
    }

    fun patchHeart(courseId: Int) {
        viewModelScope.launch {
            val request = PatchHeartRequest(courseId)
            courseRepository.patchHeart(request)
                .onSuccess {
                    delay(200L)
                    uiState.currentCourse?.let { course ->
                        val isHearted = !course.heart
                        val newHeartCount =
                            if (isHearted) course.heartCount + 1 else course.heartCount - 1
                        uiState = uiState.copy(
                            currentCourse = course.copy(
                                heart = isHearted,
                                heartCount = newHeartCount.coerceAtLeast(0)
                            )
                        )
                    }
                }
                .onFailure { e ->
                    println("좋아요 업데이트 실패: ${e.message}")
                }
        }
    }

    fun searchRuins(name: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isCreateLoading = true, createSearchRuins = null)
            ruinsRepository.getSearchRuins(name)
                .onSuccess { ruins ->
                    uiState = uiState.copy(createSearchRuins = ruins)
                }
                .onFailure { e ->
                    println("유적 검색 실패: ${e.message}")
                }
            uiState = uiState.copy(isCreateLoading = false)
        }
    }

    fun initCreateCourse() {
        uiState = uiState.copy(
            createRuinsName = "",
            createCourseHashName = "",
            createCourseHashTags = emptyList(),
            isHashTag = mutableStateOf(false),
            createCourseName = "",
            createSearchRuins = emptyList(),
            createSelectedRuins = emptyList(),
            createCourseDescription = ""
        )
    }

    fun setCreateRuinsName(name: String) {
        uiState = uiState.copy(createRuinsName = name)
    }

    fun setCreateCourseHashName(name: String) {
        uiState = uiState.copy(createCourseHashName = name)
    }

    fun setCreateCourseHashTags(tag: String) {
        uiState = uiState.copy(createCourseHashTags = uiState.createCourseHashTags + tag)
    }

    fun setIsHashTag() {
        uiState = uiState.copy(isHashTag = mutableStateOf(!uiState.isHashTag.value))
    }

    fun setCreateCourseName(name: String) {
        uiState = uiState.copy(createCourseName = name)
    }

    fun setCreateSelectedRuins(ruin: RuinsIdResponse?) {
        if (ruin == null || uiState.createSelectedRuins!!.contains(ruin)) return
        uiState = uiState.copy(
            createSelectedRuins = (uiState.createSelectedRuins ?: emptyList()) + ruin
        )
    }

    fun createCourse() {
        val name = uiState.createCourseName
        val tags = uiState.createCourseHashTags
        val ruins = uiState.createSelectedRuins
        val description = uiState.createCourseDescription

        if (name.isBlank() || tags.isEmpty() || ruins.isNullOrEmpty()) {
            println("Course 생성 실패: 필수 항목 누락")
            return
        }

        launchWithLoading {
            val data = CreateCourseRequest(
                name = name,
                tag = tags,
                description = description,
                ruinsId = ruins.map { it.ruinsId }
            )

            courseRepository.createCourse(data)
                .onSuccess {
                    navigateBack = true
                    println("Course 생성 성공")
                    initCreateCourse()
                    loadAllCourses()
                }
                .onFailure { e ->
                    println("Course 생성 실패: ${e.message}")
                }
        }
    }

    fun onNavigated() {
        navigateBack = false
    }
}
