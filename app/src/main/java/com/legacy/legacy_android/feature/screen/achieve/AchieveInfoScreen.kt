package com.legacy.legacy_android.feature.screen.achieve

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.achieve.Item
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Disable
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Netural
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.utils.AchievementMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.chunked
import kotlin.collections.forEach

@SuppressLint("DefaultLocale")
@Composable
fun AchieveInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: AchieveViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val item = viewModel.uiState.currentAchieve
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.uiState.achieveStatus) {
        when (viewModel.uiState.achieveStatus) {
            0 -> viewModel.fetchAchieveListByType("EXPLORE")
            1 -> viewModel.fetchAchieveListByType("LEVEL")
            else -> viewModel.fetchAchieveListByType("HIDDEN")
        }
    }

    val bgImageRes = AchievementMapper.getBackgroundRes(item?.achievementGrade)
    val itemImageRes = AchievementMapper.getItemRes(item?.achievementType)
    val achievementValueGrade = when (viewModel.uiState.achieveStatus) {
        0 -> "탐험"
        1 -> "숙련"
        2 -> "히든"
        else -> "기타"
    }
    val (exp, credit) = AchievementMapper.getReward(item?.achievementGrade)


    LaunchedEffect(Unit) {
        println(item)
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
            // 뒤로가기
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
                    contentDescription = null
                )
                Text(
                    text = "도전과제 정보",
                    style = AppTextStyles.Heading1.bold
                )
            }

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier.height(24.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = bgImageRes),
                        contentDescription = item?.achievementGrade,
                        modifier = Modifier.fillMaxSize()
                    )

                    itemImageRes?.let {
                        Image(
                            painter = painterResource(id = itemImageRes),
                            contentDescription = item?.achievementType,
                            modifier = Modifier.size(84.dp)
                        )
                    }
                }

                // 도전과제 이름/설명
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = AppTextStyles.Title2.bold.toSpanStyle()) {
                                append(item?.achievementName ?: "이름이 없어요")
                            }
                            append(" ")
                            withStyle(
                                style = AppTextStyles.Title3.medium.toSpanStyle().copy(
                                    color = when (achievementValueGrade) {
                                        "탐험" -> Blue_Netural
                                        "숙련" -> Yellow_Netural
                                        else -> Label_Disable
                                    }
                                )
                            ) {
                                append("   #$achievementValueGrade")
                            }
                        },
                        style = AppTextStyles.Title3.medium,
                        color = Label_Netural
                    )

                    Text(
                        text = item?.achievementContent ?: "설명글이 없습니다.",
                        style = AppTextStyles.Headline.regular,
                        color = Label_Alternative,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    // 목표 / 상태 / 달성자 비율
                    Column(
                        modifier = modifier.fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val progressText = item?.let {
                            when {
                                it.receive -> "수령 완료"
                                it.currentRate >= it.goalRate -> "완료"
                                else -> "${it.currentRate} / ${it.goalRate}"
                            }
                        } ?: "-"

                        Row(
                            modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "목표",
                                style = AppTextStyles.Body1.regular,
                                color = Label_Netural
                            )
                            Text(text = item?.achievementGradeText ?: "없음", style = AppTextStyles.Body1.bold)
                        }

                        Row(
                            modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "상태",
                                style = AppTextStyles.Body1.regular,
                                color = Label_Netural
                            )
                            Text(
                                text = progressText,
                                style = AppTextStyles.Body1.bold,
                                color = if (item?.receive == true) Red_Netural else Label_Netural
                            )
                        }

                        Row(
                            modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "달성자 비율",
                                style = AppTextStyles.Body1.regular,
                                color = Label_Netural
                            )
                            Text(
                                text = String.format("%.2f%%", item?.achieveUserPercent ?: 0f),
                                style = AppTextStyles.Body1.bold
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier = modifier.fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "보상",
                            style = AppTextStyles.Headline.medium,
                            color = Label_Netural
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "크레딧", style = AppTextStyles.Label.Medium)
                                Text(
                                    text = "$credit 크레딧",
                                    style = AppTextStyles.Body2.medium,
                                    color = Label_Alternative
                                )
                            }
                            Row(
                                modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "경험치", style = AppTextStyles.Label.Medium)
                                Text(
                                    text = "$exp exp",
                                    style = AppTextStyles.Body2.medium,
                                    color = Label_Alternative
                                )
                            }
                            Spacer(Modifier.height(4.dp))

                            item?.achievementAward?.chunked(8)?.forEach { rowItems ->
                                Column(
                                    modifier = modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    rowItems.forEach { award ->
                                        Row(
                                            modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = award.itemName,
                                                style = AppTextStyles.Label.Medium
                                            )
                                            Item(count = award.itemCount)
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
}
