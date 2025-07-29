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
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.course.CourseBox
import com.legacy.legacy_android.res.component.course.CourseInfo
import com.legacy.legacy_android.res.component.course.SmallCourseWrap
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun CourseScreen(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.loadAllCourses()
        viewModel.loadPopularCourses()
        viewModel.loadRecentCourses()
        viewModel.loadEventCourses()
    }

    when (viewModel.uiState.courseStatus) {
        CourseStatus.INFO-> {
            CourseInfo(course = viewModel.uiState.currentCourse, modifier = modifier, viewModel = viewModel)
        }

        else -> {
            CommonScreenLayout(
                modifier = modifier,
                navHostController = navHostController
            ) {
                TitleBox(title = "코스", image = R.drawable.course)

                if (viewModel.uiState.courseStatus == CourseStatus.CATEGORY) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = modifier.fillMaxWidth()
                    ) {
                        SmallCourseWrap(
                            type = "popular",
                            data = viewModel.uiState.popularCourse,
                            modifier = modifier,
                            viewModel = viewModel
                        )
                        SmallCourseWrap(
                            type = "event",
                            data = viewModel.uiState.allCourse,
                            modifier = modifier,
                            viewModel = viewModel
                        )
                        SmallCourseWrap(
                            type = "recent",
                            data = viewModel.uiState.allCourse,
                            modifier = modifier,
                            viewModel = viewModel
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .border(1.dp, color = Line_Alternative, shape = RoundedCornerShape(12.dp))
                            .clickable {
                                viewModel.updateCourseStatus(CourseStatus.ALL)
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "목록으로 보기",
                            color = Label,
                            style = AppTextStyles.Body1.bold
                        )
                    }
                } else if (viewModel.uiState.courseStatus == CourseStatus.ALL) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.uiState.allCourse.forEach { course ->
                            CourseBox(course = course, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
