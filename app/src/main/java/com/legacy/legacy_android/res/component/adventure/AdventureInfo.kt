package com.legacy.legacy_android.res.component.adventure

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdResponse
import com.legacy.legacy_android.feature.screen.home.HomeViewModel
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.skeleton.SkeletonBox
import com.legacy.legacy_android.ui.theme.*
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdventureInfo(
    data: List<RuinsIdResponse>?,
    viewModel: HomeViewModel,
) {
    val isLoading = data == null

    val scrollStates = remember(data?.size) {
        List(data?.size ?: 1) { ScrollState(0) }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val pagerState = rememberPagerState(pageCount = { data?.size ?: 1 })
    val coroutineScope = rememberCoroutineScope()

    var showSheet by remember { mutableStateOf(true) }

    LaunchedEffect(data?.firstOrNull()?.ruinsId) {
        data?.firstOrNull()?.let {
            viewModel.loadCommentById(it.ruinsId)
            viewModel.setSelectedRuinsDetail(it)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (data != null) {
            delay(100)
            data.getOrNull(pagerState.currentPage)?.let { currentRuin ->
                viewModel.loadCommentById(currentRuin.ruinsId)
                viewModel.setSelectedRuinsDetail(currentRuin)
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.updateSelectedId(emptyList())
                viewModel.fetchRuinsDetail(emptyList())
                viewModel.initRuinsDetail()
                viewModel.updateIsCommenting(false)
                showSheet = false
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = Background_Netural,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(width = 40.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Fill_Alternative)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                PagerIndicator(data, pagerState)
                Spacer(modifier = Modifier.height(8.dp))

                if (isLoading) {
                    RuinsDetailPage(
                        currentData = null,
                        viewModel = viewModel,
                        scrollState = rememberScrollState(),
                        scale = 1f,
                        alpha = 1f
                    )
                } else {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp, max = 600.dp)
                    ) { page ->
                        val currentData = data.getOrNull(page)
                        val scrollState = scrollStates.getOrNull(page) ?: rememberScrollState()

                        val pageOffset =
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        val scale = lerp(0.85f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
                        val alpha = lerp(0.5f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))

                        RuinsDetailPage(
                            currentData = currentData,
                            viewModel = viewModel,
                            scrollState = scrollState,
                            scale = scale,
                            alpha = alpha
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    onClick = {
                        if (!isLoading) {
                            data?.getOrNull(pagerState.currentPage)?.ruinsId?.let {
                                viewModel.loadQuiz(it)
                            }
                        }
                    },
                    text = if (isLoading) "로딩 중..." else "퀴즈 풀고 탐험하기!",
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = Blue_Netural,
                    textColor = Blue_Netural,
                    backgroundColor = Fill_Normal,
                    contentPadding = PaddingValues(vertical = 12.dp),
                    fontSize = AppTextStyles.Body1.bold.fontSize
                )
            }
        }
    }
}

@Composable
private fun PagerIndicator(
    data: List<RuinsIdResponse>?,
    pagerState: PagerState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data != null && data.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Fill_Netural, shape = RoundedCornerShape(12.dp))
                    .height(24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (pagerState.currentPage == 0) {
                        "옆으로 넘겨서 더 많은 유적지 확인 →"
                    } else {
                        "${pagerState.currentPage + 1} / ${data.size}"
                    },
                    style = AppTextStyles.Caption1.Medium,
                    color = Label_Netural
                )
            }
        }
    }
}

