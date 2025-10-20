package com.legacy.legacy_android.res.component.achieve

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.network.achieve.AchievementResponse
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Normal
import com.legacy.legacy_android.ui.theme.Yellow_Netural

@Composable
fun AchieveBox(
    modifier: Modifier,
    item: AchievementResponse,
    viewModel: AchieveViewModel,
    navHostController: NavHostController
){
    val bgImageRes = when (item.achievementGrade) {
        "LEGENDARY" -> R.drawable.legendary
        "EPIC" -> R.drawable.epic
        "UNIQUE" -> R.drawable.unique
        "CHALLENGE" -> R.drawable.challenge
        "COMMON" -> R.drawable.common
        else -> R.drawable.legacylogo
    }

    val itemImageRes = when (item.achievementType) {
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                viewModel.updateCurrentAchieve(item)
                navHostController.navigate("achieve_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.size(64.dp)
        ) {
            Image(
                painter = painterResource(id = bgImageRes),
                contentDescription = item.achievementGrade,
                modifier = Modifier.fillMaxSize()
            )

            Image(
                painter = painterResource(id = itemImageRes),
                contentDescription = item.achievementType,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier.width(12.dp))
        Column {
            // 제목 + 타입 표시
            Text(
                text = buildAnnotatedString {
                    append("${item.achievementName} ")
                    withStyle(
                        style = SpanStyle(
                            color = Yellow_Netural,
                            fontSize = AppTextStyles.Caption1.Medium.fontSize,
                            fontWeight = AppTextStyles.Caption1.Medium.fontWeight,
                            fontFamily = AppTextStyles.Caption1.Medium.fontFamily
                        )
                    ) {
                        append("#${item.achievementType}")
                    }
                },
                style = AppTextStyles.Label.Bold
            )

            // 내용
            Text(
                text = item.achievementContent,
                style = AppTextStyles.Caption2.Medium,
                color = Label_Alternative
            )

            Spacer(modifier.height(4.dp))

            // 목표 및 진행률
            Row(verticalAlignment = Alignment.CenterVertically) {
                val goalText = achieveGoalMapper(
                    type = item.achievementType,
                    value = item.goalRate,
                )

                val progressText = when {
                    item.receive -> "수령 완료"
                    item.currentRate >= item.goalRate -> "미완료"
                    else -> "${item.currentRate} / ${item.goalRate}"
                }

                // 목표 텍스트
                Text(
                    text = buildAnnotatedString {
                        append("목표 ")
                        withStyle(
                            style = SpanStyle(
                                color = Label,
                                fontSize = AppTextStyles.Caption1.ExtraBold.fontSize,
                                fontWeight = AppTextStyles.Caption1.ExtraBold.fontWeight,
                                fontFamily = AppTextStyles.Caption1.ExtraBold.fontFamily
                            )
                        ) {
                            append(goalText)
                        }
                    },
                    style = AppTextStyles.Caption1.regular,
                    color = Label_Netural
                )

                Spacer(modifier.width(12.dp))

                // 진행 상태
                Text(
                    text = progressText,
                    style = AppTextStyles.Caption1.ExtraBold,
                    color = if (item.receive) Red_Normal else Label_Netural
                )
            }
        }
    }
}


@Composable
fun achieveGoalMapper(
    type: String,
    value: Int,
    stateItem: String? = null
): String {
    return when (type) {
        "CARD" -> "카드 ${value}개 획득"
        "SHINING_CARD" -> "찬란한 카드 ${value}개 획득"
        "CARD_PACK" -> "카드팩 ${value}회 개봉"
        "STATED_CARD" -> "[${stateItem}] 카드 획득"
        "RUINS" -> "유적지 ${value}개 탐험"
        "BLOCKS" -> "블록 ${value}개 탐험"
        "CLEAR_COURSE" -> "코스 ${value}개 완료"
        "MAKE_COURSE" -> "코스 ${value}개 제작"
        "STATE_COURSE" -> "[${stateItem}] 코스 완료"
        "SOLVE_QUIZ" -> "퀴즈 ${value}개 정답"
        "WRONG_QUIZ" -> "퀴즈 ${value}개 오답"
        "BUY_ITEM" -> "아이템 ${value}개 구매"
        "TITLE" -> "칭호 ${value}개 소지"
        "LEVEL" -> "${value}레벨 달성"
        "FRIEND" -> "친구 ${value}명 달성"
        else -> ""
    }
}
