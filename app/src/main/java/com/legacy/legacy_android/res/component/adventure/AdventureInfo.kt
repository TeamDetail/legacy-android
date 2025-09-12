package com.legacy.legacy_android.res.component.adventure

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventureInfo(
    id: Int?,
    viewModel: HomeViewModel,
    name: String?,
    info: String?,
    tags: List<String>?,
    img: String?,
    ruinsId: Int?,
    description: String?
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(id) {
        id?.let { viewModel.loadCommentById(it) }
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
                            Black,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
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
                                if (id == null) {
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .fillMaxWidth(0.3f)
                                            .clip(RoundedCornerShape(4.dp)),
                                    )
                                } else {
                                    Text(
                                        text = "#" + NumberFormat.getNumberInstance(Locale.US)
                                            .format(id),
                                        style = AppTextStyles.Caption1.Medium,
                                        color = Label_Alternative
                                    )
                                }

                                // info
                                if (info.isNullOrBlank()) {
                                    SkeletonBox(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .fillMaxWidth(0.6f)
                                            .clip(RoundedCornerShape(4.dp)),
                                    )
                                } else {
                                    Text(
                                        text = info ?: "",
                                        style = AppTextStyles.Headline.bold
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
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                                        .border(1.dp, color = Line_Netural, shape = RoundedCornerShape(12.dp))
                                        .clickable {}
                                ) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        text = "한줄평 남기기",
                                        color = Label_Netural,
                                        style = AppTextStyles.Caption2.Bold
                                    )
                                }
                            }

                            // 이미지
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .padding(5.dp)
                            ) {
                                if (img.isNullOrBlank()) {
                                    SkeletonBox(modifier = Modifier.matchParentSize())
                                } else {
                                    AsyncImage(
                                        model = img,
                                        contentDescription = "유적지 이미지",
                                        modifier = Modifier
                                            .height(184.dp)
                                            .border(
                                                1.dp,
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
                                        text = name ?: "",
                                        fontFamily = bitbit,
                                        fontSize = 16.sp,
                                        color = Label
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        // description
                        if (description.isNullOrBlank()) {
                            SkeletonBox(
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        } else {
                            Text(
                                text = description ?: "설명글이 없습니다.",
                                style = AppTextStyles.Body2.medium
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
                                AsyncImage(
                                    model = it.userImgUrl,
                                    contentDescription = "프로필 이미지",
                                    modifier = Modifier
                                        .height(184.dp)
                                        .border(
                                            1.dp,
                                            color = Line_Netural,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.school_img),
                                    placeholder = painterResource(R.drawable.school_img)
                                )
                                Text(text = it.userName)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        ) {}

        // 퀴즈 버튼
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Fill_Normal, shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .border(1.dp, color = Blue_Netural, shape = RoundedCornerShape(12.dp))
                .clickable { viewModel.loadQuiz(ruinsId) }
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
