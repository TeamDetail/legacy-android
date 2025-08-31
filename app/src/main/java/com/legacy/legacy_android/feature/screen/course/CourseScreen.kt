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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.bars.CustomDropDown
import com.legacy.legacy_android.res.component.bars.CustomSearchBar
import com.legacy.legacy_android.res.component.course.CourseBox
import com.legacy.legacy_android.res.component.course.CourseInfo
import com.legacy.legacy_android.res.component.course.SmallCourseWrap
import com.legacy.legacy_android.res.component.layout.CommonScreenLayout
import com.legacy.legacy_android.res.component.layout.CourseScreenLayout
import com.legacy.legacy_android.res.component.title.TitleBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Line_Alternative

@Composable
fun CourseScreen(
    modifier: Modifier = Modifier,
    viewModel: CourseViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val query = remember { mutableStateOf("") }
    val isRefreshing = viewModel.uiState.isRefreshing
    LaunchedEffect(Unit) {
        viewModel.loadAllCourses()
        viewModel.loadPopularCourses()
        viewModel.loadRecentCourses()
        viewModel.loadEventCourses()
    }

        when (viewModel.uiState.courseStatus) {
            CourseStatus.INFO -> {
                CourseInfo(
                    course = viewModel.uiState.currentCourse,
                    modifier = modifier,
                    viewModel = viewModel
                )
            }

            CourseStatus.CREATE -> {
                CreateCourse(modifier, viewModel)
            }

            else -> {
                CourseScreenLayout(
                    modifier = modifier,
                    navHostController = navHostController,
                    viewModel = viewModel
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
                                .border(
                                    1.dp,
                                    color = Line_Alternative,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    viewModel.updateCourseStatus(CourseStatus.ALL)
                                    viewModel.loadAllCourses()
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
                            // 탑바
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
                                        viewModel.updateCourseStatus(CourseStatus.CATEGORY)
                                        viewModel.loadEventCourses()
                                        viewModel.loadPopularCourses()
                                        viewModel.loadRecentCourses()
                                    }
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    text = "추천 페이지로 보기",
                                    color = Label,
                                    style = AppTextStyles.Body1.bold
                                )
                            }
                            CustomSearchBar(
                                query = query,
                                placeholder = "코스 이름으로 검색",
                                onSearch = { keyword ->
                                    println("검색 실행: $keyword")
                                    viewModel.searchCourses(query.value)
                                },
                                modifier = modifier
                            )
//                            CustomDropDown(
//
//                            ) { }

                            if (viewModel.uiState.searchCourse.isEmpty()) {
                                viewModel.uiState.allCourse.forEach { course ->
                                    CourseBox(course = course, viewModel = viewModel)
                                }
                            } else {
                                viewModel.uiState.searchCourse.forEach { course ->
                                    CourseBox(course = course, viewModel = viewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }