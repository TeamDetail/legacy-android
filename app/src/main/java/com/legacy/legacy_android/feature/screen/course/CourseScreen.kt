package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.bars.CustomSearchBar
import com.legacy.legacy_android.res.component.button.CustomDropdown
import com.legacy.legacy_android.res.component.course.CourseBox
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

    LaunchedEffect(Unit) {
        viewModel.loadAllCourses()
    }
    val query = remember { mutableStateOf("") }

            CourseScreenLayout(
                modifier = modifier,
                navHostController = navHostController,
                viewModel = viewModel
            ) {
                TitleBox(title = "코스", image = R.drawable.course)
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
                    CustomSearchBar(
                        query = query,
                        placeholder = "코스 이름으로 검색",
                        onSearch = { keyword ->
                            println("검색 실행: $keyword")
                            viewModel.searchCourses(query.value)
                        },
                        modifier = modifier
                    )
                    Row (
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        CustomDropdown(
                            options = viewModel.eventList,
                            selected = viewModel.uiState.selectedEvent,
                            setSelect = { viewModel.setSelectedEventList(it) }
                        )
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ){
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

                    if (viewModel.uiState.searchCourse.isEmpty()) {
                        viewModel.uiState.allCourse.forEach { course ->
                            CourseBox(course = course, viewModel = viewModel, navHostController)
                        }
                    } else {
                        viewModel.uiState.searchCourse.forEach { course ->
                            CourseBox(course = course, viewModel = viewModel, navHostController)
                        }
                    }
                }
            }
        }