@Composable
private fun RuinsDetailPage(
    currentData: RuinsIdResponse?,
    viewModel: HomeViewModel,
    scrollState: ScrollState,
    scale: Float,
    alpha: Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        RuinsHeader(currentData, viewModel)

        Spacer(modifier = Modifier.height(20.dp))

        RuinsDescription(currentData)

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        CommentsSection(currentData, viewModel)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun RuinsHeader(
    currentData: RuinsIdResponse?,
    viewModel: HomeViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            // 유적지 탐험 타이틀
            if (currentData == null) {
                SkeletonBox(
                    modifier = Modifier
                        .height(28.dp)
                        .fillMaxWidth(0.6f)
                        .clip(RoundedCornerShape(4.dp))
                )
            } else {
                Text(
                    text = "유적지 탐험",
                    color = White,
                    style = AppTextStyles.Headline.bold
                )
            }

            // 유적지 ID
            when {
                currentData == null -> {
                    SkeletonBox(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(0.3f)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
                currentData.ruinsId == 0 -> {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                else -> {
                    Text(
                        text = "#${NumberFormat.getNumberInstance(Locale.US).format(currentData.ruinsId)}",
                        style = AppTextStyles.Caption1.Medium,
                        color = Label_Alternative
                    )
                }
            }

            // 유적지 이름
            when {
                currentData == null -> {
                    SkeletonBox(
                        modifier = Modifier
                            .height(28.dp)
                            .fillMaxWidth(0.7f)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
                currentData.name.isBlank() -> {
                    Spacer(modifier = Modifier.height(28.dp))
                }
                else -> {
                    Text(
                        text = currentData.name,
                        style = AppTextStyles.Headline.bold
                    )
                }
            }

            // 별점 및 댓글 수
            if (currentData == null) {
                SkeletonBox(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(4.dp))
                )
            } else {
                StarRating(
                    rating = currentData.averageRating,
                    commentCount = currentData.countComments.toString()
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

            // 빈 공간
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (currentData == null) {
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
                } else {
                    Spacer(modifier = Modifier.height(40.dp).weight(1f))
                    Spacer(modifier = Modifier.height(40.dp).weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 한줄평 남기기 버튼
            if (currentData == null) {
                SkeletonBox(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
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
        }

        RuinsImage(currentData)
    }
}

@Composable
private fun StarRating(rating: Int, commentCount: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
            text = "($commentCount)",
            style = AppTextStyles.Body2.medium,
            color = Fill_Alternative
        )
    }
}

@Composable
private fun RuinsImage(currentData: RuinsIdResponse?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .clip(RoundedCornerShape(12.dp))
            .padding(5.dp)
    ) {
        when {
            currentData == null -> {
                RuinImage(
                    image = null,
                    name = null,
                    nationAttributeName = null,
                    regionAttributeName = null,
                    lineAttributeName = null,
                    height = 220,
                    isLoading = true
                )
            }

            currentData.ruinsImage.isBlank() -> {
                Spacer(modifier = Modifier.matchParentSize())
            }

            currentData.card == null -> {
                RuinImage(
                    image = currentData.ruinsImage,
                    name = currentData.name,
                    nationAttributeName = null,
                    regionAttributeName = null,
                    lineAttributeName = null,
                    height = 220,
                    isLoading = true
                )
            }

            else -> {
                RuinImage(
                    image = currentData.ruinsImage,
                    name = currentData.name,
                    nationAttributeName = currentData.card.nationAttributeName,
                    regionAttributeName = currentData.card.regionAttributeName,
                    lineAttributeName = currentData.card.lineAttributeName,
                    height = 220,
                    isLoading = false
                )
            }
        }
    }
}

@Composable
private fun RuinsDescription(currentData: RuinsIdResponse?) {
    when {
        currentData == null -> {
            SkeletonBox(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
            )
        }
        currentData.description.isBlank() -> {
            Text(
                text = "설명글이 없습니다.",
                style = AppTextStyles.Body2.medium,
                color = Label_Alternative
            )
        }
        else -> {
            Text(
                text = currentData.description,
                style = AppTextStyles.Body2.medium
            )
        }
    }
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Line_Alternative)
    )
}

@Composable
private fun CommentsSection(
    currentData: RuinsIdResponse?,
    viewModel: HomeViewModel
) {
    when {
        currentData == null -> {
            repeat(2) {
                SkeletonBox(
                    modifier = Modifier
                        .height(184.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        viewModel.uiState.comments.isNullOrEmpty() -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "남겨진 한줄평이 없습니다..",
                    style = AppTextStyles.Headline.bold
                )
                Spacer(Modifier.height(12.dp))
                CustomButton(
                    onClick = {
                        viewModel.updateIsCommenting(true)
                        viewModel.setCommentValue("")
                    },
                    text = "유적지에 첫 한줄평 남기기",
                    textStyle = AppTextStyles.Caption1.Bold,
                    textColor = Label_Netural,
                    borderColor = Line_Alternative,
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(Modifier.height(30.dp))
            }
        }
        else -> {
            viewModel.uiState.comments?.forEach { comment ->
                CommentBox(comment = comment)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}