package com.legacy.legacy_android.feature.screen.achieve

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.achieve.Item
import com.legacy.legacy_android.res.component.achieve.achieveGoalMapper
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Netural
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun AchieveInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: AchieveViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val item = viewModel.uiState.currentAchieve
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val bgImageRes = when (item?.achievementGrade) {
        "LEGENDARY" -> R.drawable.legendary
        "EPIC" -> R.drawable.epic
        "UNIQUE" -> R.drawable.unique
        "CHALLENGE" -> R.drawable.challenge
        "COMMON" -> R.drawable.common
        else -> R.drawable.legacylogo
    }

    val itemImageRes = when (item?.achievementType) {
        "CARD" -> R.drawable.card
        "SHINING_CARD" -> R.drawable.shining_card
        "CARD_PACK" -> R.drawable.card_pack
        "STATED_CARD" -> R.drawable.shining_card
        "RUINS" -> R.drawable.ruins
        "BLOCKS" -> R.drawable.blocks
        "CLEAR_COURSE" -> R.drawable.clear_course
        "MAKE_COURSE" -> R.drawable.make_course
        "STATE_COURSE" -> R.drawable.make_course
        "SOLVE_QUIZ" -> R.drawable.solve_quiz
        "WRONG_QUIZ" -> R.drawable.wrong_quiz
        "BUY_ITEM" -> R.drawable.buy_item
        "TITLE" -> R.drawable.sequence_present
        "LEVEL" -> R.drawable.level
        "FRIEND" -> R.drawable.friend
        else -> R.drawable.legacylogo
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
                    modifier = modifier.size(64.dp)
                ) {
                    Image(
                        painter = painterResource(id = bgImageRes),
                        contentDescription = item?.achievementGrade,
                        modifier = Modifier.fillMaxSize()
                    )

                    Image(
                        painter = painterResource(id = itemImageRes),
                        contentDescription = item?.achievementType,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier.height(24.dp))

                // 도전과제 이름/설명
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item?.achievementName ?: "이름이 없습니다.",
                        style = AppTextStyles.Title2.bold,
                    )
                    Text(
                        text = item?.achievementContent ?: "설명이 없습니다.",
                        style = AppTextStyles.Headline.regular,
                        color = Label_Alternative
                    )
                }

                // 목표 / 상태 / 달성자 비율
                Column(
                    modifier = modifier.fillMaxWidth(0.9f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val goalText = item?.let { achieveGoalMapper(it.achievementType, it.goalRate) } ?: "-"
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
                        Text(text = "목표", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(text = goalText, style = AppTextStyles.Body1.bold)
                    }

                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "상태", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(
                            text = progressText,
                            style = AppTextStyles.Body1.bold,
                            color = if(item?.receive == true) Red_Netural else Label_Netural
                        )
                    }

                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "달성자 비율", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(text = "${item?.achieveUserPercent ?: 0}%", style = AppTextStyles.Body1.bold)
                    }
                }

                // 보상
                Column(
                    modifier = modifier.fillMaxWidth(0.9f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "보상", style = AppTextStyles.Headline.medium, color = Label_Netural)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        item?.achievementAward?.chunked(8)?.forEach { rowItems ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                rowItems.forEach { award ->
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
