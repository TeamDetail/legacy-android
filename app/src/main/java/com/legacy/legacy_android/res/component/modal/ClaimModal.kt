package com.legacy.legacy_android.res.component.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.R
import com.legacy.legacy_android.feature.screen.achieve.AchieveViewModel
import com.legacy.legacy_android.res.component.achieve.Item
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Black
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Yellow_Netural
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun ClaimModal(viewModel: AchieveViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black.copy(alpha = 0.75f))
            .zIndex(1500f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Background_Normal, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredSize(280.dp)
                    .padding(vertical = 20.dp, horizontal = 28.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.uiState.awardLoading){
                    Text(text = "로딩 중입니다...", style = AppTextStyles.Caption1.Bold)
                }else if (viewModel.uiState.awardAchieve == null){
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(R.drawable.sad),
                            contentDescription = "sad",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(text = "수령할 보상이 없어요...", style = AppTextStyles.Headline.bold)
                    }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(1500)
                        viewModel.updateClaimModalOpen(false)
                    }
                }else{
                    Column (
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "보상 수령 완료!",
                            style = AppTextStyles.Heading2.bold,
                            color = Yellow_Netural
                        )
                        Text(
                            text = "도전과제 완료를 축하드려요~!",
                            style = AppTextStyles.Caption1.Medium,
                            color = Label_Netural
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        viewModel.uiState.awardAchieve?.achievementAward
                            ?.chunked(5)
                            ?.forEach{  rowItems ->
                        Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                rowItems.forEach { item ->
                                    Box(
                                        Modifier
                                            .size(40.dp)
                                            .background(Fill_Normal, RoundedCornerShape(8.dp))
                                            .border(
                                                1.dp,
                                                Line_Alternative,
                                                RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Item(count = item.itemCount)
                                    }
                                }
                            }
                        }
                    }
                    CustomButton(
                        text = "닫기",
                        onClick = {
                            viewModel.initAward()
                        },
                        modifier = Modifier.weight(0.8f),
                        borderColor = Label_Assitive,
                        textStyle = AppTextStyles.Caption1.Bold,
                        textColor = Label_Assitive
                    )
                }
            }
        }
    }
}