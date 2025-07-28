package com.legacy.legacy_android.res.component.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Green_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun SmallCourseWrap(modifier: Modifier = Modifier, type: String, data: List<AllCourseResponse>?) {
    Column(modifier = modifier) {
        Text(
            text = buildAnnotatedString {
                append(when (type) {
                    "popular" -> "현재 최고 "
                    else -> ""
                })
                withStyle(
                    style = SpanStyle(
                        color = when (type) {
                            "popular" -> Red_Netural
                            "event" -> Yellow_Netural
                            else -> Green_Netural
                        }
                    )
                ) {
                    append(
                        when (type) {
                            "popular" -> "인기 "
                            "event" -> "이벤트 "
                            else -> "최근 제작 "
                        }
                    )
                }
                withStyle(style = SpanStyle(color = Label_Netural)) {
                    append(
                        when (type) {
                            "popular" -> "코스"
                            "event" -> "진행 중인 코스"
                            else -> "된 코스"
                        }
                    )
                }
            },
            style = AppTextStyles.Headline.bold,
            color = Label_Netural
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(modifier = Modifier.fillMaxWidth().height(220.dp)) {
            data?.forEach { course ->
                item {
                    Box(
                        modifier = Modifier
                            .width(144.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .padding(5.dp)
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

                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Background_Normal.copy(alpha = 1f),
                                                Background_Normal.copy(alpha = 0.2f)
                                            ),
                                            startY = Float.POSITIVE_INFINITY,
                                            endY = 0f
                                        )
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Column(
                                modifier = Modifier.padding(top = 150.dp, start = 4.dp)
                            ) {
                                Text(
                                    text = course.courseName,
                                    style = AppTextStyles.Body2.bold,
                                    color = Label
                                )
                                Text(
                                    text = course.creator,
                                    style = AppTextStyles.Caption2.Medium,
                                    color = Label_Alternative
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.heart),
                                            contentDescription = "하트 아이콘",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = if (course.heartCount > 999) "999+" else course.heartCount.toString(),
                                            style = AppTextStyles.Caption2.Medium,
                                            color = Label_Assitive
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.green_flag),
                                            contentDescription = "깃발 아이콘",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = course.clearCount.toString(),
                                            style = AppTextStyles.Caption2.Medium,
                                            color = Label_Assitive
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
