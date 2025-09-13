package com.legacy.legacy_android.feature.screen.course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.course.CourseRepository
import com.legacy.legacy_android.domain.repository.home.RuinsRepository
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.all.CreateCourseRequest
import com.legacy.legacy_android.feature.network.course.all.PatchHeartRequest
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.feature.screen.course.model.CourseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val ruinsRepository: RuinsRepository,

): ViewModel(){

    var uiState by mutableStateOf(CourseUiState())
        private set

    var navigateBack by mutableStateOf(false)
        private set

    val statusList = listOf( "전체", "미완료", "완료")
    val newList = listOf("최신", "인기", "클리어 인원")
    val eventList = listOf("전체", "일반", "이벤트")

    fun setSelectedEventList(status: String){
        uiState = uiState.copy(selectedEvent = status)
    }

    fun setSelectedNewList(status: String){
        uiState = uiState.copy(selectedNew = status)
    }

    fun setSelectedStatusList(status: String){
        uiState = uiState.copy(selectedStatus = status)
    }

    fun setCreateDescription(description: String){
        uiState = uiState.copy(createCourseDescription = description)

    }

    fun filterRuinElem(item: RuinsIdResponse) {
        uiState = uiState.copy(
            createSelectedRuins = uiState.createSelectedRuins
                ?.filter { it.ruinsId != item.ruinsId }
        )
    }

    fun loadAllCourses() {
        viewModelScope.launch {
            courseRepository.getAllCourse()
                .onSuccess { course -> uiState = uiState.copy(allCourse = course) }
        }
    }

    fun loadPopularCourses() {
        viewModelScope.launch {
            courseRepository.getPopularCourse()
                .onSuccess { course -> uiState = uiState.copy(popularCourse = course) }
        }
    }


    fun searchCourses(name: String){
        viewModelScope.launch {
            courseRepository.getSearchCourse(name)
                .onSuccess { course -> uiState = uiState.copy(searchCourse = course) }
        }
    }

    fun onNavigated() {
        navigateBack = false
    }

    fun searchRuins(name: String){
        viewModelScope.launch {
            uiState = uiState.copy(isCreateLoading = true)
            uiState = uiState.copy(createSearchRuins = null)
            ruinsRepository.getSearchRuins(name)
                .onSuccess {
                    ruins -> uiState = uiState.copy(createSearchRuins = ruins)
                }
            uiState = uiState.copy(isCreateLoading = false)
        }
    }

    fun loadRecentCourses() {
        viewModelScope.launch {
            courseRepository.getRecentCourse()
                .onSuccess { course -> uiState = uiState.copy(recentCourse = course) }
        }
    }
    fun loadEventCourses() {
        viewModelScope.launch {
            courseRepository.getEventCourse()
                .onSuccess { course -> uiState = uiState.copy(eventCourse = course) }
        }
    }

    fun setCurrentCourse(course: SearchCourseResponse){
        viewModelScope.launch {
            courseRepository.getCourseById(course.courseId)
                .onSuccess { course -> uiState = uiState.copy(currentCourse = course) }
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
                        val newHeartCount = if (isHearted) course.heartCount + 1 else course.heartCount - 1
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

    // 여기가 createCourse
    fun initCreateCourse(){
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
    fun setCreateRuinsName(name: String){
        uiState = uiState.copy(createRuinsName = name)
    }
    fun setCreateCourseHashName(name: String){
        uiState = uiState.copy(createCourseHashName = name)
    }
    fun setCreateCourseHashTags(tag: String){
        uiState = uiState.copy(createCourseHashTags = uiState.createCourseHashTags + tag)
    }
    fun setIsHashTag(){
        uiState = uiState.copy(isHashTag = mutableStateOf(!uiState.isHashTag.value))
    }
    fun setCreateCourseName(name: String){
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
            println("덜채움")
            return
        }

        viewModelScope.launch {
            val data = CreateCourseRequest(
                name = name,
                tag = tags,
                description = description,
                ruinsId = ruins.map { it.ruinsId }
            )

            courseRepository.createCourse(data)
                .onSuccess {
                    navigateBack = true
                    println("Course 만들기 성공")
                    initCreateCourse()
                    loadAllCourses()
                }
                .onFailure { e ->
                    println("Course 만들기 실패: ${e.message}")
                }
        }
    }
}