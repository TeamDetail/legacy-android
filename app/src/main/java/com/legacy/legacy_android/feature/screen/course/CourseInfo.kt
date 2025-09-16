package com.legacy.legacy_android.feature.screen.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.course.CourseRuins
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Green_Netural
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Netural
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CourseInfo(modifier: Modifier,  viewModel: CourseViewModel, navHostController: NavHostController) {
    val course = viewModel.uiState.currentCourse
    val coroutineScope = rememberCoroutineScope ()
    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        println("LaunchEffect")
        println(viewModel.uiState.currentCourse)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background_Alternative)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
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
                            navHostController.popBackStack()
                            isLoading.value = false
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
                        Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (course == null || course.thumbnail.isBlank()) {
                    SkeletonBox(modifier = modifier.fillMaxWidth().height(200.dp))
                } else {
                    AsyncImage(
                        model = course.thumbnail,
                        contentDescription = "유적지 이미지",
                        modifier = modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.school_img),
                        placeholder = painterResource(R.drawable.school_img)
                    )
                }

                course?.let {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (it.eventId > 0) {
                            Text(
                                text = "이벤트 중!",
                                style = AppTextStyles.Label.Bold,
                                modifier = modifier
                                    .padding(horizontal = 0.dp, vertical = 4.dp)
                                    .background(Red_Netural, RoundedCornerShape(999.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }else{
                            Text(
                                text=it.creator,
                                style = AppTextStyles.Body2.medium,
                                color = Label_Alternative
                            )
                        }
                        Column {
                            Text(
                                text = it.courseName,
                                style = AppTextStyles.Title1.bold
                            )
                            Text(
                                text = it.description,
                                style = AppTextStyles.Headline.medium,
                                color = Label_Netural
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            it.tag.forEach { tag ->
                                Text(
                                    text = "#$tag",
                                    style = AppTextStyles.Label.Medium,
                                    modifier = modifier
                                        .padding(horizontal = 0.dp, vertical = 4.dp)
                                        .background(Fill_Normal, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(
                                            if (!it.heart) R.drawable.heart else R.drawable.p_heart
                                        ),
                                        contentDescription = "하트 아이콘",
                                        modifier = Modifier.size(16.dp).clickable { viewModel.patchHeart(course.courseId) }
                                    )
                                    Text(
                                        text = if (it.heartCount > 999) "999+" else it.heartCount.toString(),
                                        style = AppTextStyles.Body2.medium,
                                        color = if (it.heart) Red_Netural else Label_Assitive
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(
                                            if (it.clear) R.drawable.p_green_flag else R.drawable.green_flag
                                        ),
                                        contentDescription = "깃발 아이콘",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = it.clearCount.toString(),
                                        style = AppTextStyles.Body2.medium,
                                        color = if (it.clear) Green_Netural else Label_Assitive
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.35f)
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .height(28.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(Fill_Normal),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(modifier = Modifier.matchParentSize()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(((course.clearRuinsCount).toFloat() / (course.maxRuinsCount)))
                                            .background(Green_Netural)
                                    )
                                }
                                Text(
                                    text = if (!course.clear) {
                                        "${course.clearRuinsCount}/${course.maxRuinsCount}"
                                    } else {
                                        "탐험 완료!"
                                    },
                                    style = AppTextStyles.Label.Bold,
                                )
                            }
                        }
                    }
                }
            }
            (course?.ruins ?: emptyList()).forEachIndexed { index, item ->
                CourseRuins(data = item, index = index)
            }
        }
    }
}
