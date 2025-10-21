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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.legacy.legacy_android.feature.network.achieve.AchievementResponse
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Blue_Netural
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Disable
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Red_Normal
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import com.legacy.legacy_android.utils.AchievementMapper

@Composable
fun AchieveBox(
    modifier: Modifier,
    item: AchievementResponse,
    viewModel: AchieveViewModel,
    navHostController: NavHostController
){

    val bgImageRes = AchievementMapper.getBackgroundRes(item.achievementGrade)
    val itemImageRes = AchievementMapper.getItemRes(item.achievementType)
    val achievementValueGrade = when (viewModel.uiState.achieveStatus) {
        0 -> "탐험"
        1 -> "숙련"
        2 -> "히든"
        else -> "기타"
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

            itemImageRes?.let {
                Image(
                    painter = painterResource(id = itemImageRes),
                    contentDescription = item.achievementType,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier.width(12.dp))
        Column {
            Text(
                text = buildAnnotatedString {
                    append(item.achievementName)
                    withStyle(
                        style = SpanStyle(
                            color = when (achievementValueGrade) {
                                "탐험" -> Blue_Netural
                                "숙련" -> Yellow_Netural
                                else -> Label_Disable
                            },
                            fontSize = AppTextStyles.Caption1.Medium.fontSize,
                            fontWeight = AppTextStyles.Caption1.Medium.fontWeight,
                            fontFamily = AppTextStyles.Caption1.Medium.fontFamily
                        )
                    ) {
                        append(" #${achievementValueGrade}")
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

                val progressText = when {
                    item.receive -> "수령 완료"
                    item.currentRate >= item.goalRate -> "미수령"
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
                            append(item.achievementGradeText)
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