package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.screen.course.CourseViewModel
import com.legacy.legacy_android.feature.screen.course.model.CourseStatus
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Green_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Netural

@Composable
fun CourseBox (course: AllCourseResponse, viewModel: CourseViewModel) {
    val clearSize = course.clearRuins?.size ?: 0
    val ruinSize = course.ruins?.size ?: 0
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .padding(5.dp)
            .clickable{viewModel.setCurrentCourse(course)
            viewModel.updateCourseStatus(CourseStatus.INFO)}
    ) {
        if (course.thumbnail.isBlank()) {
            SkeletonBox(modifier = Modifier.matchParentSize())
        } else {
            AsyncImage(
                model = course.thumbnail,
                contentDescription = "유적지 이미지",
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.school_img),
                placeholder = painterResource(R.drawable.school_img)
            )
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Background_Normal.copy(alpha = 1f),
                            Background_Normal.copy(alpha = 0.2f)
                        )
                    )
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(start = 12.dp).fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                    if (course.eventCourse) {
                        Text(
                            text = "이벤트 중!",
                            style = AppTextStyles.Caption2.Medium,
                            fontSize = 8.sp,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 4.dp)
                                .background(Red_Netural, RoundedCornerShape(999.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = course.courseName,
                        style = AppTextStyles.Title3.bold,
                        color = Label
                    )

                    Text(
                        text = course.description,
                        style = AppTextStyles.Label.regular,
                        color = Label_Netural
                    )
                }
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Image(
                                painter = painterResource(if(course.heart){R.drawable.heart}else{R.drawable.p_heart}),
                                contentDescription = "하트 아이콘",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (course.heartCount > 999) "999+" else course.heartCount.toString(),
                                style = AppTextStyles.Label.Medium,
                                color = if(!course.heart){Red_Netural}else{Label_Assitive}
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Image(
                                painter = painterResource(if(course.isClear){R.drawable.p_green_flag}else{R.drawable.green_flag}),
                                contentDescription = "깃발 아이콘",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = course.clearCount.toString(),
                                style = AppTextStyles.Label.Medium,
                                color = if(course.isClear){Green_Netural}else{Label_Assitive}
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Green_Netural),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.4f)
                                    .background(Fill_Normal)
                            )
                        }
                        Text(
                            text = if (clearSize != 0 && clearSize != ruinSize) {
                                "${clearSize + 1}/${ruinSize + 1}"
                            } else {
                                "탐험 완료"
                            },
                            style = AppTextStyles.Label.Bold,
                        )
                    }
                }
            }
        }
    }
}