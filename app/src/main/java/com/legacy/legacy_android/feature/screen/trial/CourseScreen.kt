package com.legacy.legacy_android.feature.screen.trial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.ComingSoon
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox

@Composable
fun CourseScreen(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel = hiltViewModel(),
    navHostController: NavHostController){
    CommonScreenLayout(
        modifier = modifier,
        navHostController = navHostController
    ) {
        TitleBox(title = "코스", image = R.drawable.course)
        // 여기부터 다음에 없애기
        ComingSoon()
    }
}