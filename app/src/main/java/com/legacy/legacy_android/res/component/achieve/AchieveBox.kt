package com.legacy.legacy_android.res.component.achieve

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Row (
        modifier = modifier.fillMaxWidth()
            .padding(8.dp)
            .clickable{
                viewModel.updateCurrentAchieve(item)
                println(viewModel.uiState.currentAchieve)
                navHostController.navigate("achieve_info")},
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(R.drawable.temp_profile),
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier.width(12.dp))
        Column {
            Text(
                text = buildAnnotatedString {
                    append(
                        text = item.achievementName + " "
                    )
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
                style = AppTextStyles.Label.Bold,
            )
            Text(
                text = item.achievementContent,
                style = AppTextStyles.Caption2.Medium,
                color = Label_Alternative
            )
            Spacer(modifier.height(4.dp))
            Row {
                Text(
                    text = buildAnnotatedString {
                        append(
                            text = "목표" + " "
                        )
                        withStyle(
                            style = SpanStyle(
                                color = Label,
                                fontSize = AppTextStyles.Caption1.ExtraBold.fontSize,
                                fontWeight = AppTextStyles.Caption1.ExtraBold.fontWeight,
                                fontFamily = AppTextStyles.Caption1.ExtraBold.fontFamily
                            )
                        ) {
                            append("5블록 탐험")
                        }
                    },
                    style = AppTextStyles.Caption1.regular,
                    color = Label_Netural
                )
                Spacer(modifier.width(12.dp))
                if (item.receive) {
                    Text(
                        text = "수령 완료",
                        style = AppTextStyles.Caption1.ExtraBold,
                        color = Red_Normal
                    )
                }else if (item.currentRate == item.goalRate){
                    Text(
                        text = "미완료",
                        style = AppTextStyles.Caption1.ExtraBold
                    )
                }
                else {
                    Text(
                        text = buildAnnotatedString {
                            append(
                                text = "현재 상태" + " "
                            )
                            withStyle(
                                style = SpanStyle(
                                    color = Label,
                                    fontSize = AppTextStyles.Caption1.ExtraBold.fontSize,
                                    fontWeight = AppTextStyles.Caption1.ExtraBold.fontWeight,
                                    fontFamily = AppTextStyles.Caption1.ExtraBold.fontFamily
                                )
                            ) {
                                append("${item.currentRate} / ${item.goalRate}")
                            }
                        },
                        style = AppTextStyles.Caption1.regular,
                        color = Label_Netural
                    )
                }
            }
        }
    }
}