package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.course.SmallCourseWrap
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox

@Composable
fun CourseScreen(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel = hiltViewModel(),
    navHostController: NavHostController){

    LaunchedEffect(Unit) {
        viewModel.loadAllCourses()
        viewModel.loadPopularCourses()
        viewModel.loadRecentCourses()
        viewModel.loadEventCourses()
    }

    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "코스", image = R.drawable.course)
        Column (verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier.fillMaxWidth()){
            // 콘텐츠 넣는 곳
            SmallCourseWrap(type = "popular", data = viewModel.uiState.popularCourse, modifier = modifier)
            SmallCourseWrap(type = "event", data = viewModel.uiState.allCourse, modifier = modifier)
            SmallCourseWrap(type = "recent", data = viewModel.uiState.allCourse, modifier = modifier)
        }
    }
}