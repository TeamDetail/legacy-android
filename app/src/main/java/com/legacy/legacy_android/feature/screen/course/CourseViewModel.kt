package com.legacy.legacy_android.feature.screen.course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.course.CourseRepository
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.screen.course.model.CourseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository
): ViewModel(){

    var uiState by mutableStateOf(CourseUiState())
        private set

    fun updateAllCourse(allCourse: List<AllCourseResponse>) {
        uiState = uiState.copy(
           allCourse = allCourse
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
}