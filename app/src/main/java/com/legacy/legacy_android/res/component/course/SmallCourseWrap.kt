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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.legacy.legacy_android.R
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
fun SmallCourseWrap(modifier: Modifier, type: String, img: String) {
    Column {
        Text(
            text = buildAnnotatedString {
                append(
                    when (type) {
                        "hot" -> "현재 최고 "
                        else -> ""
                    }
                )
                withStyle(
                    style = SpanStyle(
                        color = when (type) {
                            "hot" -> Red_Netural
                            "event" -> Yellow_Netural
                            else -> Green_Netural
                        }
                    )
                ) {
                    append(
                        when (type) {
                            "hot" -> "인기 "
                            "event" -> "이벤트 "
                            else -> "최근 제작 "
                        }
                    )
                }
                withStyle(style = SpanStyle(color = Label_Netural)) {
                    append(
                        when (type) {
                            "hot" -> "코스"
                            "event" -> "진행 중인 코스"
                            else -> "된 코스"
                        }
                    )
                }
            },
            style = AppTextStyles.Headline.bold,
            color = Label_Netural
        )

        Spacer(modifier.height(4.dp))
        LazyRow(modifier = Modifier.fillMaxWidth().height(220.dp)) {
            item {
                // 박스
                Box(
                    modifier = Modifier
                        .width(144.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .padding(5.dp)
                ) {
                    if (img.isBlank()) {
                        SkeletonBox(
                            modifier = Modifier.matchParentSize()
                        )
                    } else {
                        //                AsyncImage(
//                    model = img,
//                    contentDescription = "유적지 이미지",
//                    modifier = Modifier
//                        .matchParentSize()
//                        .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
//                        .clip(RoundedCornerShape(12.dp)),
//                    contentScale = ContentScale.Crop,
//                    error = painterResource(R.drawable.school_img),
//                    placeholder = painterResource(R.drawable.school_img)
//                )
                        Image(
                            painter = painterResource(R.drawable.shop),
                            contentDescription = "유적지 이미지",
                            modifier = Modifier.matchParentSize()
                                .clip(RoundedCornerShape(12.dp))
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
                            modifier = Modifier.padding(top = 150.dp)
                        ) {
                            Text(
                                text = "2025 안동 최신 코스",
                                style = AppTextStyles.Body2.bold,
                                color = Label
                            )
                            Text(
                                text = "유저이름",
                                style = AppTextStyles.Caption2.Medium,
                                color = Label_Alternative
                            )
                            Spacer(modifier.height(4.dp))
                            // 좋아요 개수
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 하트 아이콘과 숫자
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
                                        text = "999+",
                                        style = AppTextStyles.Caption2.Medium,
                                        color = Label_Assitive
                                    )
                                }

                                // 깃발 아이콘과 숫자
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
                                        text = "105",
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