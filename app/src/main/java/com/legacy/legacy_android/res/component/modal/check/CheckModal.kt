package com.legacy.legacy_android.res.component.modal.check

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.ui.theme.AppTextStyles
import com.legacy.legacy_android.ui.theme.Background_Normal
import com.legacy.legacy_android.ui.theme.Fill_Netural
import com.legacy.legacy_android.ui.theme.Fill_Normal
import com.legacy.legacy_android.ui.theme.Label
import com.legacy.legacy_android.ui.theme.Label_Alternative
import com.legacy.legacy_android.ui.theme.Label_Assitive
import com.legacy.legacy_android.ui.theme.Line_Alternative
import com.legacy.legacy_android.ui.theme.Line_Netural
import com.legacy.legacy_android.ui.theme.Primary
import com.legacy.legacy_android.ui.theme.White
import java.time.LocalDate

@Composable
fun CheckModal(
    onCheckClick: (Boolean) -> Unit,
) {
    val today = LocalDate.now()
    val currentMonth by remember { mutableIntStateOf(today.monthValue) }
    val days = remember { (1..today.lengthOfMonth()).toList() }
    val checkList = remember { listOf("${currentMonth}월", "론칭") }
    val viewModel: CheckViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.checkDaily()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A2B2C).copy(alpha = 0.75f))
            .zIndex(5000f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(Background_Normal, RoundedCornerShape(20.dp))
                .fillMaxWidth(0.9f)
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            /** 헤더 */
            Header(onCheckClick)

            /** 선택 버튼 */
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                checkList.forEachIndexed { index, item ->
                    StatusButton(
                        selectedValue = viewModel.uiState.checkStatus,
                        onClick = { viewModel.changeCheckStatus(index) },
                        text = item,
                        id = index,
                        selectedColor = Primary,
                        nonSelectedColor = Line_Netural
                    )
                }
            }

            /** 기간 */
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "기간",  style = AppTextStyles.Caption1.Medium, color = Label_Alternative)
                Text(text = "${viewModel.uiState.check?.get(0)?.startAt} ~ ${viewModel.uiState.check?.get(0)?.endAt}", style = AppTextStyles.Caption1.Medium, color = Label_Alternative)
            }

            val chunkedDays = days.chunked(7)

            chunkedDays.forEachIndexed { index, rowItems ->
                val isLastRow = index == chunkedDays.lastIndex

                val displayItems: List<Int?> = if (isLastRow) {
                    val placeholders = List(7 - rowItems.size) { null }
                    rowItems.map { it as Int? } + placeholders
                } else {
                    rowItems.map { it as Int? }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    displayItems.forEach { day ->
                        if (day != null) {
                            val backgroundColor = when {
                                day < today.dayOfMonth -> Fill_Netural
                                day == today.dayOfMonth -> Primary
                                else -> Fill_Normal
                            }
                            val textColor = if (day < today.dayOfMonth) Label_Assitive else Label

                            Box(
                                modifier = Modifier
                                    .clickable { viewModel.setSelectedItem(day)
                                    println(day)
                                    println(viewModel.uiState.selectedCheck)}
                                    .size(40.dp)
                                    .background(backgroundColor, RoundedCornerShape(8.dp))
                                    .border(1.dp, Line_Alternative, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    color = textColor,
                                    style = AppTextStyles.Body2.bold
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .alpha(0f)
                            )
                        }
                    }
                }
            }
            /** 선택 창*/
            if (viewModel.uiState.selectedCheck != null) {
                Detail(viewModel.uiState.selectedCheck)
            }
        }
    }
}

@Composable
private fun Header(onCheckClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("출석체크", style = AppTextStyles.Heading2.bold)
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = White,
            modifier = Modifier.clickable { onCheckClick(false) }
        )
    }
}

@Composable
private fun Detail(
    selectedCheck: DailyResponse?,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(20.dp))
        Spacer(
            Modifier
                .height(1.dp)
                .background(Line_Alternative)
        )
        Spacer(Modifier.height(20.dp))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .height(80.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "${selectedCheck?.startAt}일차 보상", style = AppTextStyles.Body2.bold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            }
        }
    }
}