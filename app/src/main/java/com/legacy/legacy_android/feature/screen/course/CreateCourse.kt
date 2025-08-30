package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.course.PlusButton
import com.legacy.legacy_android.res.component.course.PlusInput
import com.legacy.legacy_android.res.component.course.SearchCourseBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.White

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateCourse(modifier: Modifier, viewModel: CourseViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.initCreateCourse()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
                .padding(vertical = 40.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopStart)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(enabled = !isLoading.value) {
                        coroutineScope.launch {
                            isLoading.value = true
                            delay(100)
                            viewModel.updateCourseStatus(CourseStatus.ALL)
                        }
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                )
                Text(
                    text = "목록으로",
                    style = AppTextStyles.Heading1.bold
                )
            }
            // 이름 적는 곳
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = modifier.fillMaxWidth()
                        .background(shape = RoundedCornerShape(12.dp), color = Background_Netural),
                    value = viewModel.uiState.createCourseName,
                    onValueChange = { viewModel.setCreateCourseName(it) },
                    placeholder = { Text(text = "코스 이름을 입력해주세요..", color = White) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedContainerColor = Background_Normal,
                        unfocusedContainerColor = Background_Normal,
                        disabledContainerColor = Background_Normal,
                        focusedIndicatorColor = Background_Normal,
                        unfocusedIndicatorColor = Background_Normal,
                        disabledIndicatorColor = Background_Normal
                    )
                )
                // 해시태그 설정
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.uiState.createCourseHashTags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(color = Fill_Normal, shape = RoundedCornerShape(4.dp))
                                .height(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "# $tag",
                                style = AppTextStyles.Body2.medium.copy(color = White),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                    if (viewModel.uiState.isHashTag.value) {
                        PlusInput(viewModel)
                    } else {
                        PlusButton(viewModel)
                    }
                }
            }
            // 유적지 선택 파트
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "선택된 유적지",
                    style = AppTextStyles.Body1.bold,
                    color = Label_Alternative
                )
            }
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                SearchCourseBox(modifier, viewModel)
            }
        }
    }
}