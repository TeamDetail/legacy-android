package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.course.SmallCourseWrap
import com.legacy.legacy_android.res.component.layout.CourseScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun CourseCategory(
    modifier: Modifier,
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

        CustomButton(
            onClick = { navHostController.navigate("COURSE") },
            text = "목록으로 보기",
            modifier = Modifier.fillMaxWidth(),
            borderColor = Line_Alternative,
            textColor = Label,
            backgroundColor = Fill_Normal,
            contentPadding = PaddingValues(vertical = 8.dp),
            fontSize = AppTextStyles.Body1.bold.fontSize
        )
        Spacer(modifier.height(12.dp))
    }
}