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
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.feature.screen.course.model.CourseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val ruinsRepository: RuinsRepository
): ViewModel(){

    var uiState by mutableStateOf(CourseUiState())
        private set

    fun updateCourseStatus(status: CourseStatus){
        uiState = uiState.copy(courseStatus = status)
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

    fun searchRuins(name: String){
        viewModelScope.launch {
            ruinsRepository.getSearchRuins(name)
                .onSuccess { ruins -> uiState = uiState.copy(createSearchRuins = ruins) }
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
    fun setCurrentCourse(course: AllCourseResponse){
        uiState = uiState.copy(currentCourse = course)
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
            createSelectedRuins = emptyList()
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

        if (name.isBlank() || tags.isEmpty() || ruins.isNullOrEmpty()) {
            println("덜채움")
            return
        }

        viewModelScope.launch {
            val data = CreateCourseRequest(
                name = name,
                tag = tags,
                description = "",
                ruinsId = ruins.map { it.ruinsId }
            )

            courseRepository.createCourse(data)
                .onSuccess {
                    updateCourseStatus(CourseStatus.ALL)
                    println("Course 만들기 성공")
                    initCreateCourse()
                    loadAllCourses()
                }
                .onFailure { e ->
                    println("Course 만들기 실패: ${e.message}")
                }
        }
    }


    fun setCreateSearchCourseName(name: String){
        uiState = uiState.copy(searchCourseName = name)
        searchCourses(name)
    }
}