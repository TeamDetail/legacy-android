package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.bars.CustomSearchBar
import com.legacy.legacy_android.res.component.button.CustomDropdown
import com.legacy.legacy_android.res.component.course.CourseBox
import com.legacy.legacy_android.res.component.layout.CourseScreenLayout
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.*

@Composable
fun CourseScreen(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.loadAllCourses()
    }

    val displayedCourses = viewModel.uiState.displayedCourses
    val isLoading = viewModel.uiState.isLoading
    val query = remember { mutableStateOf("") }

    CourseScreenLayout(
        modifier = modifier,
        navHostController = navHostController,
    ) {
        TitleBox(title = "코스", image = R.drawable.course)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            // 추천 페이지 이동 버튼
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Line_Alternative, shape = RoundedCornerShape(12.dp))
                    .clickable {
                        navHostController.navigate("course_category")
                    }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "추천 페이지로 보기",
                    color = Label,
                    style = AppTextStyles.Body1.bold
                )
            }

            // 검색 바
            CustomSearchBar(
                query = query,
                placeholder = "코스 이름으로 검색",
                onSearch = { viewModel.searchCourses(it) },
                modifier = modifier
            )

            // 드롭다운 필터
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDropdown(
                    options = viewModel.eventList,
                    selected = viewModel.uiState.selectedEvent,
                    setSelect = { viewModel.setSelectedEventList(it) }
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CustomDropdown(
                        options = viewModel.newList,
                        selected = viewModel.uiState.selectedNew,
                        setSelect = { viewModel.setSelectedNewList(it) }
                    )
                    CustomDropdown(
                        options = viewModel.statusList,
                        selected = viewModel.uiState.selectedStatus,
                        setSelect = { viewModel.setSelectedStatusList(it) }
                    )
                }
            }

            // 리스트 UI
            when {
                isLoading -> {
                    repeat(5) {
                        SkeletonBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                .height(80.dp)
                        )
                    }
                }

                displayedCourses.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "검색 결과가 없습니다",
                            style = AppTextStyles.Body1.medium,
                            color = Label
                        )
                    }
                }

                else -> {
                    displayedCourses.forEach { course ->
                        CourseBox(
                            course = course,
                            viewModel = viewModel,
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
    }
}
