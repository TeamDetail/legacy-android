package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.course.SmallCourseWrap
import com.legacy.legacy_android.res.component.layout.CourseScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun CourseCategory(modifier: Modifier,
                   viewModel: CourseViewModel = hiltViewModel(),
                   navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.loadEventCourses()
        viewModel.loadPopularCourses()
        viewModel.loadRecentCourses()
    }

    CourseScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
        viewModel = viewModel
    ) {
        TitleBox(title = "코스", image = R.drawable.course)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            SmallCourseWrap(
                type = "popular",
                data = viewModel.uiState.popularCourse,
                modifier = modifier,
                viewModel = viewModel,
                navHostController = navHostController
            )
            SmallCourseWrap(
                type = "event",
                data = viewModel.uiState.eventCourse,
                modifier = modifier,
                viewModel = viewModel,
                navHostController = navHostController
            )
            SmallCourseWrap(
                type = "recent",
                data = viewModel.uiState.recentCourse,
                modifier = modifier,
                viewModel = viewModel,
                navHostController = navHostController
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = Line_Alternative,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    navHostController.navigate("COURSE")
                }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "목록으로 보기",
                color = Label,
                style = AppTextStyles.Body1.bold
            )
        }
    }
}