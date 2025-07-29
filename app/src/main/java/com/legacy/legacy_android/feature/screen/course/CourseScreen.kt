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
        if (viewModel.uiState.courseStatus == CourseStatus.CATEGORY) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                // 콘텐츠 넣는 곳
                SmallCourseWrap(
                    type = "popular",
                    data = viewModel.uiState.popularCourse,
                    modifier = modifier,
                )
                SmallCourseWrap(
                    type = "event",
                    data = viewModel.uiState.allCourse,
                    modifier = modifier,
                )
                SmallCourseWrap(
                    type = "recent",
                    data = viewModel.uiState.allCourse,
                    modifier = modifier,
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Line_Alternative, shape = RoundedCornerShape(12.dp))
                    .clickable(
                        onClick = {
                            viewModel.updateCourseStatus(CourseStatus.ALL)
                        }
                    )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "목록으로 보기",
                    color = Label,
                    style = AppTextStyles.Body1.bold
                )
            }
        }else if ((viewModel.uiState.courseStatus == CourseStatus.ALL)){
            //전체로 보여줄 떄
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            viewModel.uiState.allCourse.forEach { course ->
                    CourseBox(course = course)
                }
            }
        }else{

        }
    }
}