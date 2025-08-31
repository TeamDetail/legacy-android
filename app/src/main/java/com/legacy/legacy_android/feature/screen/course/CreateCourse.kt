package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.course.PlusButton
import com.legacy.legacy_android.res.component.course.PlusInput
import com.legacy.legacy_android.res.component.course.RuinsBox
import com.legacy.legacy_android.res.component.course.SearchCourseBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Background_Netural
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.bitbit

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateCourse(modifier: Modifier, viewModel: CourseViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.initCreateCourse()
    }

    val isEnabled = viewModel.uiState.createCourseName.isNotBlank() &&
            viewModel.uiState.createCourseHashTags.isNotEmpty() &&
            !viewModel.uiState.createSelectedRuins.isNullOrEmpty()


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
                            viewModel.loadAllCourses()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                viewModel.uiState.createSelectedRuins?.forEachIndexed { index, item ->
                    Column(
                        modifier = Modifier.width(120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                modifier = Modifier
                                    .padding(vertical = 2.dp),
                                color = White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                                .clip(RoundedCornerShape(12.dp))
                                .padding(5.dp)
                        ) {
                            AsyncImage(
                                model = item.ruinsImage,
                                contentDescription = item.name,
                                modifier = Modifier
                                    .height(144.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, Line_Netural, RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.school_img),
                                placeholder = painterResource(R.drawable.school_img)
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .width(144.dp)
                                    .background(Background_Normal.copy(alpha = 0.5f))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    text = item.name,
                                    fontFamily = bitbit,
                                    fontSize = 16.sp,
                                    color = Label
                                )
                            }
                        }
                    }
                }
            }
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                SearchCourseBox(modifier, viewModel)
                viewModel.uiState.createSearchRuins?.forEach {it ->
                    RuinsBox(data = it, viewModel)
                }
            }
        }
        Row(
           horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        Fill_Normal,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth(0.9f)
                    .border(
                        1.dp,
                        color = if (isEnabled) Blue_Netural else Label_Alternative,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .then(
                        if (isEnabled) Modifier.clickable { viewModel.createCourse() }
                        else Modifier
                    )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "코스 제작 완료!",
                    color = if (isEnabled) Blue_Netural else Label_Alternative,
                    style = AppTextStyles.Body1.bold
                )
            }
        }
    }
}