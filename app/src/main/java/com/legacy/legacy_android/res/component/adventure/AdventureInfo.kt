package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AdventureInfo(
    id: Int?,
    viewModel: HomeViewModel,
    name: String?,
    info: String?,
    tags: List<String>?,
    img: String?,
    ruinsId: Int?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black, shape = RoundedCornerShape(12.dp))
            .height(300.dp)
            .padding(12.dp)
            .zIndex(50f)
            .clickable(enabled = false) {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.8f)
                ) {
                    Text(
                        text = "유적지 탐험",
                        color = White,
                        style = AppTextStyles.Headline.bold
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "선택한 블록",
                            color = Label_Alternative,
                            style = AppTextStyles.Body2.medium
                        )
                        if (id == null) {
                            SkeletonBox(
                                modifier = Modifier
                                    .height(24.dp)
                                    .fillMaxWidth(0.3f)
                                    .clip(RoundedCornerShape(4.dp)),
                            )
                        } else {
                            Text(
                                text = "#" + NumberFormat.getNumberInstance(Locale.US).format(id),
                                style = AppTextStyles.Headline.medium
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "유적지 정보",
                            color = Label_Alternative,
                            style = AppTextStyles.Body2.medium
                        )
                        if (info.isNullOrBlank()) {
                            SkeletonBox(
                                modifier = Modifier
                                    .height(24.dp)
                                    .fillMaxWidth(0.6f)
                                    .clip(RoundedCornerShape(4.dp)),
                            )
                        } else {
                            Text(
                                text = info,
                                style = AppTextStyles.Body1.bold
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(5.dp)
                ) {
                    if (img.isNullOrBlank()) {
                        SkeletonBox(
                            modifier = Modifier
                                .matchParentSize()
                        )
                    } else {
                        AsyncImage(
                            model = img,
                            contentDescription = "유적지 이미지",
                            modifier = Modifier
                                .matchParentSize()
                                .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.school_img),
                            placeholder = painterResource(R.drawable.school_img)
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Background_Normal.copy(alpha = 0.5f))
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp),
                            text = name ?: "",
                            fontFamily = bitbit,
                            fontSize = 16.sp,
                            color = Label
                        )
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .border(1.dp, color = Blue_Netural, shape = RoundedCornerShape(12.dp))
                    .clickable(
                        onClick = {
                            viewModel.loadQuiz(ruinsId)
                        }
                    )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "퀴즈 풀고 탐험하기!",
                    color = Blue_Netural,
                    style = AppTextStyles.Body1.bold
                )
            }
        }
    }
}
