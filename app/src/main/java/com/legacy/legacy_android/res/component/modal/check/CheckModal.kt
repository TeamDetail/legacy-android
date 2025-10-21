package com.legacy.legacy_android.res.component.modal.check

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.legacy.legacy_android.feature.network.check.DailyResponse
import com.legacy.legacy_android.res.component.button.CustomButton
import com.legacy.legacy_android.res.component.button.StatusButton
import com.legacy.legacy_android.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CheckModal(
    onCheckClick: (Boolean) -> Unit,
) {
    val viewModel: CheckViewModel = hiltViewModel()
    val today = LocalDate.now()
    val checkList = remember { listOf("${today.monthValue}월", "론칭") }


    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentCheck = viewModel.uiState.check?.firstOrNull()

    val startDate = remember(currentCheck) {
        currentCheck?.startAt?.let {
            try { LocalDate.parse(it, formatter) } catch (_: Exception) { null }
        }
    }

    val totalDays = currentCheck?.awards?.size ?: 0
    val checkCount = currentCheck?.checkCount ?: 0

    LaunchedEffect(currentCheck) {
        if (currentCheck != null && currentCheck.checkCount > 0) {
            val todayIndex = currentCheck.checkCount.coerceIn(1, currentCheck.awards.size)
            viewModel.setSelectedCheck(currentCheck.awards[todayIndex - 1])
            viewModel.setSelectedDay(todayIndex)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkDaily()
        viewModel.setSelectedDay(currentCheck?.checkCount ?: 0)
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

            /** 상태 버튼 */
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

            /** 기간 표시 */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "기간",
                    style = AppTextStyles.Caption1.Medium,
                    color = Label_Alternative
                )
                Text(
                    text = "${startDate ?: "-"} ~ ${currentCheck?.endAt ?: "-"}",
                    style = AppTextStyles.Caption1.Medium,
                    color = Label_Alternative
                )
            }

            /** 출석 달력 (awards 기준) */
            if (totalDays > 0) {
                val daysRange = (1..totalDays).toList()
                val chunkedDays = daysRange.chunked(7)

                chunkedDays.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEach { dayIndex ->
                            val isPastDay = dayIndex < checkCount + 1
                            val backgroundColor = when {
                                isPastDay -> Fill_Netural
                                viewModel.uiState.currentDay == dayIndex -> Primary
                                else -> Fill_Normal
                            }
                            val textColor = when {
                                isPastDay -> Label_Assitive
                                viewModel.uiState.currentDay == dayIndex -> White
                                else -> Label_Alternative
                            }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(backgroundColor, RoundedCornerShape(8.dp))
                                    .border(1.dp, Line_Alternative, RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.setSelectedCheck(currentCheck!!.awards[dayIndex - 1])
                                        viewModel.setSelectedDay(dayIndex)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$dayIndex",
                                    color = textColor,
                                    style = AppTextStyles.Body2.bold
                                )
                            }
                        }

                        repeat(7 - rowItems.size) {
                            Box(Modifier.size(40.dp).alpha(0f))
                        }
                    }
                }
            }

            /** 상세 보상 및 출석 버튼 */
            if (viewModel.uiState.currentDay != null) {
                currentCheck?.let {
                    Detail(it, viewModel)
                }
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
    check: DailyResponse,
    viewModel: CheckViewModel = hiltViewModel()
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(8.dp))
        Spacer(
            Modifier
                .height(1.dp)
                .background(Line_Alternative)
        )
        Spacer(Modifier.height(8.dp))
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .height(80.dp)
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "${viewModel.uiState.currentDay}일차 보상", style = AppTextStyles.Body2.bold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                viewModel.uiState.currentCheck?.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.itemName,
                            style = AppTextStyles.Caption1.Medium,
                            color = Label_Netural
                        )
                        Text(
                            text = "${it.itemCount}개",
                            style = AppTextStyles.Caption1.Medium,
                            color = Label_Netural
                        )
                    }
                }
            }
        }
        if ((viewModel.uiState.currentDay ?: 0) == if (check.check) check.checkCount else check.checkCount + 1) {
            CustomButton(
                text = if (check.check) "출석완료" else "출석하기",
                onClick = { viewModel.getItem() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                borderColor = if (check.check) Line_Alternative else Primary,
                textColor = if (check.check) Line_Alternative else Primary,
                textStyle = AppTextStyles.Caption1.Bold
            )
        }
    }
}
