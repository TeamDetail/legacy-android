package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventureInfo(
    data: RuinsIdResponse?,
    viewModel: HomeViewModel,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(data?.ruinsId) {
        data?.ruinsId?.let { viewModel.loadCommentById(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                viewModel.updateSelectedId(-1)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 302.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetDragHandle = { },
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 600.dp)
                        .background(
                            Background_Netural,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(12.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {}
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.1f)
                                .background(Fill_Alternative, shape = RoundedCornerShape(8.dp))
                                .height(8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth(0.5f)
                            ) {
                                // 유적지 탐험 제목
                                Text(
                                    text = "유적지 탐험",
                                    color = White,
                                    style = AppTextStyles.Headline.bold
                                )

                                // ID
                                if (data?.ruinsId == null) {
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .fillMaxWidth(0.3f)
                                            .clip(RoundedCornerShape(4.dp)),
                                    )
                                } else {
                                    Text(
                                        text = "#" + NumberFormat.getNumberInstance(Locale.US)
                                            .format(data.ruinsId),
                                        style = AppTextStyles.Caption1.Medium,
                                        color = Label_Alternative
                                    )
                                }

                                // info
                                if (data?.name.isNullOrBlank()) {
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .fillMaxWidth(0.6f)
                                            .clip(RoundedCornerShape(4.dp)),
                                    )
                                } else {
                                    Text(
                                        text = data.name,
                                        style = AppTextStyles.Headline.bold
                                    )
                                }

                                // 별점 표시
                                val rating = data?.averageRating ?: 0
                                val commentCount = data?.countComments?.toString() ?: "0"
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    for (i in 1..10) {
                                        if (i % 2 != 0) {
                                            Image(
                                                painter = painterResource(R.drawable.starhalfleft),
                                                contentDescription = null,
                                                colorFilter = ColorFilter.tint(
                                                    if (i <= rating) Primary else White
                                                )
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(R.drawable.starhalfright),
                                                contentDescription = null,
                                                colorFilter = ColorFilter.tint(
                                                    if (i <= rating) Primary else White
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                    }
                                    Text(
                                        text = "(${commentCount})",
                                        style = AppTextStyles.Body2.medium,
                                        color = Fill_Alternative
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(Line_Alternative)
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                // 탐험자 수 / 획득 비율
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(40.dp)
                                            .weight(1f)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(40.dp)
                                            .weight(1f)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // 한줄평 남기기 버튼
                                CustomButton(
                                    onClick = {
                                        viewModel.updateIsCommenting(true)
                                        viewModel.setCommentValue("")
                                    },
                                    text = "한줄평 남기기",
                                    modifier = Modifier.fillMaxWidth(),
                                    borderColor = Line_Netural,
                                    textColor = Label_Netural,
                                    backgroundColor = Fill_Normal,
                                    contentPadding = PaddingValues(vertical = 8.dp),
                                    fontSize = AppTextStyles.Caption2.Bold.fontSize
                                )
                            }

                                // 이미지
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .padding(5.dp)
                            ) {
                                if (data?.ruinsImage.isNullOrBlank()) {
                                    SkeletonBox(modifier = Modifier.matchParentSize())
                                } else {
                                    AsyncImage(
                                        model = data.ruinsImage,
                                        contentDescription = "유적지 이미지",
                                        modifier = Modifier
                                            .height(220.dp)
                                            .border(
                                                2.dp,
                                                color = Line_Netural,
                                                shape = RoundedCornerShape(12.dp)
                                            )
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
                                        text = data.name,
                                        fontFamily = bitbit,
                                        fontSize = 16.sp,
                                        color = Label
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // description
                        if (data?.description.isNullOrBlank()) {
                            SkeletonBox(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        } else {
                            Text(
                                text = data.description,
                                style = AppTextStyles.Body2.medium
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(Line_Alternative)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // comments
                        if (viewModel.uiState.comments.isNullOrEmpty()) {
                            repeat(2) {
                                SkeletonBox(
                                    modifier = Modifier
                                        .height(184.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        } else {
                            viewModel.uiState.comments?.forEach { it ->
                                CommentBox(comment = it)
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        ) {}

        // 퀴즈 버튼
        CustomButton(
            onClick = { data?.ruinsId?.let { viewModel.loadQuiz(it) } },
            text = "퀴즈 풀고 탐험하기!",
            modifier = Modifier.fillMaxWidth(),
            borderColor = Blue_Netural,
            textColor = Blue_Netural,
            backgroundColor = Fill_Normal,
            contentPadding = PaddingValues(vertical = 12.dp),
            fontSize = AppTextStyles.Body1.bold.fontSize
        )
    }
}
