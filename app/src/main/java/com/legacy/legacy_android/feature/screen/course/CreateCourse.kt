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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.button.CustomButton
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
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.White
import com.legacy.legacy_android.ui.theme.bitbit

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateCourse(modifier: Modifier, viewModel: CourseViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initCreateCourse()
    }

    if (viewModel.navigateBack) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
            viewModel.onNavigated()
        }
    }

    val isEnabled = viewModel.uiState.createCourseName.isNotBlank() &&
            viewModel.uiState.createCourseHashTags.isNotEmpty() &&
            !viewModel.uiState.createSelectedRuins.isNullOrEmpty() &&
            viewModel.uiState.createCourseDescription.isNotBlank() &&
            viewModel.uiState.createSelectedRuins!!.size >= 5

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
            .imePadding()
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
                            navController.popBackStack()
                            isLoading.value = false
                            viewModel.onNavigated()
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
            // 유적지 설명 파트
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "코스 설명", color = Label_Alternative, style = AppTextStyles.Body1.bold)
                TextField(
                    value = viewModel.uiState.createCourseDescription,
                    onValueChange = { viewModel.setCreateDescription(it) },
                    modifier = modifier.fillMaxWidth()
                        .background(color = Background_Normal, shape = RoundedCornerShape(12.dp))
                        .height(120.dp).clip(RoundedCornerShape(12.dp)),
                    placeholder = { Text(text = "코스 설명을 입력해주세요.") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Label, unfocusedTextColor = Label,
                        focusedContainerColor = Fill_Normal,
                        unfocusedContainerColor = Fill_Normal,
                        disabledContainerColor = Fill_Normal,
                        focusedIndicatorColor = Fill_Normal,
                        unfocusedIndicatorColor = Fill_Normal,
                        disabledIndicatorColor = Fill_Normal,
                        unfocusedPlaceholderColor = Label,
                        focusedPlaceholderColor = Label,
                    )
                )
            }
            // 유적지 선택 파트
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "선택된 유적지",
                        color = Label_Alternative,
                        style = AppTextStyles.Body1.bold
                    )
                    Text("(클릭 시 삭제)", color = Label_Assitive, style = AppTextStyles.Label.Medium)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    viewModel.uiState.createSelectedRuins?.forEachIndexed { index, item ->
                        Column(
                            modifier = Modifier.width(120.dp).clickable {
                                viewModel.filterRuinElem(item)
                                println(viewModel.uiState.createSearchRuins)
                            },
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
                                        fontSize = 12.sp,
                                        color = Label
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchCourseBox(modifier, viewModel)
                if (viewModel.uiState.isCreateLoading) {
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("검색 중입니다...", style = AppTextStyles.Label.Bold)
                    }
                } else if (viewModel.uiState.createSearchRuins != null) {
                    viewModel.uiState.createSearchRuins?.forEach { it ->
                        RuinsBox(data = it, viewModel)
                    }
                } else {
                    Row(
                        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("검색 결과가 없습니다.", style = AppTextStyles.Label.Bold)
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ) {
            CustomButton(
                text = "코스 제작 완료!",
                onClick = {
                    viewModel.createCourse()
                },
                textColor = if (isEnabled) Blue_Netural else Label_Alternative,
                borderColor = if (isEnabled) Blue_Netural else Label_Alternative,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )
        }
    }
}