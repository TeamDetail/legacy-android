package com.legacy.legacy_android.feature.screen.achieve

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.legacy.legacy_android.R
import com.legacy.legacy_android.res.component.achieve.Item
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Alternative
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Netural
import com.legacy.legacy_android.ui.theme.Line_Alternative
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
                    contentDescription = null,
                    modifier = Modifier
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
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row (modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "목표", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(text = "${item?.currentRate}블록 탐험", style = AppTextStyles.Body1.bold)
                    }
                    Row (modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "상태", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(text = "${item?.currentRate} / ${item?.goalRate}", style = AppTextStyles.Body1.bold, color = Red_Netural)
                    }
                    Row (modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "달성자 비율", style = AppTextStyles.Body1.regular, color = Label_Netural)
                        Text(text = "${item?.achieveUserPercent}%", style = AppTextStyles.Body1.bold)
                    }
                }
                Column (
                    modifier = modifier.fillMaxWidth(0.9f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ){
                    Text(text = "보상", style = AppTextStyles.Headline.medium, color = Label_Netural)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        item?.achievementAward?.chunked(8)?.forEach { rowItems ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                rowItems.forEach { item ->
                                    Item(count = item.itemCount)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}